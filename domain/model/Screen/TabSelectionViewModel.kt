package com.mobilegame.robozzle.domain.model.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TabSelectionViewModel(): ViewModel() {
    private val _selected: MutableStateFlow<Tabs> = MutableStateFlow(Tabs.Register)
    val selected : StateFlow<Tabs> = _selected.asStateFlow()

    fun setSelecedTo(tab: Tabs) {
        _selected.value = tab
    }
}

enum class Tabs() {
    Login, Register
}
