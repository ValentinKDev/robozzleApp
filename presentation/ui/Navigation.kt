package com.mobilegame.robozzle.presentation.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayingScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.CreatorScreen
import com.mobilegame.robozzle.toREMOVE.PlayerData
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.ProfilScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation() {
    //todo: should i create a LoadingScreen between every Screen to reset and reload Data to avoid some issues ?

    val putain_de_bordel_de_git = "..."

    val navController = rememberNavController()

    val playerData = PlayerData()

    val ctxt = LocalContext.current

    val mUserViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(ctxt.applicationContext as Application)
    )

    val mLevelViewModel: MainMenuViewModel = viewModel(
        factory = RobuzzleLevelFactory(ctxt.applicationContext as Application)
    )

    infoLog("Navigation", "${mLevelViewModel._localLevelsListVersion.value}")

    val levelsList: List<RobuzzleLevel> by mLevelViewModel.rbAllLevelList.observeAsState(initial = emptyList())

    mLevelViewModel.testroomversion()
    Log.v("Navigation", "levelList size ${levelsList.size}")

    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
//    NavHost(startDestination = Screens.MainScreen.route, modifier = Modifier.padding(10.dp)){
        composable(route = Screens.MainScreen.route) {
            MainSreen(navController = navController, playerData, mUserViewModel)
        }
        composable( route = Screens.ProfilScreen.route) {
            ProfilScreen(playerData = playerData, userVM = mUserViewModel)
        }
        composable(route = Screens.ConfigScreen.route) {
            ConfigScreen()
        }
        composable(route = Screens.CreatorScreen.route) {
            CreatorScreen()
        }
        composable(route = Screens.LevelsScreen.route) {
            //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data
            LevelsScreen(navController = navController, playerData, mLevelViewModel)
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[6])}
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[3])}
//            levelsList?.let {  RobuzzleLevelDisplay(level = levelsList[8])}
        }
        composable(
            route = Screens.InGameScreen.route + "/{levelNumber}",
            arguments = listOf(navArgument("levelNumber") { type = NavType.StringType }))
        {
            entry ->
            PlayingScreen(level = levelsList[entry.arguments?.getString("levelNumber")?.toInt()!! - 1])
        }
    }
}