package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import androidx.compose.ui.layout.LayoutCoordinates
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
        infoLog("density", "$density ")
        verbalLog("init", "first Part")
        InGameFirstPart.init(context, level)
        verbalLog("init", "second Part")
        InGameSecondPart.init(context , level)
        verbalLog("init", "third Part")
        InGameThirdPart.init(context)
        verbalLog("init", "initMenu")
        InGameInstructionMenu.init(context , level)
    }
}