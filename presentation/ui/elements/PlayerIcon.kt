package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Preview
@Composable
fun TestIt() {
    PlayerIcon(widhtDp = 100)
}

@Composable
fun PlayerIcon(widhtDp: Int) {
   Box(modifier = Modifier
       .size(widhtDp.dp)
   ){
       CenterComposable {
           Box(modifier = Modifier
               .size((0.9F * widhtDp ).dp)
               .drawBehind {
                   val height = size.height
                   val width = size.width

                   val start = 0F
                   val end = width
                   val top = 0F
                   val bottom = height

                   val yCenter = (bottom - top) / 2F

                   val trianglePath = Path().apply {
                       moveTo(start, 0.07F * end)
                       lineTo(width, yCenter)
                       lineTo(start, 0.93F * end)
                       lineTo(width / 5F, yCenter)
                   }
                   drawPath(trianglePath, Color(0xFFEE6615))

                   val armorColor = yellowDark2
                   val cap = StrokeCap.Round
                   val strokeWidth = 6F
                   drawLine(
                       start = Offset(width / 5F, yCenter),
                       end = Offset(end, yCenter),
                       color = armorColor,
                       cap = cap,
                       strokeWidth = strokeWidth
                   )

                   drawLine(
                       start = Offset(width, yCenter),
                       end = Offset(start, 0.93F * bottom),
                       color = armorColor,
                       cap = cap,
//                       strokeWidth = strokeWidth,
                       strokeWidth = 5F,
                   )
                   drawLine(
                       start = Offset(start, 0.93F * bottom),
                       end = Offset(width / 5F, yCenter),
                       color = armorColor,
                       cap = cap,
//                       strokeWidth = strokeWidth,
                       strokeWidth = 5F,
                   )

                   drawLine(
                       start = Offset(x = start, y = 0.07F * end),
                       end = Offset(width, yCenter),
                       color = armorColor,
                       cap = cap,
                       strokeWidth = strokeWidth
                   )
                   drawLine(
                       start = Offset(width / 5F, yCenter),
                       end = Offset(x = start, y = 0.07F * end),
                       color = armorColor,
                       cap = cap,
                       strokeWidth = strokeWidth
                   )
               }
           )
       }
   }
}

class CustomShape(): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}