package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.black6

@Composable
fun PointerShape() {
    Canvas(
        Modifier.size(15.dp)
    ) {
        val width = size.width
        val trianglePath = Path().apply {
            moveTo(width/2F, width/3F)
            lineTo(width, width)
            lineTo(0f, width)
        }
        drawPath(
            color = black6 ,
            path = trianglePath
        )
    }
}

