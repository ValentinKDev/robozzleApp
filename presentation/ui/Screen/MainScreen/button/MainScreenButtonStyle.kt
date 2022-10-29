package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button

import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {

    object UserInfos: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.UserInfo,
            text = "profile",
            enable = true
        )
    )
    object RegisterLogin: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.RegisterLogin,
            text = "profile",
            enable = true
        )
    )
    object Config: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.Config ,
            text = "config",
            enable = true)
    )
    object Creator: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.Creator ,
            text = "creator",
            enable = true)
    )
    object Donation: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.Donation,
            text = "donation",
            enable = true
        )
    )
    object LevelDifficulty1: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty1) )
    object LevelDifficulty2: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty2) )
    object LevelDifficulty3: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty3) )
    object LevelDifficulty4: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty4) )
    object LevelDifficulty5: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty5) )
}

private fun LevelButtonInfos(screen: Screens): NavigationButtonInfo {
    return NavigationButtonInfo(
        button = screen,
        text = when (screen) {
            Screens.Difficulty1 -> "Difficulty 1"
            Screens.Difficulty2 -> "Difficulty 2"
            Screens.Difficulty3 -> "Difficulty 3"
            Screens.Difficulty4 -> "Difficulty 4"
            Screens.Difficulty5 -> "Difficulty 5"
            else -> "ERROR"
        },
        enable = true
    )
}
