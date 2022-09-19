package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class PopupViewModel(firstVisible: Boolean = false): ViewModel() {

//    private val _visiblePopup = MutableStateFlow<Boolean>(true)
    private val _visiblePopup = MutableStateFlow<Boolean>(firstVisible)

    val visiblePopup: StateFlow<Boolean> = _visiblePopup.asStateFlow()
    fun changePopupVisibility() {
        _visiblePopup.value = !_visiblePopup.value
    }
}