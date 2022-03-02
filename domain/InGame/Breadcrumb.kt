package com.mobilegame.robozzle.domain.InGame

import android.util.Log
import com.mobilegame.robozzle.Extensions.Contains
import com.mobilegame.robozzle.Extensions.ToInt
import com.mobilegame.robozzle.Extensions.copy
import com.mobilegame.robozzle.Extensions.replaceAt
import com.mobilegame.robozzle.analyse.*
import com.mobilegame.robozzle.domain.InGame.res.ON_MAP_PATH
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_BORDER
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_PATH
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.res.TRUE

class Breadcrumb (
    val initialPlayerState: PlayerInGame,
    var initialMap: MutableList<String>,
    var starsList: MutableList<Position>,
    var preloadActions: Int,
    var funInstructionsList: MutableList<FunctionInstructions>,
){
    var map = mutableListOf<String>()
    var initialPreloadAction = preloadActions
    var actionsCount = 0
    var actionList = ""
    var stop = false
    var win = UNKNOWN
    var lost = UNKNOWN
    var loop = UNKNOWN
    var starsNumberLeft = starsList.size
    var starsPositionsLeft = starsList.copy()
    var starsRemovalMap = mutableMapOf<Int, Position>()
    var currentInstructionList = mutableListOf<Position>()
    var playerStateList = mutableListOf<PlayerInGame>()
    var actionsTriggerStarRemoveList = mutableListOf<Int>()
    var colorChangeMap = mutableMapOf<Int, ColorSwitch>()
    var oneMoreRound = false
    var currentPlayerState = initialPlayerState.copy()

    enum class InstructionType {
        MOVE, ROTATE, CHANGE_FUNCTION, COLOR_MAP, UNKNOWN
    }

    fun Char.getInstructionType(): InstructionType {
        return when {
            this.isDirectionChange() -> InstructionType.ROTATE
            this.isMoving() -> InstructionType.MOVE
            this.isDigit() -> InstructionType.CHANGE_FUNCTION
            this.isColorChange() -> InstructionType.COLOR_MAP

            else -> InstructionType.UNKNOWN
        }
    }

    fun CreateNewBeadcrumb(addActions: Int, newfunInstructionsList: MutableList<FunctionInstructions>) {
        funInstructionsList = newfunInstructionsList
//        Print_FunInstructionList(funInstructionsList)
        ResetVars(addActions)

        CreateBreadcrumb()
    }

    //todo : make action 0 initial postion then start the count at 1
    fun CreateBreadcrumb() {
        Log.v("CREATEBREADCRUMB", "STARTING")
        ResetVars(2)

        this.StartLogic()
//        if (IsWin() == false) { win = FALSE; lost = TRUE }

        infoLog(secondpart = "->$actionList", firstpart = "actionList")
        infoLog(secondpart = "->${actionList.length}", firstpart = "actionList.length")
        infoLog(secondpart = "->${playerStateList.size}", firstpart = "playerStateList size")
        infoLog(secondpart = "->$win", firstpart = "win")
        infoLog(secondpart = "->$lost", firstpart = "lost")
        infoLog(secondpart = "->${actionList.length}", firstpart = "actionList size")
        infoLog(secondpart = "->${currentInstructionList.size}", firstpart = "currentInstList size")
        infoLog("Color Change Map", "${colorChangeMap}")
        Print_breadcrumb(this)
    }

    fun StartLogic() { ReadFunction(0) }

    //todo : do i have to initiate the PlayerStateList in case the first instruciton is blanck so the CalculatePlayerState() can CloneLastPlayerstate()
    private fun ReadFunction(funIndex: Int) {
        val caseIndexMax = funInstructionsList[funIndex].instructions.length
//        verbalLog("caseIndexMax", "${caseIndexMax}")
        var caseIndex = 0

//        while ((caseIndex in 0 until caseIndexMax) && !IsWin()) {
        while ((caseIndex in 0 until caseIndexMax) && !IsWin() && actionsCount < preloadActions) {
//            infoLog("ReadFunction(${funIndex})", "index $caseIndex")

            ReadFunctionCase(funIndex, caseIndex)

            if (IsWin()) {
//                errorLog("BREAK", "is win loop ***************")
                break
            }

            if (StopTheOneMoreRound()) {
                errorLog("true", "StopOneMoreRound()")
                oneMoreRound = false}
            if (stop && !oneMoreRound) {
                errorLog("true", "stop && !oneMoreRound")
                lost = actionsCount; break }

            caseIndex++
        }
    }

    fun ReadFunctionCase(funIndex: Int, caseIndex: Int) {
        val instruction = funInstructionsList[funIndex].instructions[caseIndex]
        val instructionColor = funInstructionsList[funIndex].colors[caseIndex]
        val mapCaseColor = map[currentPlayerState.pos.line][currentPlayerState.pos.column]
        val colorMatch = instructionColor.matchMapColor(mapCaseColor)

        //todo : check actions placement
//        errorLog("action $actionsCount : $instruction", "${currentPlayerState.direction.ToChar()}(${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})")

        if (colorMatch) { ApplyAnInstruction(instruction, funIndex, caseIndex) }
        else {
            UpdateActionCountInstructionList(funIndex, caseIndex)
            actionList += instruction
            AddElementToPlayerStateList(CloneLastPlayerState())
        }
    }


    fun ApplyAnInstruction(instruction: Char, funIndex: Int, caseIndex: Int) {
//        infoLog("ApplyAnInstruction", "${instruction}")
        when (instruction.getInstructionType()) {
            InstructionType.MOVE -> {
//                verbalLog("", "move")
                ApplyTheMouvement()
                actionList += instruction
                CheckStarOnNewPosition()
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.ROTATE -> {
//                verbalLog("", "rotation")
                AddTheRotationToTheList(instruction)
                actionList += instruction
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.CHANGE_FUNCTION -> {
                actionList += instruction
                AddElementToPlayerStateList(currentPlayerState.copy())
                UpdateActionCountInstructionList(funIndex, caseIndex)
//                verbalLog("win ${IsWin()}", "call function")
//                if (actionsCount < 25)
                    ReadFunction(instruction.ToInt() )
            }
            InstructionType.COLOR_MAP -> {
                verbalLog("", "color change")
                actionList += instruction
                AddElementToPlayerStateList(currentPlayerState.copy())

                AddColorChange(funIndex, caseIndex, instruction)
                ChangeMapCaseColor(instruction)
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.UNKNOWN -> {
                errorLog("unknown Type", "ApplyAnInstruction()")
            }
        }
    }


    private fun ChangeMapCaseColor(instruction: Char) {
        val colorSwitch = colorChangeMap.get(actionsCount)

//        infoLog("", map[colorSwitch!!.pos.line])
//        errorLog("new", "${colorSwitch.newColor}")
        map[colorSwitch!!.pos.line] = map[colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.newColor)
//        infoLog("", map[colorSwitch.pos.line])
    }

    private fun AddColorChange(funIndex: Int, caseIndex: Int, instruction: Char) {
        val currentPos = currentPlayerState.pos
            colorChangeMap.put(actionsCount, ColorSwitch(
                map[currentPos.line][currentPos.column],
                instruction,
                currentPos.copy()
                )
            )
//            verbalLog("AddColorChanged()", "${colorChangeMap}")
    }

    private fun AddTheRotationToTheList(instruction: Char) {
        currentPlayerState.ChangeDirectionPlayer(instruction)
        AddElementToPlayerStateList(currentPlayerState.copy())
    }
    private fun AddElementToPlayerStateList(newPlayerState: PlayerInGame) {
        playerStateList.add(newPlayerState)
    }

    private fun ApplyTheMouvement() {
        val newPlayerState: PlayerInGame = CloneLastPlayerState()
//        verbalLog("(${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})", "currentPlayerState.pos")

        when (currentPlayerState.ApplyTheMovement(map)) {
            UNKNOWN -> { stop = true
//                errorLog("ApplyTheMouvement", "Can't read the mouvement")
            }
            OUT_OF_MAP_PATH -> {
                stop = true
                AddElementToPlayerStateList(currentPlayerState.copy())
//                errorLog("ApplyTheMouvement", "Outside of the mapPath at action (${newPlayerState.pos.line}, ${newPlayerState.pos.column})")
            }
            OUT_OF_MAP_BORDER-> {
                stop = true
                oneMoreRound = true
//                errorLog("ApplyTheMouvement", "Outside of the mapBorder")
            }
            ON_MAP_PATH -> {
                AddElementToPlayerStateList(currentPlayerState.copy())
//                infoLog("ApplyTheMouvement", "newPlayerState.ApplyTheMovement = ON_MAP_PATH")
            }
        }
    }

    private fun CheckStarOnNewPosition() {
        if (starsPositionsLeft.Contains(currentPlayerState.pos)){
//            infoLog("action count", "${actionsCount}")
//            verbalLog("star at (${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})", ".")
//            verbalLog("","${starsRemovalMap}")

            starsRemovalMap.put(actionsCount, Position(currentPlayerState.pos.line, currentPlayerState.pos.column))
            actionsTriggerStarRemoveList.add(playerStateList.lastIndex)

            starsPositionsLeft.remove(currentPlayerState.pos)
            starsNumberLeft -= 1

            //todo : useless ??
            check_set_Win()
        }
    }

    fun UpdateActionCountInstructionList(funIndex: Int, caseIndex: Int) {
        actionsCount += 1
        currentInstructionList.add(Position(line = funIndex, column = caseIndex))
    }

    private fun StopTheOneMoreRound(): Boolean { return (stop && oneMoreRound) }

    private fun CloneLastPlayerState(): PlayerInGame {
        val cloneLastPlayerState: PlayerInGame = if (playerStateList.isNotEmpty()) {
            playerStateList.last().copy()
        } else { initialPlayerState.copy() }
        return (cloneLastPlayerState)
    }

    private fun check_set_Win(): Boolean {
        val ret =  if (win == UNKNOWN && starsNumberLeft == 0) { win = actionsCount; true } else false
        infoLog("", "checkWin() actionCount ${actionsCount} / ${win} / ${starsNumberLeft}")
        return ret
    }

    private fun Char.matchMapColor(mapCaseColor: Char): Boolean {
        return ((this == mapCaseColor) || (this == 'g'))
    }

    private fun Char.isMoving(): Boolean { return (this == 'u') }
//    private fun Char.isMoving(): Boolean { return (this == 'U') }

    private fun Char.isColorChange(): Boolean { return (this.toString().matches("[RGBg]".toRegex())) }

    private fun Char.isDirectionChange():Boolean {
        return ("[rl]".toRegex().matches(this.toString()))
    }

    fun Int.isUnknown(): Boolean = this == UNKNOWN

    fun IsLost(): Boolean {return (lost >= TRUE)}
    fun IsWin(): Boolean {return (win >= TRUE)}

    fun IsLostAt(action: Int): Boolean {return (IsLost() && lost == action)}
    fun IsWinAt(action: Int): Boolean {return (IsWin() && win == action)}
//    fun IsWin(): Boolean {return (win > -1)}
//    fun NoEnd(): Boolean {return !(IsWin() || IsLost())}
    fun NotFinished(): Boolean {return (win.isUnknown() && lost.isUnknown())}

    fun ResetVars(addActions: Int) {
        win = UNKNOWN
        loop = UNKNOWN
        lost = UNKNOWN

        map = initialMap.copy()
        oneMoreRound = false
        currentInstructionList = arrayListOf()
        playerStateList = mutableListOf()
        actionsTriggerStarRemoveList = mutableListOf()
        colorChangeMap = mutableMapOf()

        stop = false
        actionsCount = 0
        actionList = ""
        preloadActions += addActions
        starsRemovalMap = mutableMapOf()
        starsNumberLeft = starsList.size
        starsPositionsLeft = starsList.copy()
        currentPlayerState = initialPlayerState.copy()
    }



}