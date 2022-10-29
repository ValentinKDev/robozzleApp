package com.mobilegame.robozzle.presentation.ui.button

import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

data class NavigationButtonInfo(
    val button: Screens,
    val route: Screens = button,
    val text: String,
    val enable: Boolean,
)
