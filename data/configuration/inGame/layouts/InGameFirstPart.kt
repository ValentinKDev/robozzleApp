package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level

object InGameFirstPart {
    val ratios = Ratios
    val size = Sizes

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.95F
        const val mapWidht = 0.95F
        const val playerIcon = 0.75F
        const val starIcon = 0.8F
//        const val playerIcon = 0.8F
    }
    object Sizes {
        var width: Int = 0
        var widthDp: Int = 0
        var height: Int = 0
        var heightDp: Int = 0
        var mapHeightDp: Int = 0
        var mapWidth: Int = 0
        var mapCaseDp: Int = 0
        var starIconDp: Int = 0
        var playerIconDp: Int = 0
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = (widthFull).toInt()
        size.widthDp = (size.width / density).toInt()
        size.mapWidth = (size.widthDp * ratios.mapWidht).toInt()
        size.height = (heightFull * Ratios.height).toInt()
        size.heightDp = (size.height / density).toInt()
        size.mapHeightDp = (size.heightDp * ratios.mapHeight).toInt()
        size.mapCaseDp = (size.mapHeightDp / level.map.size)
//        size.playerIcon = ()
        size.starIconDp = (size.mapCaseDp * ratios.starIcon).toInt()
        size.playerIconDp = (size.mapCaseDp * ratios.playerIcon).toInt()


        infoLog(" width ", "${size.width}")

        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" mapWidth ", "${size.mapWidth}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" mapHeight ", "${size.mapHeightDp}")
        infoLog(" mapCaseDp ", "${size.mapCaseDp}")
        infoLog(" starIconDp ", "${size.starIconDp}")
    }
}
