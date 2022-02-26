package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.presentation.res.*

@Composable
fun MapView(widthInt: Int, map: List<String>, modifier: Modifier = Modifier) {
    //todo : make a function to trunc every full column and line of '.' (if it preserver symmetry)
    //todo : put those calculs in a VM ?
    val caseNumberWidth = map[0].length
    val caseNumberHeight = map.size
    val padingRatio: Float = 1.0f / 20.0f
    val caseSize: Float = widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))
    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
    val mapWidthtDP: Dp = widthInt.dp
    val casePaddingDP: Dp = (caseSize / 20.0F).dp

    Box( modifier = Modifier
        .height(mapHeightDP)
        .width(mapWidthtDP)
        .background(grayDark3)
        .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            map.forEachIndexed { rowIndex, rowString ->
                Row {
                    rowString.forEachIndexed { columnIndex, char ->
                        val caseColor = char.toString()
                        Box(modifier = Modifier
                            .background(Color.Transparent)
                            .size(caseSize.dp)
//                            .padding(casePaddingDP)
                            ,
                        ) {
//                           todo : might add clarity to augement the range of luminosity in the gradient so the difference between each case is clear and it compensate the fact that there is no padding
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .gradientBackground(ColorsList(caseColor), 135f)
                                    .graphicsLayer { shadowElevation = if (caseColor != ".") 7.dp.toPx() else 0.dp.toPx() }
                            ) { }
                        }
                    }
                }
            }
        }
    }
}