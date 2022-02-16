package com.mobilegame.robozzle.presentation.ui.button

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination

data class NavigationButtonInfo(
    val buttonId: Int,
    val text: String,
    val width: Int,
    val height: Int,
    val color: Color,
//    val route: String,
    val destination: NavigationDestination,
    val arg: String,
    val enable: Boolean,
)
