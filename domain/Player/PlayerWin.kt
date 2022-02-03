package com.mobilegame.robozzle.domain.Player

import com.mobilegame.robozzle.domain.WinDetails.WinDetails

data class PlayerWin(
    val playerID: Int,
    val points: Int,
    val winDetails: WinDetails
)

