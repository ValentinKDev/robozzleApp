package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.ui.button.ButtonInfo

sealed class MainScreenButtonStyle(val type: ButtonInfo) {
    object Profil: MainScreenButtonStyle( ButtonInfo("profile", 100,100, Color.Gray, Screens.ProfilScreen.route, true))

    object LevelDifficulty1: MainScreenButtonStyle( ButtonInfo("difficulty 1",300,80, Color.Gray, Screens.LevelsScreenByID.route + "/1", true))
    object LevelDifficulty2: MainScreenButtonStyle( ButtonInfo("difficulty 2",300,80, Color.Gray, Screens.LevelsScreenByID.route + "/2", true))
    object LevelDifficulty3: MainScreenButtonStyle( ButtonInfo("difficulty 3",300,80, Color.Gray, Screens.LevelsScreenByID.route + "/3", true))
    object LevelDifficulty4: MainScreenButtonStyle( ButtonInfo("difficulty 4",300,80, Color.Gray, Screens.LevelsScreenByID.route + "/4", true))
    object LevelDifficulty5: MainScreenButtonStyle( ButtonInfo("difficulty 5",300,80, Color.Gray, Screens.LevelsScreenByID.route + "/5", true))

    object Config: MainScreenButtonStyle( ButtonInfo("config",100,100, Color.Gray, Screens.ConfigScreen.route, true))
    object Creator: MainScreenButtonStyle( ButtonInfo("creator",100,100, Color.Gray, Screens.CreatorScreen.route, false))
    object Donation: MainScreenButtonStyle( ButtonInfo("donation",100,100, Color.Gray, Screens.DonationScreen.route, true))
}
