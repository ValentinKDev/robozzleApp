package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Rect
import com.mobilegame.robozzle.analyse.infoLog

object InGameThirdPart {
    val ratios = Ratios
    val size = Sizes

    object Ratios {
        const val height = 1F
        const val buttonWidth = 0.15F
        const val buttonHeight = 0.5F

    }
    object Sizes {
        var width: Int = 0
        var widthDp: Int = 0
        var height: Int = 0
        var heightDp: Int = 0
//        var button = 0
        var buttonWidth = 0
        var buttonHeight = 0
    }

    fun init(context: Context) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = (widthFull).toInt()
        size.widthDp = (size.width / density).toInt()
        size.height = (heightFull * (Ratios.height / (Ratios.height + InGameFirstPart.ratios.height + InGameSecondPart.ratios.height))).toInt()
        size.heightDp = (size.height / density).toInt()
//        size.button = (size.widthDp * ratios.height).toInt()
        size.buttonWidth = (size.widthDp * ratios.buttonWidth).toInt()
        size.buttonHeight = (size.heightDp * ratios.buttonHeight).toInt()
        infoLog(" width ", "${size.width}")
        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" buttonWidth ", "${size.buttonWidth}")
        infoLog(" buttonHeight ", "${size.buttonWidth}")
    }
}