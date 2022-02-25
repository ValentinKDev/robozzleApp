package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {

    object UserInfos: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = ButtonId.Profile,
            text = "profile",
            destination = Screens.UserInfo,
            arg = "",
            enable = true
        )
    )
    object RegisterLogin: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = ButtonId.Profile,
            text = "profile",
            destination = Screens.RegisterLogin,
            arg = "",
            enable = true
        )
    )
    object Config: MainScreenButtonStyle(
        NavigationButtonInfo(
            button= ButtonId.Config,
            text = "config",
            destination = Screens.Config ,
            arg = "",
            enable = true)
    )
    object Creator: MainScreenButtonStyle(
        NavigationButtonInfo(
            button = ButtonId.Creator,
            text = "creator",
            destination = Screens.Creator ,
            arg = "",
            enable = true)
    )
    object Donation: MainScreenButtonStyle(
        NavigationButtonInfo(
            button= ButtonId.Donation,
            text = "donation",
            destination = Screens.Donation,
            arg = "",
            enable = true
        )
    )
    object LevelDifficulty1: MainScreenButtonStyle( LevelButtonInfos(ButtonId.LevelDiff1) )
    object LevelDifficulty2: MainScreenButtonStyle( LevelButtonInfos(ButtonId.LevelDiff2) )
    object LevelDifficulty3: MainScreenButtonStyle( LevelButtonInfos(ButtonId.LevelDiff3) )
    object LevelDifficulty4: MainScreenButtonStyle( LevelButtonInfos(ButtonId.LevelDiff4) )
    object LevelDifficulty5: MainScreenButtonStyle( LevelButtonInfos(ButtonId.LevelDiff5) )
}

private fun LevelButtonInfos(button: ButtonId): NavigationButtonInfo {
    return NavigationButtonInfo(
        button= button,
        text = when (button) {
            is ButtonId.LevelDiff1 -> "Difficulty 1"
            is ButtonId.LevelDiff2 -> "Difficulty 2"
            is ButtonId.LevelDiff3 -> "Difficulty 3"
            is ButtonId.LevelDiff4 -> "Difficulty 4"
            is ButtonId.LevelDiff5 -> "Difficulty 5"
            else -> "error"
        },
        destination = Screens.LevelByDifficulty,
        arg = button.key.toString(),
        enable = true
    )
}
