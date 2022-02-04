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
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.CreatorScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
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
        composable(route = Screens.MainMenu.route) {
            MainScreen(navigator)
        }
        composable(route = Screens.Config.route) {
            ConfigScreen()
        }
        composable(route = Screens.Creator.route) {
            CreatorScreen(navigator)
        }
        composable( route = Screens.Profil.route) {
            infoLog("Screens routing", "ProfilScreen")
//            //todo : bring this coniditon to the navigation button
            if (UserDataStoreViewModel(context).getName().isNullOrBlank())
                RegisterLoginScreen(navigator)
            else
                UserInfoScreen(navigator)
        }
        composable(
            route = Screens.LevelByDifficulty.route + "/{difficulty}",
            arguments = listOf(navArgument("difficulty") {type = NavType.IntType})
        ) { entry ->
            //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data
            LevelsScreenByDifficulty(navigator, difficulty = entry.arguments?.getInt("difficulty")!!)
        }
        composable(
            route = Screens.Playing.route + "/{levelId}",
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { entry ->
            PlayingScreen(level = LevelRoomViewModel(context).getRobuzzle(entry.arguments?.getInt("levelId")!!)!!)
        }
        composable(route = Screens.Donation.route) {
            DonationScreen()
        }
    }
}