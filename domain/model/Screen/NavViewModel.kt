package com.mobilegame.robozzle.domain.model.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import kotlinx.coroutines.launch

class NavViewModel(private val navigator: Navigator): ViewModel() {
    fun navigateTo(navigationDestination: NavigationDestination, argStr: String = "") {
        viewModelScope.launch {
            navigator.navig(navigationDestination, argStr)
        }
    }
}