package com.mobilegame.robozzle.domain.model.Screen.InGame

import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.configuration.inGame.*
import com.mobilegame.robozzle.domain.model.level.Level


class InGameData(lvl: Level, dens: Density, layoutCoordinates: LayoutCoordinates): ViewModel() {
    val text = InGameText
    val colors = InGameColors
    val layout = InGameLayout(lvl, dens, layoutCoordinates)
}