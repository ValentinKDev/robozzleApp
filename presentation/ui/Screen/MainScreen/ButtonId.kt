package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

sealed class ButtonId(val key: Int) {
    object None: ButtonId(key = -1)
    object Creator: ButtonId(key = 0)
    object LevelDiff1: ButtonId(key = 1)
    object LevelDiff2: ButtonId(key = 2)
    object LevelDiff3: ButtonId(key = 3)
    object LevelDiff4: ButtonId(key = 4)
    object LevelDiff5: ButtonId(key = 5)
    object Profile: ButtonId(key = 6)
    object Donation: ButtonId(key = 7)
    object Config: ButtonId(key = 8)
}
