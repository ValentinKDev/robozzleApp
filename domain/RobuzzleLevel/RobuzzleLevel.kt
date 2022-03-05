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

    var preloadActions = 3

    var breadcrumb: Breadcrumb = Breadcrumb(
        playerInitial,
        map.toMutableList(),
        starsList.clone(),
        preloadActions,
        funInstructionsList
    )

    private val _instructionRows = MutableStateFlow<List<FunctionInstructions>>(emptyList())
    val instructionRows: StateFlow<List<FunctionInstructions>> = _instructionRows.asStateFlow()

    fun replaceInstruction(pos: Position, case: FunctionInstructions) {
        infoLog("lvl", "${_instructionRows.value}")
        _instructionRows.value[pos.line].colors =
            _instructionRows.value[pos.line].colors.replaceAt(pos.column, case.colors.first())
        _instructionRows.value[pos.line].instructions =
            _instructionRows.value[pos.line].instructions.replaceAt(pos.column, case.instructions.first())
        infoLog("lvl", "${_instructionRows.value}")
        infoLog("lvl", "${instructionRows.value}")
        breadcrumb.CreateBreadcrumb()
    }

    private fun setInstructionRows() {
        _instructionRows.value = funInstructionsList
    }

    init { setInstructionRows() }

    lateinit var selected: Position

    fun SetSelectedFunctionCase(row: Int, column: Int) {
        selected = Position(row, column)
    }
}