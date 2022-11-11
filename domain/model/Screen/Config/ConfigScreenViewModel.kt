package com.mobilegame.robozzle.domain.model.Screen.Config

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.layout.config.ConfigScreenLayout
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.data.room.Config.ConfigRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons.ConfigOption
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ConfigScreenViewModel(application: Application): AndroidViewModel(application) {
    val dataVM = ConfigRoomViewModel(getApplication())
    val animVM = AnimateHeaderAndListViewModel()
    val configData = dataVM.getConfigData()
    val layout = ConfigScreenLayout.init(getApplication())
    var recompositionListner = 0
    fun recomposeConfigScreen() {recompositionListner += 1}


    private val _showToast= MutableStateFlow<Int>(0)
    val showToast: StateFlow<Int> = _showToast.asStateFlow()
    fun showToast() = viewModelScope.launch {
        _showToast.emit(showToast.value + 1)
    }
    private val _lightThemeState = MutableStateFlow(false)
    val lightThemeState = _lightThemeState.asStateFlow()
    fun setSwitchDarkThemStateTo(state: Boolean) {
        showToast()
        _lightThemeState.value = state
        recompositionListner += 1
    }
    fun noLightTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            _lightThemeState.value = false
        }
    }
    fun getRoast(): String {
        return when (showToast.value) {
            6 -> "I was made to prevent you from doing this mistake"
            5 -> "I can do this all day"
            4 -> "Hitting again and again won't change anything"
            3 -> "There is no way"
            2 -> "I already told you no"
            1 -> "No way"
            else -> "Nope"
        }
    }

    fun handleByOptionType(optionType: ConfigOption, state: Boolean) {
        when (optionType) {
            ConfigOption.ToTrash -> handleSwitchToTrashes(state)
            ConfigOption.DisplayWinLevel -> handleSwitchToDisplayLevelWin(state)
            else -> infoLog("handlebyoption", "nothing")
        }
    }

    var switchToTrash = configData.trashesInGame
    private fun handleSwitchToTrashes(state: Boolean) {
        recomposeConfigScreen()
        if (state != switchToTrash) {
            switchToTrash = state
            dataVM.updateTrashesInGameStateTo(state)
        }
        infoLog("switchToTrash", "$switchToTrash")

    }

    var switchToDisplayLevelWin = configData.displayLevelWinInList
    private fun handleSwitchToDisplayLevelWin(state: Boolean) {
        recomposeConfigScreen()
        if (state != switchToDisplayLevelWin) {
            switchToDisplayLevelWin = state
            dataVM.updateDisplayLevelWinInListStateTo(state)
        }
        infoLog("switchToDisplayLevelWin", "$switchToDisplayLevelWin")
    }

    fun startExitAnimationAndPressBack() = runBlocking(Dispatchers.IO) {
        animVM.setVisibleListTargetStateAs(false)
//        setRetToMainMenuState(true)
    }

    fun goingMainMenuListener(navigator: Navigator) {
        if (animVM.headerAnimationEnd() && animVM.listAnimationEnd())
            NavViewModel(navigator).navigateToMainMenu(Screens.Config.route)
        if (animVM.listAnimationEnd())
            animVM.setVisibleHeaderTargetStateAs(false)
    }
}
