package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.presentation.res.green0
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Preview
@Composable
fun test() {
    PlayerIcon(widhtDp = 100, direction = Direction(1,0))
    PlayerIcon(widhtDp = 9, direction = Direction(1,0))
    PlayerIcon(widhtDp = 10, direction = Direction(1,0))
    PlayerIcon(widhtDp = 9, direction = Direction(1,0))
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
                val strokeWidthBig = 0.06F * widhtDp.toFloat()
                val strokeWidthMedium = 0.05F * widhtDp.toFloat()
                val strokeWidthSmall = 0.04F * widhtDp.toFloat()

                val height = size.height
                val width = size.width

                val start = 0F
                val end = width
                val top = 0F
                val bottom = height

                val yCenter = (bottom - top) / 2F

                val pBottomCenter = Offset( x = width / 5F, y = yCenter)
                val pBottomLeft = Offset(x = start,y = 0.07F * end)
                val pBottomRight = Offset(x = start,y = 0.93F * end)

                val trianglePathLeftHalf = Path().apply {
                    moveTo(width, yCenter)
                    lineTo(pBottomLeft.x, pBottomLeft.y)
                    lineTo(pBottomCenter.x, pBottomCenter.y)
                }
                val trianglePathRightHalf = Path().apply {
                    moveTo(width, yCenter)
                    lineTo(pBottomRight.x, pBottomRight.y)
                    lineTo(pBottomCenter.x, pBottomCenter.y)
                }

                rotate(
                    pivot = center,
                    degrees = when (direction.ToChar()) {
                        'r' -> 0F
                        'd' -> 90F
                        'l' -> 180F
                        'u' -> 270F
                        else -> 45F
                    }
                ) {

                    drawLine(
                        start = pBottomCenter,
                        end = Offset(end, yCenter),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
//                        strokeWidth = 4F
                    )
                    //ext rigth
                    drawLine(
                        start = Offset(width, yCenter),
                        end = pBottomRight,
                        color = armorColor,
                        cap = cap,
//                        strokeWidth = strokeWidthMedium
                        strokeWidth = strokeWidthSmall
//                        strokeWidth = 5F
                    )
                    //ext left
                    drawLine(
                        start = pBottomLeft,
                        end = Offset(width, yCenter),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
//                        strokeWidth = strokeWidthMedium
//                        strokeWidth = 5F
                    )

                    //bottom right
                    drawLine(
                        start = pBottomCenter,
                        end = pBottomRight,
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
//                        strokeWidth = strokeWidthBig,
//                        strokeWidth = 6F
                    )
                    //bottom left
                    drawLine(
                        start = pBottomCenter,
                        end = pBottomLeft,
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
//                        strokeWidth = strokeWidthBig,
//                        strokeWidth = 6F
                    )

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
                    //bottom right neon
                    val baseOffsetLeft = pBottomCenter.minus(pBottomLeft)
                    val newOffset = Offset(start + (baseOffsetLeft.x * 0.5F), (0.93F * bottom) + (baseOffsetLeft.y * 0.5F))
                    val pNeonLeftEnd = Offset(pBottomCenter.x - (baseOffsetLeft.x * 0.5F) , pBottomCenter.y - (baseOffsetLeft.y * 0.5F))
                    val pNeonRightEnd = Offset(pBottomCenter.x - (baseOffsetLeft.x * 0.5F) , pBottomCenter.y - (baseOffsetLeft.y * 0.5F))
                    drawLine(
                        start = pBottomCenter,
                        end = pNeonLeftEnd,
                        color = Color(0xFFFFB020),
                        cap = cap,
                        strokeWidth = 5F,
                    )
                    rotate(degrees = 45F + 184F, pivot = pBottomCenter) {
                        drawLine(
                            start = pBottomCenter,
                            end = pNeonLeftEnd,
                            color = Color(0xFFFFB020),
                            cap = cap,
                            strokeWidth = 5F,
                        )
                    }
                    drawArc(
//                        brush = Brush.linearGradient(listOf(
                        brush = Brush.verticalGradient(listOf(
                            Color(0xFFFFB020),
                            Color(0xFFAF7000),
                        )),
//                        color = Color(0xFFFFB020),
//                        color = Color(0xFFDFDF00),
                        useCenter = true,
                        topLeft = Offset.Zero,
                        startAngle = 148F,
                        sweepAngle = 64F,
//                        size =
                    )
                }
            }
       }
   }
}