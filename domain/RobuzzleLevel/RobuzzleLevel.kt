package com.mobilegame.robozzle.domain.RobuzzleLevel

import com.mobilegame.robozzle.Extensions.clone
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.DivineGuideLine
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.SelectedFunctionInstructionCase


data class RobuzzleLevel(
    val name: String,
    val id: Int,
    val difficulty: Int,
    val map: List<String>,
//    var instructionsMenu: MutableList<FunctionInstructionsRequest>,
    var instructionsMenu: MutableList<FunctionInstructions>,
//    var funInstructionsList: MutableList<FunctionInstructionsRequest>,
    var funInstructionsList: MutableList<FunctionInstructions>,
    val playerInitial: PlayerInGame,
    val starsList: MutableList<Position>,
) {
    lateinit var selected : SelectedFunctionInstructionCase
//    var preloadActions = 25
//    var preloadActions = 20
    var preloadActions = 2
    var breadcrumb: Breadcrumb = Breadcrumb(playerInitial, map.toMutableList(), starsList.clone(), preloadActions, funInstructionsList)

    fun SetSelectedFunctionCase(row: Int, column: Int) {
        selected = SelectedFunctionInstructionCase(row, column)
    }
    fun ReplaceInstructionCaseColor(selected: SelectedFunctionInstructionCase, newChar: Char){
        funInstructionsList[selected.funIndex].colors =
            funInstructionsList[selected.funIndex].colors.substring(0, selected.caseIndex) +
                    newChar + funInstructionsList[selected.funIndex].colors.substring(selected.caseIndex + 1, funInstructionsList[selected.funIndex].colors.length)
    }
    fun ReplaceInstructionInCase(selected: SelectedFunctionInstructionCase, newInstruction: Char){
        if (newInstruction != 'n') {
            funInstructionsList[selected.funIndex].instructions =
                funInstructionsList[selected.funIndex].instructions.substring(0, selected.caseIndex) +
                        newInstruction + funInstructionsList[selected.funIndex].instructions.substring(selected.caseIndex + 1, funInstructionsList[selected.funIndex].instructions.length)
        }
    }
    fun CheckPlayerAtPoint(row: Int, column: Int, plyr: PlayerInGame):Boolean {
        return (plyr.pos.column == column && plyr.pos.line == row)
    }
}