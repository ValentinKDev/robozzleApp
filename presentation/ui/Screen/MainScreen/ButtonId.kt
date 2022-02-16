package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

sealed class ButtonId(val key: Int) {
//    object None: ButtonId(key = "no_button")
//    object LevelDiff1: ButtonId(key = "button_1_key")
//    object LevelDiff2: ButtonId(key = "button_2_key")
//    object LevelDiff3: ButtonId(key = "button_3_key")
//    object LevelDiff4: ButtonId(key = "button_4_key")
//    object LevelDiff5: ButtonId(key = "button_5_key")
//    object Profile: ButtonId(key = "profile_button_key")
//    object Donation: ButtonId(key = "donation_button_key")
//    object Config: ButtonId(key = "config_button_key")
//    object Creator: ButtonId(key = "creator_button_key")
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
