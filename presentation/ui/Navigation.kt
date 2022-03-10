package com.mobilegame.robozzle.presentation.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.presentation.ui.Screen.Arguments
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.Tab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevelScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreen
import com.mobilegame.robozzle.presentation.ui.utils.getWindowCoordinates
import kotlinx.coroutines.flow.*

@Composable
fun Navigation(navigator: Navigator) {
    infoLog("navigatgion", "...")
    val navController = rememberNavController()

    LaunchedEffect("navigation") {
        navigator.des.onEach {
            navController.navigate(it)
        }.launchIn(this)
    }

    val context = LocalContext.current
//    val dens = LocalDensity.current
    NavHost(
        navController = navController,
//        startDestination = Screens.MainMenu.route
        startDestination = Screens.Test.route
    ) {
        composable(route = Screens.MainMenu.route) { MainScreen(navigator) }
        composable(route = Screens.Config.route) { ConfigScreen() }
        composable(route = Screens.Donation.route) { DonationScreen() }
        composable(route = Screens.Creator.route) { CreatorScreen(navigator) }
        composable(route = Screens.UserInfo.route) { UserInfoScreen(navigator) }
        composable(route = Screens.RegisterLogin.route) { RegisterLoginScreen(navigator, Tab()) }
        /** Main Menu Screen */
        composable(
            route = Screens.MainMenu.route + "/{" + Arguments.Button.key + "}",
            arguments = listOf(navArgument(Arguments.Button.key) { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt(Arguments.Button.key)?.let { _buttonId ->
                MainScreen(
                    navigator = navigator,
                    fromButton = _buttonId,
                )
            }
        }
        /** Level By Difficulty Screen */
        composable(
            route = Screens.LevelByDifficulty.route + "/{" + Arguments.Button.key + "}",
            arguments = listOf(navArgument(Arguments.Button.key) { type = NavType.IntType }),
        ) { entry ->
            entry.arguments?.getInt(Arguments.Button.key)?.let {
                LevelsScreenByDifficulty(
                    navigator = navigator,
                    levelsDifficulty = it
                )
            }
        }
        /** Ranks Level Screen Screen */
        composable(
            route = Screens.RanksLevel.route + "/{" + Arguments.LevelId.key + "}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt(Arguments.LevelId.key)?.let { _levelId ->
                LevelRoomViewModel(context).getLevel(_levelId)?.let { _level ->
                    RanksLevelScreen(levelId = _levelId, levelName = _level.name)
                }
            }
        }
        /** Game Screen */
        composable(
            route = Screens.Playing.route + "/{" + Arguments.LevelId.key + "}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt(Arguments.LevelId.key)?.let { _id ->
//                val layoutCoordinates: LayoutCoordinates? = getWindowCoordinates()
//                val windowCoordinates: Rect? = ScreenDataStoreViewModel(context).getWindowCoordinates()
//                windowCoordinates?.let {
//                layoutCoordinates?.let { _layoutCoordinates ->
//                LevelVM(context).getLevel(id)?.let {
                ArgumentsDataStoreViewModel(context).storeLevelNumberArg(_id)
                    PlayingScreen(
//                        it
                    )
//                }
//                    val level = LevelVM(context).getLevel(_id)
//                    PlayingScreen(
//                        vm = GameDataViewModel(
//                            level = level,
//                            windowCoordinates = it,
//                            dens = dens,
//                        )
//                    )
//                }
            }
        }

        /** Test Screen */
        composable(
            route = Screens.Test.route,
        ) {
            errorLog("Navigation", "Screen.Test.route")
//            val leyou = ScreenDataStoreViewModel(context).getWindowCoordinates()
//            val layoutCoordinates: LayoutCoordinates? = getLayoutCoordinates()
//            val layoutCoordinates: LayoutCoordinates? = ScreenDataStoreViewModel(context).getLayoutCoordinates()
//            layoutCoordinates?.let { _layoutCoordinates ->
//            ScreenDataStoreViewModel(context).getWindowCoordinates()?.let {
//                errorLog("Navigation", "layoutCoordinates = $it")

//                val vm = GameDataViewModel(
//                    level = myleveltest,
//                        context,
//                    windowCoordinates = it,
//                    dens =   dens,
//                )
//                val vm: GameDataViewModel by viewModel { GameDataViewModel(myleveltest, context) }
//                private val viewModel = ViewModelProvider(this, viewModelFactory).get(SheduleViewModel::class.java)
            ArgumentsDataStoreViewModel(context).storeLevelNumberArg(3)
//                PlayingScreen(
//                    GameDataViewModel( myleveltest, context )
//                    GameDataViewModel(context as Application, myleveltest)
//                    vm = vm
//                )
//            val lvl = LevelRoomViewModel(context).getLevel(10)?.let {
//            LevelRoomViewModel(context).getLevel(10)?.let {
            PlayingScreen(
//            lvl
//            it
            )
//            }?: errorLog("ERROR", "Cant acces the level")

//            }
        }
    }
//}
}
val myleveltest = Level(
    name = "zipline",
    id = 3,
    difficulty = 1,
    map = listOf(
        "..........",
        "..........",
        "..........",
        "..........",
        "BRRRRRRRRG",
        "B........G",
        "..........",
        "..........",
        "..........",
        "..........",
    ),
    instructionsMenu = mutableListOf(
        FunctionInstructions("urlRBGg.0123n", "g"),
        FunctionInstructions("urlRBGg.0123n", "R"),
        FunctionInstructions("urlRBGg.0123n", "B"),
        FunctionInstructions("urlRBGg.0123n", "G"),
    ),
    funInstructionsList = mutableListOf(
        FunctionInstructions("u0r0", "gRgg"),
    ),
    playerInitial = listOf(
        Position(5, 0),
        Position(0, 1)
    ),
    starsList = mutableListOf(
        Position(5, 9),
    ),
)
