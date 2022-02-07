package com.mobilegame.robozzle.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.*
import com.mobilegame.robozzle.domain.model.Screen.TabSelectionViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Arguments
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.CreatorScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.Tab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevelScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation(navigator: Navigator) {
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
        composable( route = Screens.MainMenu.route )    { MainScreen(navigator) }
        composable( route = Screens.Config.route )      { ConfigScreen() }
        composable( route = Screens.Donation.route)     { DonationScreen() }
        composable( route = Screens.Creator.route )     { CreatorScreen(navigator) }
        composable( route = Screens.Profil.route )
        {
            infoLog("Screens routing", "ProfilScreen")
//            //todo : bring this coniditon to the navigation button
            if (UserDataStoreViewModel(context).getName().isNullOrBlank())
                RegisterLoginScreen(navigator, Tab())
            else
                UserInfoScreen(navigator)
        }
        composable(
            route = Screens.RanksLevel.route + "/{${Arguments.LevelId.key}}/",
            arguments = listOf(navArgument(Arguments.LevelId.key) {type = NavType.IntType})
        ) { entry ->
//            RanksLevelScreen(levelId = entry.arguments?.getInt(Arguments.LevelId.key))
        }
        composable(
            route = Screens.LevelByDifficulty.route + "/{${Arguments.LevelDifficulty.key}}",
            arguments = listOf(navArgument(Arguments.LevelDifficulty.key) {type = NavType.IntType})
        ) { entry ->
            LevelsScreenByDifficulty(navigator, difficulty = entry.arguments?.getInt(Arguments.LevelDifficulty.key)!!)
        }
        composable(
            route = Screens.Playing.route + "/{${Arguments.LevelId.key}}",
            arguments = listOf(navArgument(Arguments.LevelId.key) { type = NavType.IntType })
        ) { entry ->
            PlayingScreen(level = LevelRoomViewModel(context).getRobuzzle(entry.arguments?.getInt(Arguments.LevelId.key)!!)!!)
        }
    }
}