package com.mobilegame.robozzle.domain.Player

import com.mobilegame.robozzle.domain.WinDetails.WinDetails

data class LevelWin(
    val lvl_id: Int,
    val lvl_name: String,
    val points: Int,
    val details: WinDetails
)
