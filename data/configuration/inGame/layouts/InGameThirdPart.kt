package com.mobilegame.robozzle.data.configuration.inGame.layouts

import androidx.compose.ui.geometry.Rect

object InGameThirdPart {
    val ratios = Ratios
    val size = Sizes

    object Ratios {
        const val height = 1F
    }
    object Sizes {
        var width: Int = 0
        var height: Int = 0
    }

    fun init(window: Rect) {
        Sizes.width = (window.width).toInt()
        Sizes.height = (window.height * Ratios.height).toInt()
    }
}