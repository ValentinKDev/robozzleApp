package com.mobilegame.robozzle.presentation.ui.button

import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

data class NavigationButtonInfo(
    val button: Screens,
    val route: Screens = button,
//    val buttonKey: Int = button.key,
//    val destination: NavigationDestination,

    val text: String,
    val arg: String = "",
    val enable: Boolean,
) {
    val infoWindow = MainScreenWindowsInfos()
    val color = infoWindow.buttonColor
}
