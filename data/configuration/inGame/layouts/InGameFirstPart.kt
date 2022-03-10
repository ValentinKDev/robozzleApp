package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Rect
import com.mobilegame.robozzle.analyse.infoLog

object InGameFirstPart {
    val ratios = Ratios
    val size = Sizes

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.95F
        const val mapWidht = 0.95F
        const val playerIcon = 0.80F
    }
    object Sizes {
        var width: Int = 0
        var widthDp: Int = 0
        var height: Int = 0
        var heightDp: Int = 0
        var mapHeight: Int = 0
        var mapWidth: Int = 0
//        var playerIcon: Int = 0
    }

    fun init(context: Context) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = (widthFull).toInt()
        size.widthDp = (size.width / density).toInt()
        size.mapWidth = (size.widthDp * ratios.mapWidht).toInt()
        size.height = (heightFull * Ratios.height).toInt()
        size.heightDp = (size.height / density).toInt()
        size.mapHeight = (size.heightDp * ratios.mapHeight).toInt()
//        size.playerIcon = ()

        infoLog(" width ", "${size.width}")

        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" mapWidth ", "${size.mapWidth}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" mapHeight ", "${size.mapHeight}")
    }
}
