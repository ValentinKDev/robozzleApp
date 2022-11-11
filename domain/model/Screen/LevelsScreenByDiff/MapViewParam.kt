package com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MapViewParam(val map: List<String>, val widthInt: Int) {
//    var widthInt: Int = 0
    val caseNumberWidth = map[0].length
    val caseNumberHeight = map.size

    val calulByWidth = if (caseNumberWidth > caseNumberHeight) true else false

    val padingRatio: Float = 1.0f / 20.0f

    val caseSize: Float =
        if (calulByWidth) widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))
        else widthInt.toFloat() / (caseNumberHeight.toFloat() * (padingRatio + 1f))
    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
    val mapWidthDP: Dp = widthInt.dp

//    fun create(width: Int): MapViewParam {
//        widthInt = width
//    }
}