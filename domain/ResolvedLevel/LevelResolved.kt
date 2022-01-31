package com.mobilegame.robozzle.domain.ResolvedLevel

import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions

data class LevelResolved (
    val lvl_id: Int,
    val lvl_difficulty: Int,
    val details: WinDetails,
)
data class WinDetails (
    val instructionsNumber: Int,
    val actionsNumber: Int,
    val solutionFound: List<FunctionInstructions>,
)
