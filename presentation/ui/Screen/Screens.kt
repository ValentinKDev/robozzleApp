package com.mobilegame.robozzle.presentation.ui.Screen

interface NavigationDestination {
    val route: String
}

sealed class Screens(override val route: String, val key: Int): NavigationDestination {
    object  LoadingData: Screens("loading_data_screen", -42)
    object  None: Screens("none_screen", -1)
    object  MainMenu: Screens("main_menu_screen", -42)
    object  LevelByDifficulty: Screens("levelsBydifficulty", -42)
    object  Profil: Screens("profile_screen_id0", 0)
    object  RegisterLogin: Screens("registerlogin_screen", 0)
    object  UserInfo: Screens("userinfo_screen", 0)
    object  Difficulty1: Screens("levels_by_difficulty_id1", 1)
    object  Difficulty2: Screens("levels_by_difficulty_id2", 2)
    object  Difficulty3: Screens("levels_by_difficulty_id3", 3)
    object  Difficulty4: Screens("levels_by_difficulty_id4", 4)
    object  Difficulty5: Screens("levels_by_difficulty_id5", 5)
    object  Creator: Screens("creator_screen_id6", 6)
    object  Donation: Screens("donation_screen_id7", 7)
    object  Config: Screens("config_screen_id8", 8)
    object  Test: Screens("test_screen", -42)
    object  Playing: Screens("ingame_screen", -42)
    object  RanksLevel: Screens("ranks_level_screen", -42)
    object  Unknown: Screens("unknown_screen", 42)

    object Loading: Screens("loading_screen", -42)

    companion object {
        fun findScreen(routeToFind: String? = null, keyToFind: Int? = null): Screens {
            return Screens::class.sealedSubclasses
                    .firstOrNull { _it ->
                        routeToFind?.let { _it.objectInstance?.route == routeToFind }
                            ?: keyToFind?.let {  _it.objectInstance?.key == keyToFind }
                            ?: false
                    }
                ?.objectInstance
                ?: Unknown
        }
//        fun Screens.isLevelByDiff(): Boolean = Difficulty1.key <= this.key && this.key <= Difficulty5.key
        fun Screens.isLevelByDiff(): Boolean = this.key in Difficulty1.key..Difficulty5.key
    }
}

sealed class Arguments(val key: String) {
    object LevelId: Arguments(key = "level_id_argument_key")
    object LevelDifficulty: Arguments(key = "level_difficulty_argument_key")
    object Button: Arguments(key = "button_screen_key")
//    object From: Arguments(key = "from_screen_key")
//    object From: Arguments(key = "from_screen_key")
//    object OnBackPressed: Arguments(key = "onBack_key")
}

