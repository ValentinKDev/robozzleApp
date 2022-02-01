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
import com.mobilegame.robozzle.toREMOVE.PlayerData
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation(mainMenuVM: MainMenuViewModel = viewModel()) {
//    val ctxt = LocalContext.current
//    val mUserViewModel: UserViewModel = viewModel(
//        factory = UserViewModelFactory(ctxt.applicationContext as Application)
//    )

//    val mainMenuVM: MainMenuViewModel = viewModel(
//        factory = RobuzzleLevelFactory(ctxt.applicationContext as Application)
//    )

    //todo: should i create a LoadingScreen between every Screen to reset and reload Data to avoid some issues ?
    val navController = rememberNavController()

    val playerData = PlayerData()


//    infoLog("Navigation", "${mainMenuVM._localLevelsListVersion.value}")

//    val levelsList: List<RobuzzleLevel> by mainMenuVM.rbAllLevelList.observeAsState(initial = emptyList())

//    Log.v("Navigation", "levelList size ${levelsList.size}")

    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
        composable(route = Screens.MainScreen.route) {
            MainSreen(navController = navController)
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
        composable(route = Screens.LevelsScreen.route) {
            //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data
            LevelsScreen(navController = navController, playerData, mainMenuVM)
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[6])}
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[3])}
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[8])}
        }
        composable(
            route = Screens.InGameScreen.route + "/{levelNumber}",
            arguments = listOf(navArgument("levelNumber") { type = NavType.StringType })
        ) { entry ->
//            PlayingScreen(level = levelsList[entry.arguments?.getString("levelNumber")?.toInt()!! - 1])
            PlayingScreen(level = mainMenuVM.rbAllLevelList.value!![entry.arguments?.getString("levelNumber")?.toInt()!! - 1])
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
