package com.mobilegame.robozzle.data.configuration.inGame.elements

import android.content.Context
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameSecondPart
import com.mobilegame.robozzle.utils.Extensions.toDp

data class CaseColoringIcon(
    val boxSize: Float,
    val density: Float,
    val ratio: Float,
) {
//    var ratioCanvas: Float = 0.75F
    var ratioCanvas: Float = 1.4F * ratio
    var canvasSize: Float = 0F
    var canvasSizeDp: Dp = Dp.Unspecified
    var radiusForBorder: Float = 0F
    var borderStroke: Float = 0F
    var radiusIn: Float = 0F
    var ratioBorder: Float = 0.1F

    init {
        canvasSize = boxSize * ratioCanvas
        canvasSizeDp = canvasSize.toDp(density)

        radiusForBorder = canvasSize / 2F
        radiusIn = (1F - ratioBorder) * radiusForBorder
        borderStroke = (radiusForBorder - radiusIn) * 2
    }
}