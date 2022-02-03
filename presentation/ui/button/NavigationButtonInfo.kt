package com.mobilegame.robozzle.presentation.ui.button

import androidx.compose.ui.graphics.Color

data class NavigationButtonInfo(
    val text: String,
    val width: Int,
    val height: Int,
    val color: Color,
    val route: String,
    val enable: Boolean,
)
