package com.mobilegame.robozzle.domain.Player

import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import kotlinx.serialization.Serializable

@Serializable
data class PlayerWin(
    val playerID: Int,
    val points: Int,
    val winDetails: WinDetails
)

@Serializable
data class MyString(
    val str: String
)
