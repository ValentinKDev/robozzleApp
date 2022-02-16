package com.mobilegame.robozzle.domain.model.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NavViewModel(private val navigator: Navigator): ViewModel() {
    fun navigateTo(destination: NavigationDestination, argStr: String = "", delayTiming: Long? = null) {
        viewModelScope.launch {
            delayTiming?.let { delay(delayTiming) }
            navigator.navig(destination, argStr)
        }
    }
}