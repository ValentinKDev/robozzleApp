package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.layout.inGame.*
import com.mobilegame.robozzle.data.layout.inGame.elements.Trash
import com.mobilegame.robozzle.domain.model.level.Level


//class InGameData(lvl: Level, context: Context): ViewModel() {
class InGameData(lvl: Level, context: Context) {
    val text = InGameText
    val colors = InGameColors
    var layout: InGameLayout = InGameLayout(lvl, context)

    init {
        infoLog("Init", "In Game Data")
        infoLog("lvl.playerInGame", "${lvl.playerInitial}")
    }
}