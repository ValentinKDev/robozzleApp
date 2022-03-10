package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.*
import com.mobilegame.robozzle.domain.model.level.Level

class InGameLayout(level: Level, context: Context) {
    val firstPart = InGameFirstPart
    val secondPart = InGameSecondPart
    val thirdPart = InGameThirdPart
    val popup = InGamePopupWin
    val menu = InGameInstructionMenu

    private var density = 0F
    private var layoutCoordinates: LayoutCoordinates? = null

    init {
//        density = dens.density
        infoLog("density", "$density ")
//        val rect = layoutCoordinates.boundsInRoot()
        verbalLog("init", "first Part")
        InGameFirstPart.init(context)
        verbalLog("init", "second Part")
        InGameSecondPart.init(context , level)
        verbalLog("init", "third Part")
        InGameThirdPart.init(context)
        verbalLog("init", "initMenu")
        InGameInstructionMenu.init(context , level)
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