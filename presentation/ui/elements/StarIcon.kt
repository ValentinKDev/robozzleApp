package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import androidx.compose.foundation.Canvas

@Preview
@Composable
fun testView() {
    val data = InGameFirstPart
    Canvas(Modifier.size(data.size.starIconDp.dp)) {
        drawRect(
            brush = Brush.linearGradient(
                listOf(
                    Color(0xff000078),
                    Color(0xff000098),
                    Color(0xff0000ba)
                )
            )
        )
    }
    StarIcon(data = data)
}

@Composable
fun StarIcon(data: InGameFirstPart) {
    DrawGlowingEffect(
        data = data,
        colorNeon =
        listOf(
            yellow1,
            yellow2,
            yellow3,
            yellow4,
            yellow5,
            yellow6,
            yellow7,
            yellow12,
        )
    )

    DrawStarPart(
        data = data,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2,
        colorLines = listOf(yellowDark2, yellowDark6),
    )
}

@Composable
fun DrawGlowingEffect(
    data: InGameFirstPart,
    colorNeon: List<Color>,
) {
    val sizeDp = data.size.starIconDp
    Box(modifier = Modifier
        .size(sizeDp.dp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(sizeDp.dp)
            ) {
                drawCircle(
                    brush = Brush.radialGradient( colorNeon ),
                    radius = center.x,
                    center = center
                )
            }
        }
    }
}

@Composable
fun DrawStarPart(
    data: InGameFirstPart,
    colorLeftSide: List<Color>,
    colorRightSide: List<Color>,
    colorBack: Color,
    colorLines: List<Color>,
) {
    val sizeDp = data.size.starIconDp
    Box(modifier = Modifier
        .size(sizeDp.dp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(data.size.starCanvasDp.dp)
            ){
//                val stroke = sizeDp / 50F
                val partRight = Path().apply {
                    moveTo(data.star.pTop.x, data.star.pTop.y)
                    lineTo(data.star.pRight.x, data.star.pRight.y)
                    lineTo(data.star.center.x, data.star.center.y)
                }
                val partRight2 = Path().apply {
                    moveTo(data.star.pTop.x, data.star.pTop.y)
                    lineTo(data.star.pRight.x, data.star.pRight.y)
                    lineTo(data.star.decenter.x, data.star.decenter.y)
                }
                val partLeft2 = Path().apply {
                    moveTo(data.star.pTop.x, data.star.pTop.y)
                    lineTo(data.star.pLeft.x, data.star.pLeft.y)
                    lineTo(data.star.decenter.x, data.star.decenter.y)
                }
                val partLeft = Path().apply {
                    moveTo(data.star.pTop.x, data.star.pTop.y)
                    lineTo(data.star.pLeft.x, data.star.pLeft.y)
                    lineTo(data.star.center.x, data.star.center.y)
                }

                for (angle in listOf(0F, 72F, 144F, 216F, 288F)) {
                    rotate(degrees = angle) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = data.star.pRight,
                            end = data.star.center,
                            strokeWidth = data.star.stroke
                        )
                        drawLine(
                            brush = Brush.verticalGradient(colorLines),
                            start = data.star.pLeft,
                            end = data.star.center,
                            strokeWidth = data.star.stroke
                        )
                    }

                }
            }
        }
    }
}