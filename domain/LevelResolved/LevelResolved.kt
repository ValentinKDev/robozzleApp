package com.mobilegame.robozzle.domain.LevelResolved

import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions

data class LevelResolved (
    val lvl_id: Int,
    val points: Int,
    val details: WinDetails,
)
