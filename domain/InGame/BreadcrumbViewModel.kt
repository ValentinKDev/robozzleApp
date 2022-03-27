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

//internal const val initialPreloadActionsNumber = 10
//internal const val initialPreloadActionsNumber = 14
internal const val initialPreloadActionsNumber = 30

class BreadcrumbViewModel(val level: Level, instructionRows: List<FunctionInstructions>, addActions: Int = 0): ViewModel() {

    private var bd: Breadcrumb

    private val printDetails: Int? = null

    var numberActionToLoad = initialPreloadActionsNumber
    private var stop = false
    private var loop = UNKNOWN
    private var oneMoreRound = false

    private var currentPlayerState = PlayerInGame(Position(-42,-42), Direction(-42, -42))

    init {
//        loop = UNKNOWN
//        oneMoreRound = false
//        stop = false
        numberActionToLoad += addActions
        currentPlayerState = level.playerInitial.toPlayerInGame()
        bd = Breadcrumb().createABreadCrumb(
            instructionsRows = instructionRows,
            level = level
        )

        logInit?.let { errorLog("Init", "BreadCrumb")
            infoLog("numberActionToLoad", "${numberActionToLoad}")
            infoLog("addAction", "${addActions}")
        }
    }

    fun getBreadCrumb(): Breadcrumb = runBlocking(Dispatchers.IO) {
        logInit?.let { errorLog("Init", "getBreadCrumb") }
        startLogic()
//        errorLog("actionCount", "${bd.lastActionNumber}")
//        infoLog("actionList.size", "${bd.actionsList.size}")
//        bd.playerStateList.forEachIndexed { _i, _plr ->
//            infoLog("$_i", "${_plr.pos}")
//        }
//        infoLog("stars number left", "${bd.starsNumberLeft}")
//        infoLog("stars left", "${bd.starsPositionsLeft}")
//        infoLog("stars removed left", "${bd.starsRemovalMap}")
        infoLog("win", "${bd.win}")
        infoLog("lose", "${bd.lost}")
        verbalLog("playerstate size ", bd.playerStateList.size.toString())
        verbalLog("actions length ", bd.actions.instructions.length.toString())
        verbalLog("actions instructions ", bd.actions.instructions)
        verbalLog("actions colors ", bd.actions.colors)
//        bd.actionsList.printList()
//        verbalLog("list action", "${bd.}")
        bd
    }

    fun expandBreadcrumb(): Breadcrumb = runBlocking(Dispatchers.IO) {
        errorLog("Init", "expandBreadcrumb")
        ReadFunctionCase(funIndex = bd.currentInstructionList.last().line, caseIndex = bd.currentInstructionList.last().column)
        bd
    }

    private fun startLogic() {
        ReadFunction(0)
//        if (IsWin()) bd.win = true
//        else if (IsLost()) bd.lost = lost
    }

    //todo : do i have to initiate the PlayerStateList in case the first instruciton is blanck so the CalculatePlayerState() can CloneLastPlayerstate()
    private fun ReadFunction(funIndex: Int) {
        val caseIndexMax = bd.funInstructionsList[funIndex].instructions.length
        var caseIndex = 0

        while ((caseIndex in 0 until caseIndexMax) && !IsWin() && bd.lastActionNumber < numberActionToLoad) {
            ReadFunctionCase(funIndex, caseIndex)

            if (IsWin()) {
                break
            }
            if (StopTheOneMoreRound()) {
                errorLog("true", "StopOneMoreRound()")
                oneMoreRound = false}
            if (stop && !oneMoreRound) {
//                errorLog("true", "stop && !oneMoreRound")
                bd.lost = bd.lastActionNumber
                break
            }
//            infoLog("playerS", "${bd.playerStateList}")
            caseIndex++
        }
    }

    private fun ReadFunctionCase(funIndex: Int, caseIndex: Int) {
        val instruction = bd.funInstructionsList[funIndex].instructions[caseIndex]
        val instructionColor = bd.funInstructionsList[funIndex].colors[caseIndex]
        val mapCaseColor = bd.map[currentPlayerState.pos.line][currentPlayerState.pos.column]
        val colorMatch = instructionColor.matchMapColor(mapCaseColor)

        //todo : check actions placement
        updateCurrentPlayerState()
        val action = FunctionInstruction(instruction, instructionColor)
        if (colorMatch) { ApplyAnInstruction(action, funIndex, caseIndex) }
        else {
            printDetails?.let { verbalLog("", "${bd.lastActionNumber} stay") }
            UpdateActionCountInstructionList(funIndex, caseIndex)
            updateActions(action)
//            actionList += instruction
            AddElementToPlayerStateList()
        }
    }

    private fun updateActions(action: FunctionInstruction) {
        bd.actions.instructions += action.instruction
        bd.actions.colors += action.color
        bd.actionsList += action
    }


    fun ApplyAnInstruction(action: FunctionInstruction, funIndex: Int, caseIndex: Int) {
//        infoLog("ApplyAnInstruction", "${instruction}")
        when (action.instruction.getInstructionType()) {
            InstructionType.MOVE -> {
                printDetails?.let { verbalLog("", "${bd.lastActionNumber} move") }
                ApplyTheMouvement()
//                actionList += instruction
                updateActions(action)
                CheckStarOnNewPosition()
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.ROTATE -> {
                printDetails?.let { verbalLog("", "${bd.lastActionNumber} rotation") }
                AddTheRotationToTheList(action.instruction)
//                actionList += instruction
                updateActions(action)
                UpdateActionCountInstructionList(funIndex, caseIndex)
            }
            InstructionType.CHANGE_FUNCTION -> {
                printDetails?.let { verbalLog("", "${bd.lastActionNumber} change function") }
//                actionList += instruction
                updateActions(action)
                AddElementToPlayerStateList()
                UpdateActionCountInstructionList(funIndex, caseIndex)
//                verbalLog("win ${IsWin()}", "call function")
//                if (actionsCount < 25)
                    ReadFunction(action.instruction.ToInt() )
            }
            InstructionType.COLOR_MAP -> {
                printDetails?.let { verbalLog("", "${bd.lastActionNumber} color change") }
//                actionList += instruction
                updateActions(action)
                AddElementToPlayerStateList()

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
        bd.colorChangeMap.get(bd.lastActionNumber)?.let {
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
            bd.colorChangeMap.put(bd.lastActionNumber, ColorSwitch(
                bd.map[currentPos.line][currentPos.column],
                instruction,
                currentPos.copy()
                )
            )
//            verbalLog("AddColorChanged()", "${colorChangeMap}")
    }

    private fun AddTheRotationToTheList(instruction: Char) {
        currentPlayerState.ChangeDirectionPlayer(instruction)
        AddElementToPlayerStateList()
    }
    private fun AddElementToPlayerStateList() {
        printDetails?.let {
            infoLog("plyrStT", "\t\tsize ${bd.playerStateList.size}")
            infoLog("add plyrStT", "\t\tfirst() ${bd.playerStateList.first().pos}")
            infoLog("add plyrStT", "\t\tlast() ${bd.playerStateList.last().pos}")
        }
        bd.playerStateList.add(currentPlayerState)
        printDetails?.let {
            infoLog("add plyrStT", "\t\tlast() ${bd.playerStateList.last().pos}")
        }
    }

    private fun ApplyTheMouvement() {
//        val newPlayerState: PlayerInGame = CloneLastPlayerState()
//        verbalLog("(${currentPlayerState.pos.line}, ${currentPlayerState.pos.column})", "currentPlayerState.pos")

        when (currentPlayerState.ApplyTheMovement(bd.map)) {
            UNKNOWN -> { stop = true
//                errorLog("ApplyTheMouvement", "Can't read the mouvement")
            }
            OUT_OF_MAP_PATH -> {
                stop = true
//                AddElementToPlayerStateList(currentPlayerState.copy())
                AddElementToPlayerStateList()
//                errorLog("ApplyTheMouvement", "Outside of the mapPath at action (${newPlayerState.pos.line}, ${newPlayerState.pos.column})")
            }
            OUT_OF_MAP_BORDER-> {
                stop = true
                oneMoreRound = true
//                errorLog("ApplyTheMouvement", "Outside of the mapBorder")
            }
            ON_MAP_PATH -> {
                AddElementToPlayerStateList()
//                AddElementToPlayerStateList(currentPlayerState.copy())
//                infoLog("ApplyTheMouvement", "newPlayerState.ApplyTheMovement = ON_MAP_PATH")
            }
        }
    }

    private fun CheckStarOnNewPosition() {
        if (bd.starsPositionsLeft.contains(currentPlayerState.pos)){

            bd.starsRemovalMap.put(bd.lastActionNumber, Position(currentPlayerState.pos.line, currentPlayerState.pos.column))
            bd.actionsTriggerStarRemoveList.add(bd.playerStateList.lastIndex)

            bd.starsPositionsLeft.remove(currentPlayerState.pos)
            bd.starsNumberLeft -= 1

            //todo : useless ??
            check_set_Win()
        }
    }

    fun UpdateActionCountInstructionList(funIndex: Int, caseIndex: Int) {
        bd.lastActionNumber += 1
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
        val ret =  if (bd.starsNumberLeft == 0) { bd.win = bd.lastActionNumber; true } else false
//        infoLog("", "checkWin() actionCount ${bd.lastActionNumber} / ${bd.win} / ${bd.starsNumberLeft}")
        return ret
    }

    private fun updateCurrentPlayerState() { currentPlayerState = bd.playerStateList.last().copy() }

    private fun Char.matchMapColor(mapCaseColor: Char): Boolean {
        return ((this == mapCaseColor) || (this == 'g'))
    }

    private fun Char.isMoving(): Boolean { return (this == 'u') }
//    private fun Char.isMoving(): Boolean { return (this == 'U') }

    private fun Char.isColorChange(): Boolean { return (this.toString().matches("[RGBg]".toRegex())) }

    private fun Char.isDirectionChange():Boolean {
        return ("[rl]".toRegex().matches(this.toString()))
    }

    private fun Int.isUnknown(): Boolean = this == UNKNOWN

    private fun IsLost(): Boolean {return (bd.lost >= TRUE)}
    private fun IsWin(): Boolean {return (bd.win >= TRUE)}

//    fun IsWin(): Boolean {return (win > -1)}
//    fun NoEnd(): Boolean {return !(IsWin() || IsLost())}
    private fun NotFinished(): Boolean {return (bd.win.isUnknown() && bd.lost.isUnknown())}

    private enum class InstructionType {
        MOVE, ROTATE, CHANGE_FUNCTION, COLOR_MAP, UNKNOWN
    }

    private fun settingVariables(level: Level, instructionRows: List<FunctionInstructions>, addActions: Int) {
    }
}