package com.mobilegame.robozzle.domain.InGame

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.utils.Extensions.*
import com.mobilegame.robozzle.analyse.*
import com.mobilegame.robozzle.domain.InGame.res.ON_MAP_PATH
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_BORDER
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_PATH
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.res.TRUE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

internal const val initialPreloadActionsNumber = 15

class BreadcrumbViewModel(level: Level, instructionRows: List<FunctionInstructions>, addActions: Int = 0): ViewModel() {
    var numberActionToLoad = initialPreloadActionsNumber

//    private var bd = Breadcrumb
//    var actionsCount = 0

    private lateinit var bd: Breadcrumb

    var stop = false
    var win = UNKNOWN
    var lost = UNKNOWN
    var loop = UNKNOWN
    var oneMoreRound = false
    var currentPlayerState = PlayerInGame(Position(-42,-42), Direction(-42, -42))

    init {
        settingVariables(level, instructionRows, addActions)
//        StartLogic()
//        bd.actionsCount =
    }


    fun getBreadCrumb(): Breadcrumb = runBlocking(Dispatchers.IO) {
        startLogic()
        Print_breadcrumb(bd)
        bd
    }

    fun startLogic() {
        ReadFunction(0)
        if (IsWin()) bd.win = true
    }

    //todo : do i have to initiate the PlayerStateList in case the first instruciton is blanck so the CalculatePlayerState() can CloneLastPlayerstate()
    private fun ReadFunction(funIndex: Int) {
        val caseIndexMax = bd.funInstructionsList[funIndex].instructions.length
        var caseIndex = 0

        while ((caseIndex in 0 until caseIndexMax) && !IsWin() && bd.actionsCount < numberActionToLoad) {
            ReadFunctionCase(funIndex, caseIndex)

            if (IsWin()) {
                break
            }

            if (StopTheOneMoreRound()) {
                errorLog("true", "StopOneMoreRound()")
                oneMoreRound = false}
            if (stop && !oneMoreRound) {
                errorLog("true", "stop && !oneMoreRound")
                lost = bd.actionsCount
                ; break }

            caseIndex++
        }
    }

    fun ReadFunctionCase(funIndex: Int, caseIndex: Int) {
        val instruction = bd.funInstructionsList[funIndex].instructions[caseIndex]
        val instructionColor = bd.funInstructionsList[funIndex].colors[caseIndex]
        val mapCaseColor = bd.map[currentPlayerState.pos.line][currentPlayerState.pos.column]
        val colorMatch = instructionColor.matchMapColor(mapCaseColor)

        //todo : check actions placement
//        errorLog("action $actionsCount : $instruction", "${currentPlayerState.direction.ToChar()}(${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})")

        val action = FunctionInstruction(instruction, instructionColor)
        if (colorMatch) { ApplyAnInstruction(action, funIndex, caseIndex) }
        else {
            UpdateActionCountInstructionList(funIndex, caseIndex)
            updateActions(action)
//            actionList += instruction
            AddElementToPlayerStateList(CloneLastPlayerState())
        }
    }

    private fun updateActions(action: FunctionInstruction) {
        bd.actions.instructions += action.instruction
        bd.actions.colors += action.color
        bd.actionsList += action
//        actionListAdd(action)
    }


    fun ApplyAnInstruction(action: FunctionInstruction, funIndex: Int, caseIndex: Int) {
//        infoLog("ApplyAnInstruction", "${instruction}")
        when (action.instruction.getInstructionType()) {
            InstructionType.MOVE -> {
//                verbalLog("", "move")
                ApplyTheMouvement()
//                actionList += instruction
                updateActions(action)
                CheckStarOnNewPosition()
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.ROTATE -> {
//                verbalLog("", "rotation")
                AddTheRotationToTheList(action.instruction)
//                actionList += instruction
                updateActions(action)
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.CHANGE_FUNCTION -> {
//                actionList += instruction
                updateActions(action)
                AddElementToPlayerStateList(currentPlayerState.copy())
                UpdateActionCountInstructionList(funIndex, caseIndex)
//                verbalLog("win ${IsWin()}", "call function")
//                if (actionsCount < 25)
                    ReadFunction(action.instruction.ToInt() )
            }
            InstructionType.COLOR_MAP -> {
                verbalLog("", "color change")
//                actionList += instruction
                updateActions(action)
                AddElementToPlayerStateList(currentPlayerState.copy())

                AddColorChange(funIndex, caseIndex, action.instruction)
                ChangeMapCaseColor(action.instruction)
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.UNKNOWN -> {
                errorLog("unknown Type", "ApplyAnInstruction()")
            }
        }
    }


    private fun ChangeMapCaseColor(instruction: Char) {

        bd.colorChangeMap.get(bd.actionsCount)?.let {
            bd.map = bd.map.toMutableList().replaceInMatrice(it.newColor , it.pos)
        }
//        val colorSwitch = bd.colorChangeMap.get(bd.actionsCount)
//        colorResource
//        infoLog("", map[colorSwitch!!.pos.line])
//        errorLog("new", "${colorSwitch.newColor}")
//        bd.map[colorSwitch!!.pos.line] = bd.map[colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.newColor)
//        infoLog("", map[colorSwitch.pos.line])
    }

    private fun AddColorChange(funIndex: Int, caseIndex: Int, instruction: Char) {
        val currentPos = currentPlayerState.pos
            bd.colorChangeMap.put(bd.actionsCount, ColorSwitch(
                bd.map[currentPos.line][currentPos.column],
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
        bd.playerStateList.add(newPlayerState)
    }

    private fun ApplyTheMouvement() {
        val newPlayerState: PlayerInGame = CloneLastPlayerState()
//        verbalLog("(${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})", "currentPlayerState.pos")

        when (currentPlayerState.ApplyTheMovement(bd.map)) {
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
        if (bd.starsPositionsLeft.Contains(currentPlayerState.pos)){
//            infoLog("action count", "${bd.actionsCount}")
//            verbalLog("star at (${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})", ".")
//            verbalLog("","${starsRemovalMap}")

            bd.starsRemovalMap.put(bd.actionsCount, Position(currentPlayerState.pos.line, currentPlayerState.pos.column))
            bd.actionsTriggerStarRemoveList.add(bd.playerStateList.lastIndex)

            bd.starsPositionsLeft.remove(currentPlayerState.pos)
            bd.starsNumberLeft -= 1

            //todo : useless ??
            check_set_Win()
        }
    }

    fun UpdateActionCountInstructionList(funIndex: Int, caseIndex: Int) {
        bd.actionsCount += 1
        bd.currentInstructionList.add(Position(line = funIndex, column = caseIndex))
    }

    private fun StopTheOneMoreRound(): Boolean { return (stop && oneMoreRound) }

    private fun CloneLastPlayerState(): PlayerInGame {
        val cloneLastPlayerState: PlayerInGame =
            if (bd.playerStateList.isNotEmpty()) {
                bd.playerStateList.last().copy()
            } else {
                currentPlayerState.copy()
//                initialPlayerState.copy()
            }
        return (cloneLastPlayerState)
    }

    private fun Char.getInstructionType(): InstructionType {
        return when {
            this.isDirectionChange() -> InstructionType.ROTATE
            this.isMoving() -> InstructionType.MOVE
            this.isDigit() -> InstructionType.CHANGE_FUNCTION
            this.isColorChange() -> InstructionType.COLOR_MAP

            else -> InstructionType.UNKNOWN
        }
    }


    private fun check_set_Win(): Boolean {
        val ret =  if (win == UNKNOWN && bd.starsNumberLeft == 0) { win = bd.actionsCount; true } else false
        infoLog("", "checkWin() actionCount ${bd.actionsCount} / ${win} / ${bd.starsNumberLeft}")
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

//    fun IsWin(): Boolean {return (win > -1)}
//    fun NoEnd(): Boolean {return !(IsWin() || IsLost())}
    fun NotFinished(): Boolean {return (win.isUnknown() && lost.isUnknown())}

    enum class InstructionType {
        MOVE, ROTATE, CHANGE_FUNCTION, COLOR_MAP, UNKNOWN
    }

    fun settingVariables(level: Level, instructionRows: List<FunctionInstructions>, addActions: Int) {
        errorLog("reset", "breadcrumb vars")

        win = UNKNOWN
        loop = UNKNOWN
        lost = UNKNOWN
        oneMoreRound = false
        stop = false
//        bd.actionsCount = 0
        numberActionToLoad += addActions
        currentPlayerState = level.playerInitial.toPlayerInGame()
//        funInstructionsList = instructionRows

        bd = Breadcrumb.createABreadCrumb(
            instructionsRows = instructionRows,
            map = level.map.toMutableList().copy(),
            starsNumberLeft = level.starsList.size,
            starsPositionLeftList = level.starsList.toMutableList().copy(),
        )
    }
}