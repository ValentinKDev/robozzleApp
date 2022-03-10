package com.mobilegame.robozzle.domain.InGame

import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position


object Breadcrumb {
    var map: List<String> = emptyList()
    var win: Boolean = false
    var actionsCount: Int = 0
    var actionsTriggerStarRemoveList: MutableList<Int> = mutableListOf()
    var starsNumberLeft: Int = UNKNOWN
    var funInstructionsList: List<FunctionInstructions> = emptyList()
    var starsPositionsLeft: MutableList<Position> = mutableListOf()
    var starsRemovalMap: MutableMap<Int, Position> = mutableMapOf()
    var currentInstructionList: MutableList<Position> = mutableListOf()
    var playerStateList: MutableList<PlayerInGame> = mutableListOf()
    var colorChangeMap: MutableMap<Int, ColorSwitch> = mutableMapOf()

    var actions: FunctionInstructions = FunctionInstructions("", "")
    var actionsList: List<FunctionInstruction> = emptyList()

    fun createABreadCrumb(
        instructionsRows: List<FunctionInstructions>,
        map: List<String>,
        starsNumberLeft: Int,
        starsPositionLeftList: MutableList<Position>
    ): Breadcrumb {
        val bd = Breadcrumb
        bd.funInstructionsList = instructionsRows
        bd.map = map
        bd.starsNumberLeft = starsNumberLeft
        bd.starsPositionsLeft = starsPositionLeftList
        return bd
    }
}