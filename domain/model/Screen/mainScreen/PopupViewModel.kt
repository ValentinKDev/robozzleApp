package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PopupViewModel(): ViewModel() {

    private val _visiblePopup = MutableStateFlow<Boolean>(true)
    val visiblePopup: StateFlow<Boolean> = _visiblePopup.asStateFlow()
    fun changePopupVisibility() {
        _visiblePopup.value = !_visiblePopup.value
    }
}