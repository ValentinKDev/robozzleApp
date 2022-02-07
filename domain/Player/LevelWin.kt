package com.mobilegame.robozzle.domain.Player

import com.mobilegame.robozzle.domain.WinDetails.WinDetails

data class LevelWin(
    val levelId: Int,
    val levelName: String,
    val points: Int,
    val winDetails: WinDetails
)
