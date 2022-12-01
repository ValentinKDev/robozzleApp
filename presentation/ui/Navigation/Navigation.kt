package com.mobilegame.robozzle.presentation.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.Navigation.AnimateNavViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.presentation.ui.LevelsScreenByDifficulty
import com.mobilegame.robozzle.presentation.ui.MainScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Arguments
import com.mobilegame.robozzle.presentation.ui.Screen.Config.ConfigScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevelScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreen
import com.mobilegame.robozzle.utils.Extensions.getNavArguement
import kotlinx.coroutines.flow.*

@Composable
fun Navigation(navigator: Navigator, animNav: AnimateNavViewModel = viewModel()) {
    val navController = rememberNavController()

    LaunchedEffect("navigation") {
        infoLog("navigation", "...")
        navigator.des.onEach {
            navController.navigate(it)
        }.launchIn(this)
    }

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screens.MainMenu.route
    ) {
        composable(route = Screens.Creator.route) { CreatorScreen(navigator) }

        /** Config Screen */
        composable(route = Screens.Config.route) {
            AnimateNavigation(
                element = Screens.Config,
                vm = animNav,
            ) { ConfigScreen(navigator) }
        }

        /** Donation Screen */
        composable(route = Screens.Donation.route ) {
            AnimateNavigation(
                element = Screens.Donation,
                vm = animNav,
            ) { DonationScreen(navigator) }
        }

        /** UserInfo Screen */
        composable(route = Screens.UserInfo.route) {
            AnimateNavigation(
                element = Screens.UserInfo,
                vm = animNav,
            ) { UserInfoScreen(navigator) }
        }

        /** RegisterLogin Screen */
        composable(route = Screens.RegisterLogin.route) {
            AnimateNavigation(
                element = Screens.RegisterLogin,
                vm = animNav,
            ) { RegisterLoginScreen(navigator) }
        }

        /** Main Menu Screen */
        composable(route = Screens.MainMenu.route) {
            AnimateNavigation(
                element = Screens.MainMenu,
                vm = animNav,
            ) { MainScreen(navigator) }
        }
        composable(
            route = Screens.MainMenu.route .plus("/{${Screens.None.route}}") ,
            arguments = listOf(navArgument(Screens.None.route) {type = NavType.StringType}),
        ) { entry ->
            entry.arguments?.getString(Screens.None.route)?.let { _fromScreen ->
                infoLog("Navigation::MainMenu", "from : $_fromScreen")
                MainScreen(
                    navigator = navigator,
                    fromScreen = Screens.identify(_fromScreen),
                )
            }
        }

        /** Level By Difficulty Screen */
        composable(
            route = Screens.LevelByDifficulty.route.getNavArguement(Arguments.Button.key) ,
            arguments = listOf( navArgument(Arguments.Button.key) { type = NavType.IntType }, ),
        ) { entry ->
            navController.previousBackStackEntry?.destination?.route?.let { _previousRoute ->
                entry.arguments?.getInt(Arguments.Button.key)?.let { _levelDiff ->
                    navController.previousBackStackEntry?.destination?.route?.let { _previousRoute ->
                        verbalLog("Navigation", "previousRoute $_previousRoute")
                        val fromScreen =  Screens.identify(_previousRoute)
                        LevelsScreenByDifficulty(
                            navigator = navigator,
                            levelsDifficulty = _levelDiff,
                            fromScreen = fromScreen,
                        )
                    }
                }
            }
        }

        /** Ranks Level Screen Screen */
        composable(
            route = Screens.RanksLevel.route + "/{" + Arguments.LevelId.key + "}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt(Arguments.LevelId.key)?.let { _levelId ->
                LevelRoomViewModel(context).getLevel(_levelId)?.let { _level ->
                    navController.previousBackStackEntry?.destination?.route?.let { _previousRoute ->
                        infoLog("Navigation", "previousRoute $_previousRoute")
                        val fromScreen = Screens.identify(_previousRoute)
                        RanksLevelScreen(
                            navigator = navigator,
                            levelId = _levelId,
                            fromScreen = fromScreen
                        )
                    }
                }
            }
        }

        /** Playing Screen */
        composable(
            route = Screens.Playing.route + "/{" + Arguments.LevelId.key + "}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt(Arguments.LevelId.key)?.let { _id ->
//                ArgumentsDataStoreViewModel(context).storeLevelNumberArg(_id)
                PlayingScreen(navigator)
            }
        }

        /** Test Screen */
        composable(
            route = Screens.Test.route,
        ) {
            errorLog("Navigation", "Screen.Test.route")
//            ArgumentsDataStoreViewModel(context).storeLevelNumberArg(0)
//            PlayingScreen(navigator)
            RegisterLoginScreen(navigator = navigator)

//            LevelsScreenByDifficulty(
//                navigator = navigator,
//                levelsDifficulty = 1,
//                fromScreen = Screens.MainMenu,
//            )
//            CreatorScreen(navigator = navigator)
//            UserInfoScreen(navigator = navigator)
//            ConfigScreen(navigator = navigator)
//            CreatorScreen(navigator = navigator)
//            RanksLevelScreen(
//                navigator = navigator,
//                levelId = 1,
//                levelName = "first steps"
//            )
//            DonationScreen(navigator = navigator)
        }
    }
//}
}

var displayUIData: Int? = null
//var displayUIData: Int? = 1
//var displayHttpClientInfo: Int? = null
var displayHttpClientInfo: Int? = 1

val levelTuto = Level(
    name = "Tuto",
    id = 0,
    difficulty = 1,
    map = listOf(
        "..........",
        "..........",
        ".BBggRRBB.",
        ".BBggRRBB.",
        ".BBggRRBB.",
        ".BBggRRBB.",
        ".BBggRRBB.",
        ".BBggRRBB.",
        "..........",
        "..........",
    ),
    instructionsMenu = mutableListOf(
        FunctionInstructions("url01n", "g"),
        FunctionInstructions("url01n", "R"),
        FunctionInstructions("url01n", "B"),
        FunctionInstructions("url01n", "G"),
    ),
    funInstructionsList = mutableListOf(
        FunctionInstructions("........", "gggggggg"),
        FunctionInstructions("........", "gggggggg"),
    ),
    playerInitial = listOf(
        Position(6, 2),
        Position(0, 1)
    ),
    starsList = mutableListOf(
        Position(5, 7),
    ),
)

val mylevelTest2 = Level(
    name = "Two stripes"
    ,
    id = 4
    ,
    difficulty = 1,
    map = listOf(
        /*0*/   "..........",
        "..........",
        "GGGGGGGGGG",
        "BBBBBBBBBB",
        "BBBBBBBBBB",
        "BBBBBBBBBB",
        "BBBBBBBBBB",
        "RRRRRRRRRR",
        "..........",
        "..........",
    ),
    instructionsMenu = mutableListOf(
        FunctionInstructions("urlRBGg.0n", "g"),
        FunctionInstructions("urlRBGg.0n", "R"),
        FunctionInstructions("urlRBGg.0n", "B"),
        FunctionInstructions("urlRBGg.0n", "G"), )
    ,
    funInstructionsList = listOf(
        FunctionInstructions("url0", "gGRg")
//                FunctionInstructions("....", "gggg")
    ) ,
    playerInitial = listOf(
        Position(4,0),
        Position(0,1)
    )
    ,
    starsList = listOf(
        Position(3,0),
        Position(3,2),
        Position(3,4),
        Position(3,6),

        Position(4,1),
        Position(4,3),
        Position(4,5),
        Position(4,7),

        Position(5,2),
        Position(5,4),
        Position(5,6),
        Position(5,8),

        Position(6,3),
        Position(6,5),
        Position(6,7),
        Position(6,9),
    )
)
