package com.mobilegame.robozzle.domain.RobuzzleLevel

import com.mobilegame.robozzle.Extensions.clone
import com.mobilegame.robozzle.Extensions.replaceAt
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.DivineGuideLine
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.SelectedFunctionInstructionCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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
    private val _instructionRows = MutableStateFlow<List<FunctionInstructions>>(emptyList())
    val instructionRows: StateFlow<List<FunctionInstructions>> = _instructionRows.asStateFlow()
    private fun setInstructionRows() {
        _instructionRows.value = funInstructionsList
    }
    fun replaceInstruction(pos: Position, case: FunctionInstructions) {
        _instructionRows.value[pos.line].colors =
            funInstructionsList[pos.line].colors.replaceAt(pos.column, case.colors.first())
        _instructionRows.value[pos.line].instructions =
            funInstructionsList[pos.line].instructions.replaceAt(pos.column, case.instructions.first())
    }

    lateinit var selected: Position
    init {
        setInstructionRows()
    }

    var preloadActions = 2
    var breadcrumb: Breadcrumb = Breadcrumb(
        playerInitial,
        map.toMutableList(),
        starsList.clone(),
        preloadActions,
        funInstructionsList
    )

    fun SetSelectedFunctionCase(row: Int, column: Int) {
        selected = Position(row, column)
    }

    fun replaceCaseColor(pos: Position, color: String){
        funInstructionsList[pos.line].colors =
            funInstructionsList[pos.line].colors.replaceAt(pos.column, color.first())
    }
    fun replaceCaseInstruction(pos: Position, newInstruction: String){
        if (newInstruction != "n") {
            funInstructionsList[pos.line].instructions =
                funInstructionsList[pos.line].instructions.replaceAt(pos.column, newInstruction.first())
        }
    }
    fun CheckPlayerAtPoint(row: Int, column: Int, plyr: PlayerInGame):Boolean {
        return (plyr.pos.column == column && plyr.pos.line == row)
    }
}