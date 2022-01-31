package com.mobilegame.robozzle.domain.LevelResolved

import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions

data class WinDetails (
    val instructionsNumber: Int,
    val actionsNumber: Int,
    val solutionFound: List<FunctionInstructions>,
)
