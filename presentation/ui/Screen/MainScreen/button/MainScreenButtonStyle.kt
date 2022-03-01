package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button

import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {

    object UserInfos: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = MainMenuButton.Profile,
            text = "profile",
            destination = Screens.UserInfo,
            arg = "",
            enable = true
        )
    )
    object RegisterLogin: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = MainMenuButton.Profile,
            text = "profile",
            destination = Screens.RegisterLogin,
            arg = "",
            enable = true
        )
    )
    object Config: MainScreenButtonStyle(
        NavigationButtonInfo(
            button= MainMenuButton.Config,
            text = "config",
            destination = Screens.Config ,
            arg = "",
            enable = true)
    )
    object Creator: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = MainMenuButton.Creator,
            text = "creator",
            destination = Screens.Creator ,
            arg = "",
            enable = true)
    )
    object Donation: MainScreenButtonStyle(
        NavigationButtonInfo(
            button= MainMenuButton.Donation,
            text = "donation",
            destination = Screens.Donation,
            arg = "",
            enable = true
        )
    )
    object LevelDifficulty1: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff1) )
    object LevelDifficulty2: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff2) )
    object LevelDifficulty3: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff3) )
    object LevelDifficulty4: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff4) )
    object LevelDifficulty5: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff5) )
}

private fun LevelButtonInfos(button: MainMenuButton): NavigationButtonInfo {
    return NavigationButtonInfo(
        button= button,
        text = when (button) {
            is MainMenuButton.LevelDiff1 -> "Difficulty 1"
            is MainMenuButton.LevelDiff2 -> "Difficulty 2"
            is MainMenuButton.LevelDiff3 -> "Difficulty 3"
            is MainMenuButton.LevelDiff4 -> "Difficulty 4"
            is MainMenuButton.LevelDiff5 -> "Difficulty 5"
            else -> "error"
        },
        destination = Screens.LevelByDifficulty,
        arg = button.key.toString(),
        enable = true
    )
}
