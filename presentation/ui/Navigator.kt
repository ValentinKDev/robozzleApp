package com.mobilegame.robozzle.presentation.ui

import androidx.navigation.NavDestination
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {
    var destination: MutableStateFlow<String> = MutableStateFlow(Screens.MainMenu.route)
    var dest: MutableSharedFlow<String> = MutableSharedFlow()
    var des: SharedFlow<String> = dest.asSharedFlow()

//    fun navigate(destination: NavigationDestination, argumentStr: String = "") {
//        if (argumentStr.isEmpty()) this.destination.value = destination.route
//        else this.destination.value = destination.route + "/" + argumentStr
//    }
    suspend fun navig(destination: NavigationDestination, argumentStr: String = "") {
        if (argumentStr.isEmpty()) dest.emit(destination.route)
        else dest.emit(destination.route + "/" + argumentStr)
    }
}
