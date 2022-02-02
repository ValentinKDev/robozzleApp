package com.mobilegame.robozzle.presentation.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.state.UserConnection
import com.mobilegame.robozzle.domain.model.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.CreatorScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Facilities.ErrorScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation(mainMenuVM: MainMenuViewModel = viewModel()) {
    //todo: should i create a LoadingScreen between every Screen to reset and reload Data to avoid some issues ?
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.ConfigScreen.route) {
            ConfigScreen()
        }
        composable(route = Screens.CreatorScreen.route) {
            CreatorScreen()
        }
        composable( route = Screens.ProfilScreen.route) {
            infoLog("Screens routing", "ProfilScreen")
//            if (mUserViewModel.userConnectionSate.value == UserConnectionState.Connected)
//                UserInfoScreen(navController = navController, mUserViewModel)
            if (mainMenuVM.userDataVM.getUserConnectionState() == UserConnection.Connected.state)
                UserInfoScreen(navController = navController)
            else
                RegisterLoginScreen(navController = navController)
        }
        composable(
            route = Screens.LevelsScreenByID.route + "/{difficulty}",
            arguments = listOf(navArgument("difficulty") {type = NavType.IntType})
        ) { entry ->
            //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data
            LevelsScreenByID(navController = navController, difficulty = entry.arguments?.getInt("difficulty")!!)
        }
        composable(
            route = Screens.InGameScreen.route + "/{levelId}",
            arguments = listOf(navArgument("levelId") { type = NavType.StringType })
        ) { entry ->
            if (entry.arguments?.getString("levelId")?.toInt() == null) {
                ErrorScreen()
            } else {
                PlayingScreen(level = mainMenuVM.levelRoomVM.getRobuzzle(entry.arguments?.getString("levelId")?.toInt()!!)!!)
            }
        }
        composable(route = Screens.DonationScreen.route) {

        }
    }
}


//class Action(navController: NavHostController) {
//    @OptIn(InternalCoroutinesApi::class)
//    val Main: () -> Unit = {
//        MainSreen(navController = navController)
//    }
//    val navigateBack: () -> Unit = { navController.popBackStack() }
//}
