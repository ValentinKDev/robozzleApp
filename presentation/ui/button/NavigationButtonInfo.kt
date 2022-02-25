package com.mobilegame.robozzle.presentation.ui.button

import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

data class NavigationButtonInfo(
    val button: ButtonId,
    val buttonKey: Int = button.key,
    val text: String,
    val explicitTargetWidthRatio: Float? = null,
    val explicitTargetHeightRatio: Float? = null,
    val explicitInitialWidthRatio: Float? = null,
    val explicitInitialHeightRatio: Float? = null,
    val destination: NavigationDestination,
    val arg: String,
    val enable: Boolean,
) {
    val infoWindow = MainScreenWindowsInfos()
    val color = infoWindow.buttonColor

    val widthRatio = infoWindow.getWidthRatioFromId(button)
    val heightRatio: Float = infoWindow.getHeightRatioFromId(button)

    val targetWidthRatio: Float = infoWindow.getWidthRatioTargetFromId(button)
    val targetHeightRatio: Float = infoWindow.getHeightRatioTargetFromId(button)

    val initialWidthRatio: Float = explicitInitialWidthRatio ?: widthRatio
    val initialHeightRatio: Float = explicitInitialHeightRatio ?: heightRatio
}
