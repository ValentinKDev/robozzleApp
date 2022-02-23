package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.Extensions.isDirection
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.MapBox
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.MapCase
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayerDirectionIcon
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Star

@Composable
fun MapView(widthInt: Int, map: List<String>) {
    //todo : make a function to trunc every full column and line of '.' (if it preserver symmetry)
    //todo : put those calculs in a VM ?
    val caseNumberWidth = map[0].length
    val caseNumberHeight = map.size
    val padingRatio: Float = 1.0f / 20.0f
    val caseSize: Float = widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))
    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
    val mapWidthtDP: Dp = widthInt.dp
    val casePaddingDP: Dp = (caseSize / 20.0F).dp
//    errorLog("map size", "w $caseNumberWidth h $caseNumberHeight caseSize $caseSize")

    Box( modifier = Modifier
        .height(mapHeightDP)
        .width(mapWidthtDP)
//        .drawBehind {  }
        .background(grayDark2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            map.forEachIndexed { rowIndex, rowString ->
//                infoLog("rowIndex", "$rowIndex")
                Row {
                    rowString.forEachIndexed { columnIndex, char ->
                        val caseColor = char.toString()
                        Box(modifier = Modifier
                            .background(Color.Transparent)
                            .size(caseSize.dp)
//                            .padding(casePaddingDP)
                        ) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                        //todo : might add clarity to augement the range of luminosity in the gradient so the difference between each case is clear and it compensate the fact that there is no padding
                                    .gradientBackground( ColorsList(caseColor), 135f )
                            ) { }
                        }
                    }
                }
            }
        }
    }
}