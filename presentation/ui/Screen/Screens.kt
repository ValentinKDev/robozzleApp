package com.mobilegame.robozzle.presentation.ui.Screen

sealed class Screens(val route: String) {
    object  LoadingDataScreen: Screens("loading_data_screen")
    object  MainScreen: Screens("main_screen")
    object  LevelsScreen: Screens("levels_screen")
    object  ProfilScreen: Screens("Profile_screen")
    object  ConfigScreen: Screens("Config_screen")
    object  CreatorScreen: Screens("Creator_screen")
    object  InGameScreen: Screens("InGame_screen")
}
