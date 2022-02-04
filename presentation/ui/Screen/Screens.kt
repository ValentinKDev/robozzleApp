package com.mobilegame.robozzle.presentation.ui.Screen

interface NavigationDestination {
    val route: String
}

sealed class Screens(override val route: String): NavigationDestination {
    object  LoadingData: Screens("loading_data_screen")
    object  MainMenu: Screens("main_menu_screen")
//    object  LevelsByID: Screens("levels_screen_by_id")
    object  LevelByDifficulty: Screens("levelsBydifficulty")
//    object  LevelDifficulty2: Screens("levels_difficulty_2")
//    object  LevelDifficulty3: Screens("levels_difficulty_3")
//    object  LevelDifficulty4: Screens("levels_difficulty_4")
//    object  LevelDifficulty5: Screens("levels_difficulty_5")
    object  Profil: Screens("profile_screen")
    object  Config: Screens("config_screen")
    object  Creator: Screens("creator_screen")
    object  Donation: Screens("donation_screen")
    object  InGame: Screens("ingame_screen")
    object  RegisterLogin: Screens("registerlogin_screen")
    object  UserInfo: Screens("userinfo_screen")

    object Loading: Screens("loading_screen")
}

