package com.mobilegame.robozzle.domain.Player

import com.mobilegame.robozzle.domain.LevelResolved.WinDetails

data class PlayerWin(
    val playerID: String,
    val winDetails: WinDetails
)

