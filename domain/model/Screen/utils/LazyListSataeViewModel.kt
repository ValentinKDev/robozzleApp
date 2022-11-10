package com.mobilegame.robozzle.domain.model.Screen.utils

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.domain.model.data.store.LazyListStateDataStoreViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LazyListStateViewModel(
    context: Context,
    id: Int
): ViewModel() {
    val dataStoreService = LazyListStateDataStoreViewModel(context, id)

    private val _state = MutableStateFlow(dataStoreService.getState())
    val state = _state.asStateFlow()

    fun getState(): LazyListState {
        return dataStoreService.getState()
    }
    fun saveState(index: Int, offset: Int) {
        dataStoreService.saveState(index, offset)
    }
}
