package com.mobilegame.robozzle.presentation.ui.button

sealed class MainMenuButton(val key: Int) {
    object None: MainMenuButton(key = -1)
    object Creator: MainMenuButton(key = 0)
    object LevelDiff1: MainMenuButton(key = 1)
    object LevelDiff2: MainMenuButton(key = 2)
    object LevelDiff3: MainMenuButton(key = 3)
    object LevelDiff4: MainMenuButton(key = 4)
    object LevelDiff5: MainMenuButton(key = 5)
    object Profile: MainMenuButton(key = 6)
    object Donation: MainMenuButton(key = 7)
    object Config: MainMenuButton(key = 8)
}
