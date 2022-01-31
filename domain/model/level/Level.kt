package com.mobilegame.robozzle.domain.model.level

import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

data class Level (
    val name: String,
    val id: Int,
    val difficulty: Int,
    val map: List<String>,
    var instructionsMenu: List<FunctionInstructions>,
    var funInstructionsList: List<FunctionInstructions>,
    val playerInitial: List<Position>,
    val starsList: List<Position>,
)