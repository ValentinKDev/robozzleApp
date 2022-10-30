package com.mobilegame.robozzle.domain.model.Screen.Navigation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

class NavViewModel(private val navigator: Navigator): ViewModel() {

    fun navigateToScreen(screen: Screens) {
        when (screen) {
            Screens.Profil -> navigateToProfil()
            Screens.Config -> navigateToConfig()
            Screens.Creator -> navigateToCreator()
            Screens.Donation -> navigateToDonation()
            Screens.UserInfo -> navigateToUserInfo()
            Screens.RegisterLogin -> navigateToRegisterLogin()
            Screens.Difficulty1 , Screens.Difficulty2 , Screens.Difficulty3 , Screens.Difficulty4 , Screens.Difficulty5 ->
                navigateToScreenLevelByDiff(screen.key.toString())
            else -> errorLog("NavViewModel::navigateToScreen", "ERROR with ${screen.route} param")
        }
    }
    fun navigateToConfig() = navigateTo(Screens.Config)
    fun navigateToCreator() = navigateTo(Screens.Creator)
    fun navigateToDonation() = navigateTo(Screens.Donation)
    fun navigateToProfil() = navigateTo(Screens.Profil)
    fun navigateToUserInfo() = navigateTo(Screens.UserInfo)
    fun navigateToRegisterLogin() = navigateTo(Screens.RegisterLogin)
    fun navigateToRanksLevel(argStr: String, delayTiming: Long?) = navigateTo(Screens.RanksLevel, argStr)
    fun navigateToMainMenu(fromScreen: String) = navigateTo(Screens.MainMenu, fromScreen)
    fun navigateToScreenLevelByDiff(levelDifficulty: String) {
        navigateTo( Screens.LevelByDifficulty, levelDifficulty)
    }

    //todo make it private
    private fun navigateTo(destination: NavigationDestination, argStr: String = "") {
        viewModelScope.launch {
            navigator.navig(destination, argStr)
        }
    }

    //todo: why storing the int ???
    fun navigateToScreenPlay(levelId: Int, delayTiming: Long? = null, context: Context) = runBlocking {
        val storing = viewModelScope.async {
            ArgumentsDataStoreViewModel(context).storeLevelNumberArg(levelId = levelId)
        }
        storing.await()
        navigateTo(
            destination = Screens.Playing,
            argStr = levelId.toString(),
        )
    }
    fun storeIntAndNavigateTo(destination: NavigationDestination, int: Int, delayTiming: Long? = null, context: Context) = runBlocking {
        val storing = viewModelScope.async {
            ArgumentsDataStoreViewModel(context).storeLevelNumberArg(levelId = int)
        }
        storing.await()
        navigateTo(
            destination = destination,
            argStr = int.toString(),
        )
    }
}