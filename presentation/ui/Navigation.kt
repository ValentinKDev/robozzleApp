package com.mobilegame.robozzle.presentation.ui

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Arguments
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.Tab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevelScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@ExperimentalAnimationApi
@DelicateCoroutinesApi
@Composable
fun Navigation(navigator: Navigator, testShared: TestShared) {
    infoLog("navigatgion", "...")
    val navController = rememberNavController()

    LaunchedEffect("navigation") {
        navigator.des.onEach {
            navController.navigate(it)
        }.launchIn(this)
    }

    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screens.MainMenu.route
    ) {
        composable( route = Screens.MainMenu.route )        { MainScreen(navigator) }
        composable( route = Screens.Config.route )          { ConfigScreen() }
        composable( route = Screens.Donation.route)         { DonationScreen() }
        composable( route = Screens.UserInfo.route )        { UserInfoScreen(navigator) }
        composable( route = Screens.RegisterLogin.route )   { RegisterLoginScreen(navigator, Tab()) }
        composable(
            route = Screens.MainMenu.route + "/{${Arguments.Button.key}}",
            arguments = listOf(navArgument(Arguments.Button.key) {type = NavType.IntType})
        ) { entry ->
            entry.arguments?.getInt(Arguments.Button.key)?.let { _buttonId ->
                MainScreen(
                    navigator = navigator,
                    fromButton = _buttonId
                )
            }
        }
        composable(
            route = Screens.LevelByDifficulty.route + "/{${Arguments.Button.key}}",
            arguments = listOf(navArgument(Arguments.Button.key) {type = NavType.IntType}),
        ) { entry ->
//            LevelsScreenByDifficulty(navigator, difficulty = entry.arguments?.getInt(Arguments.LevelDifficulty.key)!!)
            entry.arguments?.getInt(Arguments.Button.key)?.let {
                LevelsScreenByDifficulty(
                    navigator = navigator,
                    levelsDifficulty = it
                )
            }
        }
        composable(
            route = Screens.MainMenu.route + "/{${Arguments.Button.key}}",
            arguments = listOf(navArgument(Arguments.Button.key) {type = NavType.IntType})
        ) { entry ->
            entry.arguments?.getInt(Arguments.Button.key)?.let { _buttonId ->
                MainScreen(
                    navigator = navigator,
                    fromButton = _buttonId
                )
            }
        }
        composable(
            route = Screens.RanksLevel.route + "/{${Arguments.LevelId.key}}",
            arguments = listOf(navArgument(Arguments.LevelId.key) {type = NavType.IntType})
        ) { entry ->
            entry.arguments?.getInt(Arguments.LevelId.key)?.let { _levelId ->
                LevelRoomViewModel(context).getLevel(_levelId)?.let { _level ->
                    RanksLevelScreen(levelId = _levelId, levelName = _level.name)
                }
            }
        }
        composable(
            route = Screens.Playing.route + "/{${Arguments.LevelId.key}}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
//            PlayingScreen(level = LevelRoomViewModel(context).getRobuzzle(entry.arguments?.getInt(Arguments.LevelId.key)!!)!!)
//            PlayingScreen(level = LevelVM(context).getRobuzzleLevel(entry.arguments?.getInt(Arguments.LevelId.key)!!))
            entry.arguments?.getInt(Arguments.LevelId.key)?.let {
                PlayingScreen(level = LevelVM(context).getRobuzzleLevel(it))
            }
        }
        composable(
            route = Screens.Creator.route + "/{${Arguments.Button.key}}",
            arguments = listOf(navArgument(Arguments.Button.key) {type = NavType.StringType})
        ) { entry ->
            entry.arguments?.getString(Arguments.Button.key)?.let { _fromButton ->
                CreatorScreen(
                    navigator = navigator,
                    from = _fromButton
                )
            }
        }

        composable(
            route = Screens.Test.route + "/{${Arguments.Button.key}}",
            arguments = listOf(navArgument(Arguments.Button.key) {type = NavType.StringType}),
        ) { entry ->
            entry.arguments?.getString(Arguments.Button.key)?.let {
                TestScreen(navigator, Animator(), it) }
            }
    }
}