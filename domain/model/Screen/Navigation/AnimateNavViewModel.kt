package com.mobilegame.robozzle.domain.model.Screen.Navigation

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimateNavViewModel(): ViewModel() {
    private val _visibleElement = MutableStateFlow<Screens>(Screens.Unknown)
    val visibleElement: StateFlow<Screens> = _visibleElement.asStateFlow()
    fun updateVisibleElementAs(newState: Screens) {
        infoLog("AnimateNavViewModel::updateVisibleElementAs", "${newState.route}")
       _visibleElement.value = newState
    }
}