package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

sealed class MainScreenButtonStyle(val type: NavigationButtonInfo) {
//    object Profil: MainScreenButtonStyle( NavigationButtonInfo("profile", 100,100, Color.Gray, Screens.Profil,"", true))
    object UserInfos: MainScreenButtonStyle( NavigationButtonInfo("profile", 100,100, Color.Gray, Screens.UserInfo,"", true))
    object RegisterLogin: MainScreenButtonStyle( NavigationButtonInfo("profile", 100,100, Color.Gray, Screens.RegisterLogin,"", true))

    object LevelDifficulty1: MainScreenButtonStyle( NavigationButtonInfo( "difficulty 1",300,80, Color.Gray, Screens.LevelByDifficulty, "1",true))
    object LevelDifficulty2: MainScreenButtonStyle( NavigationButtonInfo( "difficulty 2",300,80, Color.Gray, Screens.LevelByDifficulty, "2",true))
    object LevelDifficulty3: MainScreenButtonStyle( NavigationButtonInfo( "difficulty 3",300,80, Color.Gray, Screens.LevelByDifficulty, "3",true))
    object LevelDifficulty4: MainScreenButtonStyle( NavigationButtonInfo( "difficulty 4",300,80, Color.Gray, Screens.LevelByDifficulty, "4",true))
    object LevelDifficulty5: MainScreenButtonStyle( NavigationButtonInfo( "difficulty 5",300,80, Color.Gray, Screens.LevelByDifficulty, "5",true))

    object Config: MainScreenButtonStyle( NavigationButtonInfo( "config",100,100, Color.Gray, Screens.Config ,"",false))
    object Creator: MainScreenButtonStyle( NavigationButtonInfo("creator",100,100, Color.Gray, Screens.Creator ,"", true))
    object Donation: MainScreenButtonStyle( NavigationButtonInfo("donation",100,100, Color.Gray, Screens.Donation,"", true))
}
