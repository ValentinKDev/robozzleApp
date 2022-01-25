package com.mobilegame.robozzle.domain.Player

data class Player(
    val id: String,
    val name: String,
    val ResolvedLevelList: List<WinLevel>
)

data class WinLevel(
    val lvl: Int,
    val stats: StatLevel,
)

data class StatLevel(
    val instructions: Int,
    val actions: Int
)
