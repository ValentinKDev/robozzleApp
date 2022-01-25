package com.mobilegame.robozzle.domain.InGame

import android.util.Log
import com.mobilegame.robozzle.Extensions.Contains
import com.mobilegame.robozzle.Extensions.ToInt
import com.mobilegame.robozzle.Extensions.isInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.analyse.Print_FunInstructionList
import com.mobilegame.robozzle.analyse.Print_Pos_Dir_Inst


class DivineGuideLine(
    val initialPlayerState: PlayerInGame,
    var map: List<String>,
    var starsList: MutableList<Position>,
    var preloadActions: Int,
    var funInstructionsList: MutableList<FunctionInstructions>,
) {
    var initialPreloadAction = preloadActions
    var actionsCount = 0
    var actionList = ""
    var stop = false
    var win = -1
    var lost = -1
    var starsLeft = starsList.size
    var currentInstructionList = arrayListOf<Position>()
    var playerStateList = mutableListOf<PlayerInGame>()
    var actionsTriggerStarRemoveList = mutableListOf<Int>()
    var oneMoreRound = false

    fun CreateGuideline() {
        Log.v("CreateNewGuidLine", "starting")
        this.CreateActionList()
        Print_Pos_Dir_Inst(this)

//        Print_List_Position("currentInstructionList", currentInstructionList)
//        Log.v("CreateGuideline", "actionList.isNotEmpty ${actionList.isNotEmpty()} playerStateList.isNotEmpty ${playerStateList.isNotEmpty()}")
//        Log.i("CreateGuideLine", "actionList.lenght ${actionList.length} -> $actionList")
//        Log.i("CreateGuideLine", "playerStateList.size ${playerStateList.size}")
//        Log.e("CreateGuideLine", "win $win")
//        Log.e("CreateGuideLine", "lost $lost")
//        Log.v("CreateGuidLine", "end")
    }

    private fun CheckForPattern() {
//TODO: stop the creation of new Guideline and trigger the same GuidelinePattern to continue animation
    }

    fun CreateNewGuideline(addActions: Int, newfunInstructionsList: MutableList<FunctionInstructions>) {
        funInstructionsList = newfunInstructionsList
        Print_FunInstructionList(funInstructionsList)
//        ResetVars(addActions)

    Log.e("CHECK", "CreateNewGuideLine")
        CreateGuideline()
//        Log.v("CreateNewGuideline", "actionListsize ${actionList.length} playerStateList.size ${playerStateList.size}")
    }

    fun CreateActionList() {
//        Log.i("CreateActionList", "preloadActions -> ${preloadActions}")
        ReadFunction(0, initialPlayerState.pos)
//        Log.i("CreateActionList", "lenght ${actionList.length} -> ${actionList}")
    }

    //todo: reconnaitre les instruction pour changer la couleur de la case
    private fun ReadFunction(funIndex: Int, playerPos: Position) {
        var currentInstruction: Char
        var instructionColor: Char
        var mapCaseColor: Char
        val caseIndexMax = funInstructionsList[funIndex].instructions.length
        var caseIndex = 0

        while ((caseIndex in 0 until caseIndexMax) && CheckWin()) {
            currentInstruction = funInstructionsList[funIndex].instructions[caseIndex]
            instructionColor = funInstructionsList[funIndex].colors[caseIndex]
            mapCaseColor = map[playerPos.line][playerPos.column]
//            Log.i("action", "------------------- $actionsCount")
            if (mapCaseColor == 'g') Log.i("","action ${actionsCount} mapCaseColor is green")

            if (actionsCount >= preloadActions) { break }
            if (currentInstruction.isInstruction()) {
                currentInstructionList.add(Position(line = funIndex, column = caseIndex))
                actionList += currentInstruction
//                Log.i("", "pos (${playerStateList.last().pos.line}, ${playerStateList.last().pos.column})")
                if (instructionColor.matchColorMap(mapCaseColor) == false ) { Log.e("", "action ${actionsCount} unmatch color $currentInstruction(${funIndex} ,${caseIndex}) colors ${instructionColor}-${mapCaseColor}")}
                CalculatePlayerState( currentInstruction, instructionColor.matchColorMap(mapCaseColor) )
//                Log.i("", "to pos (${playerStateList.last().pos.line}, ${playerStateList.last().pos.column})")
//                if (currentInstruction.isDigit() && actionsCount < preloadActions) ReadFunction(currentInstruction.ToInt(), playerStateList.last().pos)
                if (currentInstruction.isDigit() && instructionColor.matchColorMap(mapCaseColor) && actionsCount < preloadActions) ReadFunction(currentInstruction.ToInt(), playerStateList.last().pos)
                if (!CheckWin()) { break }
            }
//            else { Log.e("isNOTInstruction", "$currentInstruction") }

            if (stopTheOneMoreRound()) oneMoreRound = false
            if (stop && !oneMoreRound) { lost = actionsCount; break }
            caseIndex++
        }
    }

    private fun CheckWin(): Boolean {
        val loop =  if (starsLeft == 0 ) { win = actionsCount; false } else true
        return loop
    }


    private fun CalculatePlayerState(instruction: Char, colorsMatching: Boolean) {
        actionsCount += 1
        when (colorsMatching) {
            true -> { stop = DoesInstructionStopProcess(instruction) }
            false -> { playerStateList.add(CloneLastPlayerState(playerStateList)) }
        }
    }

    private fun DoesInstructionStopProcess(instruction: Char): Boolean {
        var instructionIsInvalid : Boolean = when {
            instruction.isDirectionChange() -> {
//                Log.i("isDirectionChange", "$instruction")
                AddDirectionChangedState(instruction);
                false}
            instruction.isDigit() -> {
//                Log.i("isDigit", "$instruction")
                playerStateList.add(CloneLastPlayerState(playerStateList));
                false }
            instruction.isMoving() -> {
//                Log.i("isMoving", "$instruction")
                AddNewPositionState() }
            else -> {
//                Log.i("StopProcess", "$instruction")
                true}
        }
        return instructionIsInvalid
    }

    private fun AddNewPositionState(): Boolean {
        val newPlayerState: PlayerInGame = CloneLastPlayerState(playerStateList)
        var newPositionStateIsInvalid = true

        when (newPlayerState.ApplyTheMovement(map)) {
            -1 -> {
                playerStateList.add(newPlayerState)
                newPositionStateIsInvalid = true
//                Log.v("CalculatePlayerState", "Outside of the mapPath at action (${newPlayerState.pos.line}, ${newPlayerState.pos.column})")
            }
            1 -> { playerStateList.add(newPlayerState);
                newPositionStateIsInvalid = false
//                Log.v("AddNewPostionState", "newPlayerState.ApplyTheMovement = 1")
            }
            0 -> { newPositionStateIsInvalid = true;
                oneMoreRound = true
//                Log.v("CalculatePlayerState", "Outside of the mapBorder")
            }
            -2 -> { newPositionStateIsInvalid = true
//                Log.v("CalculatePlayerState", "Can't read the mouvement")
            }
        }
//        if (newPlayerState.isInsideMapBorder) { playerStateList.add(newPlayerState) } else { newPositionStateIsValid = false }
        if (starsList.Contains(newPlayerState.pos)){
            actionsTriggerStarRemoveList.add(playerStateList.lastIndex)
            starsLeft -= 1
        }
        return newPositionStateIsInvalid
    }

    private fun AddDirectionChangedState(instruction: Char) {
        var newPlayerState = CloneLastPlayerState(playerStateList)
        newPlayerState.ChangeDirectionPlayer(instruction)
        playerStateList.add(newPlayerState)
    }

    fun ResetVars(addActions: Int) {
        oneMoreRound = false
        currentInstructionList = arrayListOf()
        playerStateList = mutableListOf()
//        playerStateList = mutableListOf(initialPlayerState)
        actionsTriggerStarRemoveList = mutableListOf()
        stop = false
//        actionsCount = -1
        actionsCount = 0
        actionList = ""
        preloadActions += addActions
        starsLeft = starsList.size
    }

    // Todo : est necessaire ou just initialiser directement a la creation de la guideline?
    private fun CloneLastPlayerState(playerStateList: MutableList<PlayerInGame>): PlayerInGame {
        val cloneLastPlayerState: PlayerInGame = if (playerStateList.isNotEmpty()) {
            CloneAPlayerState(playerStateList.last())
        } else { CloneAPlayerState(initialPlayerState)}
        return (cloneLastPlayerState)
    }

    private fun CloneAPlayerState(player: PlayerInGame): PlayerInGame {
        return (PlayerInGame(Position(player.pos.line, player.pos.column), Direction(player.direction.x, player.direction.y)) )
    }

    private fun stopTheOneMoreRound(): Boolean { return (stop && oneMoreRound) }

    private fun Char.isMoving(): Boolean { return (this == 'U') }

    private fun Char.isDirectionChange():Boolean {
        return ("[rl]".toRegex().matches(this.toString()))
    }

    //Todo: on instruction calling for a function the color is not taken as a criteria
    //Todo: bug instruction in gray color should work on any case but it seem it does in particular for the fonction call like 0
    private fun Char.matchColorMap(mapCaseColor: Char): Boolean {
//        if( ! ((this == mapCaseColor) || (this == 'G')) ) Log.
//        if (!((this == mapCaseColor) || (this == 'G')))Log.e("NOmatch", "this $this and $mapCaseColor")
        return ((this == mapCaseColor) || (this == 'G'))
    }

    fun IsLost(): Boolean {return (lost > -1)}
    fun IsWin(): Boolean {return (win > -1)}
    fun NoEnd(): Boolean {return !(IsWin() || IsLost())}
    fun End(): Boolean {return (IsWin() || IsLost())}
    fun actionEnd(): Int {return (if (win > lost) win else lost)}

}

