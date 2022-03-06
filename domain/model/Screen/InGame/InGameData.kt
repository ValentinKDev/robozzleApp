package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.configuration.inGame.*
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameLayout
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel


class InGameData(): ViewModel() {
    private var notInitialized = true

    val text = InGameText
    val colors = InGameColors
    val layout = InGameLayout()

    fun init(lvl: RobuzzleLevel, context: Context, dens: Density, layoutCoordinates: LayoutCoordinates) {
        verbalLog("init", "InGameData")
        if (notInitialized) {
            infoLog("init", "density")
            layout.setDensity(dens)
            infoLog("init", "layoutCoor")
            layout.setLayoutCoordinates(layoutCoordinates)
            infoLog("init", "all layouts")
            layout.setAllLayouts(lvl)


            notInitialized= false
        }
    }
}