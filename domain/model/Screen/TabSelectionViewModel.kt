package com.mobilegame.robozzle.domain.model.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.Tab
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TabSelectionViewModel(): ViewModel() {
//    private val _tabSelected: MutableStateFlow<Int> = MutableStateFlow(1)
//    val tabSeclected : StateFlow<Int> = _tabSelected.asStateFlow()


    fun setTabToLogin(tab: Tab) {
        viewModelScope.launch {
            tab.setSelecedTo(2)
        }
    }
    fun setTabToRegister(tab: Tab) {
        viewModelScope.launch {
            tab.setSelecedTo(1)
        }
//        infoLog("setTabTo", "$tabSelection")
//        viewModelScope.launch {
//            _tabSelected.emit(tabSelection)
//            _tabSelected.value = tabSelection
//        }
    }

//    fun getTabSelected(): Int = runBlocking {
//        var ret: Int = 1
//        tabSeclected.collect {
//            ret = it
//        }
//        ret
//    }
}

//class TabSelection() {
//
//}