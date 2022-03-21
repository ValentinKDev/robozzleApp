package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.core.*
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.presentation.ui.myleveltest

@Preview
@Composable
fun testView() {
    val ctxt = LocalContext.current
    val data = InGameFirstPart
    data.init(ctxt, myleveltest)
    Canvas(Modifier.size(data.size.starBoxDp)) {
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
    StarIcon(data)
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
//            yellow12,
            Color.Transparent
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
private fun DrawGlowingEffect(
    data: InGameFirstPart,
    colorNeon: List<Color>,
) {
    val transition = rememberInfiniteTransition()
    val ratioAnim by transition.animateFloat(
        initialValue = 0.8F,
        targetValue = 0.92F,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
//                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val sizeDp = data.size.starBoxDp
    Box(modifier = Modifier
        .size(sizeDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(sizeDp * ratioAnim)
//                .fillMaxSize()
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
private fun DrawStarPart(
    data: InGameFirstPart,
    colorLeftSide: List<Color>,
    colorRightSide: List<Color>,
    colorBack: Color,
    colorLines: List<Color>,
) {
    val sizeDp = data.size.starBoxDp
    Box(modifier = Modifier
        .size(sizeDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(data.size.starCanvasDp)
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