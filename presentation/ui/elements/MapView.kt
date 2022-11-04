package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import gradientBackground

@Composable
fun MapView(param: MapViewParam) {
    //todo : put those calculs in a VM ?
//    val map = MapCleaner() clean mapParam
//    val map = mapParam

//    val caseNumberWidth = param.map[0].length
//    val caseNumberHeight = param.map.size
//
//    val calulByWidth = if (caseNumberWidth > caseNumberHeight) true else false
//
//    val padingRatio: Float = 1.0f / 20.0f
//
//    val caseSize: Float =
//        if (calulByWidth) param.widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))
//        else param.widthInt.toFloat() / (caseNumberHeight.toFloat() * (padingRatio + 1f))
//    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
//    val mapWidthtDP: Dp = param.widthInt.dp

    Box(Modifier
        .height(param.mapHeightDP)
        .width(param.mapWidthtDP)
        .background(grayDark3)
    ) {
        Column(
            Modifier.fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            param.map.forEachIndexed { rowIndex, rowString ->
                Row {
                    rowString.forEachIndexed { columnIndex, char ->
                        val caseColor = char
                        Box(Modifier
                            .background(Color.Transparent)
                            .size(param.caseSize.dp)
                            ,
                        ) {
//                           todo : might add clarity to augement the range of luminosity in the gradient so the difference between each case is clear and it compensate the fact that there is no padding
                            if (caseColor != '.') {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .gradientBackground(mapCaseColorList(caseColor), 135f)
                                        .graphicsLayer { shadowElevation = if (caseColor != '.') 7.dp.toPx() else 0.dp.toPx() }
                                ) { }
                            }
                        }
                    }
                }
            }
        }
    }
}