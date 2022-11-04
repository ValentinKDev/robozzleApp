package com.mobilegame.robozzle.data.layout.inGame.layouts

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog

object InGameThirdPart {
    val ratios = Ratios
    val sizes = Sizes
    var initiated = false

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

        sizes.width = (widthFull).toInt()
        sizes.widthDp = (sizes.width / density).toInt()
        sizes.height = (heightFull * (Ratios.height / (Ratios.height + InGameFirstPart.ratios.height + InGameSecondPart.ratios.height))).toInt()
        sizes.heightDp = (sizes.height / density).toInt()
//        size.button = (size.widthDp * ratios.height).toInt()
        sizes.buttonWidth = (sizes.widthDp * ratios.buttonWidth).toInt()
        sizes.buttonHeight = (sizes.heightDp * ratios.buttonHeight).toInt()

        initiated = true
        infoLog(" width ", "${sizes.width}")
        infoLog(" widthDp ", "${sizes.widthDp}")
        infoLog(" height ", "${sizes.height}")
        infoLog(" heightDp ", "${sizes.heightDp}")
        infoLog(" buttonWidth ", "${sizes.buttonWidth}")
        infoLog(" buttonHeight ", "${sizes.buttonWidth}")
    }
}