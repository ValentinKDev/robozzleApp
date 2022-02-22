package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.presentation.res.*

@Composable
fun RankingIcon(sizeAtt: Int) {
    Canvas(
        modifier = Modifier
            .width((sizeAtt.toFloat() * (6.0F / 7.0F)).dp)
            .height(sizeAtt.dp)
        ,
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        errorLog("width", "${size}")

        drawRect(
            brush = Brush.horizontalGradient(colors = listOf(greendark3, greendark9)),
            topLeft = Offset(x = 0F, y = canvasHeight * (2.0F/5.0F)),
            size = Size(width = canvasWidth / 3.0F - 2, height = canvasHeight * (3.0F/5.0F)),
        )
        drawRect(
            brush = Brush.horizontalGradient(colors = listOf(blueDark0, blueDark6)),
            topLeft = Offset(x = canvasWidth * (1.0F/3.0F) - 2, y = 0F),
            size = Size(width = canvasWidth / 3.0F + 4, height = (canvasHeight * (5.0F/5.0F))),
        )
        drawRect(
            brush = Brush.horizontalGradient(colors = listOf(redDark0, redDark6)),
            topLeft = Offset(x = canvasWidth * (2.0F/3.0F) + 2, y = canvasHeight * (1.0F/5.0F)),
            size = Size(width = canvasWidth / 3.0F, height = (canvasHeight * (4.0F/5.0F))),
        )
    }
}
