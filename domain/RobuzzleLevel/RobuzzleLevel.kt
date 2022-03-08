package com.mobilegame.robozzle.domain.RobuzzleLevel

import com.mobilegame.robozzle.domain.InGame.PlayerInGame


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

    init {
//        breadcrumb.cr
//        setInstructionRows()
//        reCalculBreadCrumb()
    }

    lateinit var selected: Position

    fun SetSelectedFunctionCase(row: Int, column: Int) {
        selected = Position(row, column)
    }
}