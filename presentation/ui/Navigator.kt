package com.mobilegame.robozzle.presentation.ui

import androidx.navigation.NavDestination
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {
    var dest: MutableSharedFlow<String> = MutableSharedFlow()
    var des: SharedFlow<String> = dest.asSharedFlow()

    suspend fun navig(destination: NavigationDestination, argumentStr: String = "") {
        if (argumentStr.isEmpty()) dest.emit(destination.route)
        else dest.emit(destination.route + "/" + argumentStr)
    }
}
