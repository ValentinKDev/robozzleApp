package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.*
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.Level


//class InGameData(lvl: Level, context: Context): ViewModel() {
class InGameData(lvl: Level, context: Context) {
    val text = InGameText
    val colors = InGameColors
    var layout: InGameLayout = InGameLayout(lvl, context)

    init {
        infoLog("Init", "In Game Data")
        infoLog("lvl.playerInGame", "${lvl.playerInitial}")
//        val screenData = ScreenDataStoreViewModel(context)
//        val dens = screenData.density
//        val windowCoordinates: Rect = screenData.getWindowCoordinates() ?: Rect.Zero
//        errorLog("density", "$dens")
//        errorLog("windowRect", "${windowCoordinates}")
//        errorLog("density", "${context.resources.displayMetrics.heightPixels}")

    }
}