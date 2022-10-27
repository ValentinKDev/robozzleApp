package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button

import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {

    object UserInfos: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = Screens.UserInfo,
//            button = MainMenuButton.Us,
            text = "profile",
//            destination = Screens.UserInfo,
//            arg = "",
            enable = true
        )
    )
    object RegisterLogin: MainScreenButtonStyle(
        NavigationButtonInfo(
//            button = MainMenuButton.Profile,
            button = Screens.RegisterLogin,
            text = "profile",
//            arg = "",
            enable = true
        )
    )
    object Config: MainScreenButtonStyle(
        NavigationButtonInfo(
//            button= MainMenuButton.Config,
            button = Screens.Config ,
            text = "config",
//            arg = "",
            enable = true)
    )
    object Creator: MainScreenButtonStyle(
        NavigationButtonInfo(
//            button = MainMenuButton.Creator,
            button = Screens.Creator ,
            text = "creator",
//            arg = "",
            enable = true)
    )
    object Donation: MainScreenButtonStyle(
        NavigationButtonInfo(
//            button= MainMenuButton.Donation,
            button = Screens.Donation,
            text = "donation",
//            arg = "",
            enable = true
        )
    )
//    object LevelDifficulty1: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff1) )
//    object LevelDifficulty2: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff2) )
//    object LevelDifficulty3: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff3) )
//    object LevelDifficulty4: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff4) )
//    object LevelDifficulty5: MainScreenButtonStyle( LevelButtonInfos(MainMenuButton.LevelDiff5) )
    object LevelDifficulty1: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty1) )
    object LevelDifficulty2: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty2) )
    object LevelDifficulty3: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty3) )
    object LevelDifficulty4: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty4) )
    object LevelDifficulty5: MainScreenButtonStyle( LevelButtonInfos(Screens.Difficulty5) )
}

private fun LevelButtonInfos(screen: Screens): NavigationButtonInfo {
    var text = ""
    var arg = ""
    when (screen) {
        is Screens.Difficulty1 -> {
            text = "Difficulty 1"
            arg = "1"
        }
        is Screens.Difficulty2 -> {
            text = "Difficulty 2"
            arg = "2"
        }
        is Screens.Difficulty3 -> {
            text = "Difficulty 3"
            arg = "3"
        }
        is Screens.Difficulty4 -> {
            text = "Difficulty 4"
            arg = "4"
        }
        is Screens.Difficulty5 -> {
            text = "Difficulty 5"
            arg = "5"
        }
        else -> "error"
    }
    return NavigationButtonInfo(
        button = screen,
        route = Screens.LevelByDifficulty,
        text = text,
        arg = arg,
        enable = true
//        arg = screen.route,
//        destination = Screens.LevelByDifficulty,
    )
}
