package com.mobilegame.robozzle.domain.model.Screen.InGame

import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.Density
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.*
import com.mobilegame.robozzle.domain.model.level.Level

class InGameLayout(level: Level, dens: Density, layoutCoordinates: LayoutCoordinates) {
    val firstPart = InGameFirstPart
    val secondPart = InGameSecondPart
    val thirdPart = InGameThirdPart
    val popup = InGamePopupWin
    val menu = InGameInstructionMenu

    private var density = 0F
    private var layoutCoordinates: LayoutCoordinates? = null

    init {
        density = dens.density
        val rect = layoutCoordinates.boundsInRoot()
        infoLog("init", "first Part")
        InGameFirstPart.init(rect)
        infoLog("init", "second Part")
        InGameSecondPart.init(rect, density, level)
        infoLog("init", "third Part")
        InGameThirdPart.init(rect)
        infoLog("init", "initMenu")
        InGameInstructionMenu.init(rect, level)
    }
//    fun setDensity(dens: Density) {density = dens.density}
//    fun setLayoutCoordinates(newLayoutCoordinates: LayoutCoordinates) { layoutCoordinates ?: run { layoutCoordinates = newLayoutCoordinates } }
//    fun setAllLayouts(level: Level) {
//        layoutCoordinates?.let {
//            val rect = it.boundsInRoot()
//            infoLog("init", "first Part")
//            firstPart.init(rect)
//            infoLog("init", "second Part")
//            secondPart.init(rect, density, level)
//            infoLog("init", "third Part")
//            thirdPart.init(rect)
//            infoLog("init", "initMenu")
//            menu.init(rect, level)
//        }
//    }
//
}