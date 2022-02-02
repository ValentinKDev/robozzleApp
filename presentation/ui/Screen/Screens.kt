package com.mobilegame.robozzle.presentation.ui.Screen

sealed class Screens(val route: String) {
    object  LoadingDataScreen: Screens("loading_data_screen")
    object  MainScreen: Screens("main_screen")
    object  LevelsScreenByID: Screens("levels_screen_by_id")
    object  ProfilScreen: Screens("profile_screen")
    object  ConfigScreen: Screens("config_screen")
    object  CreatorScreen: Screens("creator_screen")
    object  DonationScreen: Screens("donation_screen")
    object  InGameScreen: Screens("ingame_screen")
    object  RegisterLoginScreen: Screens("registerlogin_screen")
    object  UserInfoScreen: Screens("userinfo_screen")
}
