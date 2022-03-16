package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.toInt

@Preview
@Composable
fun TestIt() {
    PlayerIcon(widhtDp = 100, Direction(1,0))
}

@Composable
fun PlayerIcon(widhtDp: Int, direction: Direction) {
    Box(modifier = Modifier
        .size(widhtDp.dp)
    ){
        CenterComposable {
            androidx.compose.foundation.Canvas(modifier = Modifier.size((0.9F * widhtDp).dp)) {
                val armorColor = Color.Black
                val cap = StrokeCap.Round
                val strokeWidth = 6F

                val height = size.height
                val width = size.width

                val start = 0F
                val end = width
                val top = 0F
                val bottom = height

                val yCenter = (bottom - top) / 2F

                val trianglePathLeftHalf = Path().apply {
                    moveTo(start, 0.07F * end)
                    lineTo(width, yCenter)
                    lineTo(width / 5F, yCenter)
                }
                val trianglePathRightHalf = Path().apply {
//                       moveTo(start, 0.07F * end)
                    moveTo(width, yCenter)
                    lineTo(start, 0.93F * end)
                    lineTo(width / 5F, yCenter)
                }

                rotate(
                    pivot = center,
                    degrees = when (direction.ToChar()) {
                        'u' -> 90F
                        'd' -> 270F
                        'r' -> 0F
                        'l' -> 180F
                        else -> 45F
                    }
                ) {
                    drawPath(
                        trianglePathLeftHalf,
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFA23807),
                                Color(0xFFDD5412),
                                Color(0xFFEE6615),
                            )
                        )
                    )
                    drawPath(
                        trianglePathRightHalf,
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFEE6615),
                                Color(0xFFDD5412),
                                Color(0xFFBD4409),
                                Color(0xFFA23807),
                            )
                        )
                    )

                    drawLine(
                        start = Offset(width / 5F, yCenter),
                        end = Offset(end, yCenter),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = 4F
                    )
                    //ext rigth
                    drawLine(
                        start = Offset(width, yCenter),
                        end = Offset(start, 0.93F * bottom),
                        color = armorColor,
                        cap = cap,
//                       strokeWidth = strokeWidth,
                        strokeWidth = 5F,
                    )
                    //ext left
                    drawLine(
                        start = Offset(x = start, y = 0.07F * end),
                        end = Offset(width, yCenter),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = 5F,
                    )

                    //bottom right
                    drawLine(
                        start = Offset(width / 5F, yCenter),
                        end = Offset(start, 0.93F * bottom),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidth,
                    )
                    //bottom left
                    drawLine(
                        start = Offset(width / 5F, yCenter),
                        end = Offset(x = start, y = 0.07F * end),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidth
                    )

                }
            }
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