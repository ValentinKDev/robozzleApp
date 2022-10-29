package com.mobilegame.robozzle.presentation.ui.Navigation

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.utils.Extensions.addNavArg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class Navigator {
    var dest: MutableSharedFlow<String> = MutableSharedFlow()
    var des: SharedFlow<String> = dest.asSharedFlow()

    suspend fun navig(destination: NavigationDestination, argumentStr: String = "") {
        val fullRoute =
            if (argumentStr.isEmpty()) destination.route
            else destination.route.addNavArg(argumentStr)
        infoLog("Navigator::navig", "fullRoute : $fullRoute")
        dest.emit(fullRoute)
    }

    private var _forceReload  = MutableSharedFlow<String>()
    var forceReload = _forceReload.asSharedFlow()

    fun reload_launcher(setTo: String = "") = runBlocking(Dispatchers.IO) {
        errorLog("Navigator::reload_launcher", "start")
        _forceReload.emit(setTo)
        errorLog("Navigator::reload_launcher", "end forceReload = ${forceReload.equals(true)}")
    }
}
