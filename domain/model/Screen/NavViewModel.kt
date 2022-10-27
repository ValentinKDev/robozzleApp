package com.mobilegame.robozzle.domain.model.Screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.*

class NavViewModel(private val navigator: Navigator): ViewModel() {
    fun navigateTo(destination: NavigationDestination, argStr: String = "", delayTiming: Long? = null) {
        delayTiming?.let {
            viewModelScope.launch(Dispatchers.IO) {
                delay(delayTiming)
                navigator.navig(destination, argStr)
            }
        } ?: viewModelScope.launch {
            navigator.navig(destination, argStr)
        }
    }

    //todo: why storing the int ???
    fun storeIntAndNavigateTo(destination: NavigationDestination, int: Int, delayTiming: Long? = null, context: Context) = runBlocking {
        val storing = viewModelScope.async {
            ArgumentsDataStoreViewModel(context).storeLevelNumberArg(levelId = int)
        }
        storing.await()
        navigateTo(destination, int.toString(), delayTiming)
    }
}