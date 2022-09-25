package com.mobilegame.robozzle.presentation.ui

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class Navigator {
    var dest: MutableSharedFlow<String> = MutableSharedFlow()
    var des: SharedFlow<String> = dest.asSharedFlow()

    suspend fun navig(destination: NavigationDestination, argumentStr: String = "") {
        if (argumentStr.isEmpty()) dest.emit(destination.route)
        else dest.emit(destination.route + "/" + argumentStr)
    }

    private var _forceReload  = MutableSharedFlow<String>()
    var forceReload = _forceReload.asSharedFlow()

    fun reload_launcher(setTo: String = "") = runBlocking(Dispatchers.IO) {
        errorLog("Navigator::reload_launcher", "start")
        _forceReload.emit(setTo)
        errorLog("Navigator::reload_launcher", "end forceReload = ${forceReload.equals(true)}")
    }
}
