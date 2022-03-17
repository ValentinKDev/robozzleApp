package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import io.ktor.http.*

@Preview
@Composable
fun testView() {
    val data = InGameFirstPart
    data.size.starIconDp = 50
    StarIcon(data = data)
}

@Composable
fun StarIcon(data: InGameFirstPart) {
    DrawStarPart(
        data = data,
        rotation = 0F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2,
        colorLines = listOf(yellowDark2, yellowDark6),
        colorNeon = listOf(
            yellow0,
            yellow1,
            yellow1,
            yellow2,
            yellow3,
            yellow5,
            yellow7,
            yellow8,
            yellow9,
            yellow10,
            yellow11,
            yellow12,
//            yellow12,
            Color.Transparent,
            Color.Transparent,
        )
    )
//    DrawStarPart(
//        data = data,
//        rotation = 72F,
//        colorLeftSide = listOf(yellowDark2, yellowDark2),
//        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
//        colorBack = yellowDark2,
//        colorLines = listOf(yellowDark2, yellowDark6),
//        colorNeon = listOf(
//            yellow1,
//            yellow1,
//            yellow2,
//            yellow3,
//            yellow5,
//            yellow7,
//            yellow8,
//            yellow9,
//            yellow10,
//            yellow11,
//            yellow12,
//            Color.Transparent,
//            Color.Transparent,
//        )
//    )
//    DrawStarPart(
//        data = data,
//        rotation = 144F,
//        colorLeftSide = listOf(yellowDark2, yellowDark2),
//        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
//        colorBack = yellowDark2,
//        colorLines = listOf(yellowDark2, yellowDark6),
//        colorNeon = listOf(
//            yellow1,
//            yellow1,
//            yellow2,
//            yellow3,
//            yellow5,
//            yellow7,
//            yellow8,
//            yellow9,
//            yellow10,
//            yellow11,
//            yellow12,
//            Color.Transparent,
//            Color.Transparent,
//        )
//    )
//    DrawStarPart(
//        data = data,
//        rotation = 216F,
//        colorLeftSide = listOf(yellowDark2, yellowDark2),
//        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
//        colorBack = yellowDark2,
//        colorLines = listOf(yellowDark2, yellowDark6),
//        colorNeon = listOf(
//            yellow1,
//            yellow1,
//            yellow2,
//            yellow3,
//            yellow5,
//            yellow7,
//            yellow8,
//            yellow9,
//            yellow10,
//            yellow11,
//            yellow12,
//            Color.Transparent,
//            Color.Transparent,
//        )
//    )
//    DrawStarPart(
//        data = data,
//        rotation = 288F,
//        colorLeftSide = listOf(yellowDark2, yellowDark2),
//        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
//        colorBack = yellowDark2,
//        colorLines = listOf(yellowDark2, yellowDark6),
//        colorNeon = listOf(
//            yellow1,
//            yellow1,
//            yellow2,
//            yellow3,
//            yellow5,
//            yellow7,
//            yellow8,
//            yellow9,
//            yellow10,
//            yellow11,
//            yellow12,
//            Color.Transparent,
//            Color.Transparent,
//        )
//    )
}
@Composable
fun DrawStarPart(
    data: InGameFirstPart,
//    vm: GameDataViewModel,
//    widhtDp: Int,
    rotation: Float,
    colorLeftSide: List<Color>,
    colorRightSide: List<Color>,
    colorBack: Color,
    colorLines: List<Color>,
    colorNeon: List<Color>
) {
    val sizeDp = data.size.starIconDp
    Box(modifier = Modifier
        .size(sizeDp.dp)
    ){
        CenterComposable {
            Box(modifier = Modifier
                .size((0.9F * sizeDp).dp)
                .drawBehind {
                    val canvasHeight = size.height
                    val canvasWidth = size.width
                    val xCenter = size.width / 2F
                    val yCenter = size.width / 2F

                    val horizontal = 0.343F * size.height
                    val vertical3 = xCenter + (0.118F * size.width)
                    val vertical2 = xCenter - (0.118F * size.width)
                    val vertical1 = (0.022F * size.width)

                    val stroke = sizeDp / 50F

                    val pTopx = xCenter
                    val pTopy = vertical1

                    val pLeftx = vertical2
                    val pLefty = horizontal

                    val pRightx = vertical3
                    val pRighty = horizontal

                    val decenterRightx = center.x + 0.01F * canvasWidth
                    val decenterRighty = center.y + 0.01F * canvasWidth

                    val partRight = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pRightx, pRighty)
                        lineTo(xCenter, yCenter)
                    }
                    val partRight2 = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pRightx, pRighty)
                        lineTo(decenterRightx, decenterRighty)
                    }
                    val partLeft2 = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pLeftx, pLefty)
                        lineTo(decenterRightx, decenterRighty)
                    }
                    val partLeft = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pLeftx, pLefty)
                        lineTo(xCenter, yCenter)
                    }
//                    drawRect(color = whiteDark4)
                    drawRect(brush = Brush.linearGradient( listOf(Color(0xff000078), Color(0xff000098), Color(0xff0000ba)) ))
                    val sideDistance = Offset(pLeftx, pLefty).minus(Offset(pTopx, pTopy)).getDistance()
                    rotate(
                        degrees = rotation + 90 + 21,
                        pivot = Offset(pLeftx , pLefty)
                    ) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = colorNeon
                            ),
                            topLeft = Offset(pLeftx - sideDistance, pLefty),
                            size = Size(sideDistance, 20F)
                        )
                    }
                    rotate(
                        degrees = rotation + 72 + 178,
                        pivot = Offset(pRightx, pRighty)
                    ) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = colorNeon
                            ),
                            topLeft = Offset(pRightx, pRighty),
                            size = Size(sideDistance, 20F)
                        )
                    }
                    rotate(72F){
                        rotate(
                            degrees = rotation + 90 + 21,
                            pivot = Offset(pLeftx , pLefty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pLeftx - sideDistance, pLefty),
                                size = Size(sideDistance, 20F),
                            )
                        }
                        rotate(
                            degrees = rotation + 72 + 178,
                            pivot = Offset(pRightx, pRighty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pRightx, pRighty),
                                size = Size(sideDistance, 20F)
                            )
                        }
                    }
                    rotate(144F){
                        rotate(
                            degrees = rotation + 90 + 21,
                            pivot = Offset(pLeftx , pLefty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pLeftx - sideDistance, pLefty),
                                size = Size(sideDistance, 20F),
                            )
                        }
                        rotate(
                            degrees = rotation + 72 + 178,
                            pivot = Offset(pRightx, pRighty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pRightx, pRighty),
                                size = Size(sideDistance, 20F)
                            )
                        }
                    }
                    rotate(216F){
                        rotate(
                            degrees = rotation + 90 + 21,
                            pivot = Offset(pLeftx , pLefty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pLeftx - sideDistance, pLefty),
                                size = Size(sideDistance, 20F),
                            )
                        }
                        rotate(
                            degrees = rotation + 72 + 178,
                            pivot = Offset(pRightx, pRighty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pRightx, pRighty),
                                size = Size(sideDistance, 20F)
                            )
                        }
                    }
                    rotate(288F){
                        rotate(
                            degrees = rotation + 90 + 21,
                            pivot = Offset(pLeftx , pLefty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pLeftx - sideDistance, pLefty),
                                size = Size(sideDistance, 20F),
                            )
                        }
                        rotate(
                            degrees = rotation + 72 + 178,
                            pivot = Offset(pRightx, pRighty)
                        ) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colorNeon
                                ),
                                topLeft = Offset(pRightx, pRighty),
                                size = Size(sideDistance, 20F)
                            )
                        }
                    }


                    rotate(degrees = 0F) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pRightx, pRighty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pLeftx, pLefty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                    }
                    rotate(degrees = 72F) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pRightx, pRighty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pLeftx, pLefty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                    }
                    rotate(degrees = 144F) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pRightx, pRighty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pLeftx, pLefty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                    }
                    rotate(degrees = 216F) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pRightx, pRighty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pLeftx, pLefty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                    }
                    rotate(degrees = 288F) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pRightx, pRighty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = Offset(pLeftx, pLefty),
                            end = Offset(xCenter, yCenter),
                            strokeWidth = stroke
                        )
                    }
                }
            )
        }
    }
}

//fun Offset.distanceTo(offset: Offset): Float {
//    val base: Offset =
//}