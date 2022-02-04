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
fun Navigation(navigator: Navigator, mainMenuVM: NavigationViewModel = viewModel(), ) {
    //todo: should i create a LoadingScreen between every Screen to reset and reload Data to avoid some issues ?
    val navController = rememberNavController()

//    val destination: String by navigator.destination.collectAsState()
//    navigator.dest.last()

//    val destination: String by navigator.des.collectAsState(initial = Screens.MainMenu.route)
//    val destination: String by someVM.som.
//    SomeVM(navigator, navController).go()
//    verbalLog("destination", "$destination")
//    navController.navigate(destination)
//    LaunchedEffect(destination) {
//        verbalLog("navigation", "destination $destination")
//        navController.navigate(navigator.dest.toString())
//        navController.navigate(destination)
//        if (navController.currentDestination?.route != destination) {
//            navController.navigate(destination)
//        }
//    }
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
            route = Screens.InGame.route + "/{levelId}",
//            arguments = listOf(navArgument("levelId") { type = NavType.StringType })
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { entry ->
//            entry.arguments?.getString("levelId")?.toInt()?. let {
//                PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(it)!!)
//            }?: ErrorScreen()

//            if (entry.arguments?.getString("levelId")?.toInt() == null) {
//                ErrorScreen()
//            } else {
//                PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(entry.arguments?.getString("levelId")?.toInt()!!)!!)
//                PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(entry.arguments?.getString("levelId")?.toInt()!!)!!)
//            }
            PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(entry.arguments?.getInt("levelId")!!)!!)
        }
        composable(route = Screens.Donation.route) {
            DonationScreen()
        }
    }

//    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
//        composable(route = Screens.MainScreen.route) {
//            MainScreen(navController = navController)
//        }
//        composable(route = Screens.ConfigScreen.route) {
//            ConfigScreen()
//        }
//        composable(route = Screens.CreatorScreen.route) {
//            CreatorScreen(navigator)
////            DonationScreen()
//        }
//        composable( route = Screens.ProfilScreen.route) {
//            infoLog("Screens routing", "ProfilScreen")
//            //todo : bring this coniditon to the navigation button
//            if (mainMenuVM.userDataVM.getUserConnectionState() == UserConnection.Connected.state)
//                UserInfoScreen(navController = navController)
//            else
//                RegisterLoginScreen(navController = navController)
//        }
//        composable(
//            route = Screens.LevelsScreenByID.route + "/{difficulty}",
//            arguments = listOf(navArgument("difficulty") {type = NavType.IntType})
//        ) { entry ->
//            //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data
//            LevelsScreenByID(navController = navController, difficulty = entry.arguments?.getInt("difficulty")!!)
////            mainMenuVM.levelRoomVM.getRobuzzle(6)?.let { PlayingScreen(level = it) }
//        }
//        composable(
//            route = Screens.InGameScreen.route + "/{levelId}",
//            arguments = listOf(navArgument("levelId") { type = NavType.StringType })
//        ) { entry ->
//            if (entry.arguments?.getString("levelId")?.toInt() == null) {
//                ErrorScreen()
//            } else {
//                PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(entry.arguments?.getString("levelId")?.toInt()!!)!!)
//            }
//        }
//        composable(route = Screens.DonationScreen.route) {
//            DonationScreen()
//        }
//    }
}

class SomeVM(private val navigator: Navigator, private val navController: NavController): ViewModel() {
//    val some: MutableSharedFlow<String> = MutableSharedFlow()
//    val som: SharedFlow<String> = some

    fun go() {
        viewModelScope.launch {
            navigator.des.collectLatest {
                navController.navigate(it)
            }
        }
//        viewModelScope.launch {
//            some.emit(dest.route)
//        }
    }
}
