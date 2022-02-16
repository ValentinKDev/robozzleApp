package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {
    object UserInfos: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.Profile.key,"profile", 100,100, Color.Gray, Screens.UserInfo,"", true) )
    object RegisterLogin: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.Profile.key,"profile", 100,100, Color.Gray, Screens.RegisterLogin,"", true) )

    object LevelDifficulty1: MainScreenButtonStyle(
        NavigationButtonInfo(ButtonId.LevelDiff1.key, "difficulty 1",300,80, Color.Gray, Screens.LevelByDifficulty, ButtonId.LevelDiff1.key.toString(),true) )
    object LevelDifficulty2: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.LevelDiff2.key,"difficulty 2",300,80, Color.Gray, Screens.LevelByDifficulty, ButtonId.LevelDiff2.key.toString(),true) )
    object LevelDifficulty3: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.LevelDiff3.key,"difficulty 3",300,80, Color.Gray, Screens.LevelByDifficulty, ButtonId.LevelDiff3.key.toString(),true) )
    object LevelDifficulty4: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.LevelDiff4.key,"difficulty 4",300,80, Color.Gray, Screens.LevelByDifficulty, ButtonId.LevelDiff4.key.toString(),true) )
    object LevelDifficulty5: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.LevelDiff5.key, "difficulty 5",300,80, Color.Gray, Screens.LevelByDifficulty, ButtonId.LevelDiff5.key.toString(),true) )

    object Config: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.Config.key, "config",100,100, Color.Gray, Screens.Config , "",true) )
    object Creator: MainScreenButtonStyle(
        NavigationButtonInfo(ButtonId.Creator.key, "creator",100,100, Color.Gray, Screens.Creator , ButtonId.Creator.key.toString(), true) )
    object Donation: MainScreenButtonStyle(
        NavigationButtonInfo( ButtonId.Donation.key, "donation",100,100, Color.Gray, Screens.Donation,"", true ) )
}
