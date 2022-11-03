package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import com.mobilegame.robozzle.data.layout.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.data.layout.inGame.InGameColors
import com.mobilegame.robozzle.presentation.ui.Navigation.myleveltest

@Preview
@Composable
fun testView() {
    val ctxt = LocalContext.current
    val data = InGameFirstPart
    data.init(ctxt, myleveltest)
    Canvas(Modifier.size(data.size.starBoxDp)) {
        drawRect(
            brush = Brush.linearGradient(
//                InGameColors.gradientOfBlue
//                InGameColors.gradientOfGray
                InGameColors.gradientOfGreen
//                InGameColors.gradientOfRed
            )
        )
    }
    StarIcon(data, InGameColors)
}

@Composable
fun StarIcon(data: InGameFirstPart, colors: InGameColors) {
    DrawBackGlowingEffect( data = data, colors = colors)
    DrawStarPart( data = data, colors = colors)
    DrawFrontNeonEffect(data = data, colors = colors)
}


@Composable
fun DrawFrontNeonEffect(data: InGameFirstPart, colors: InGameColors) {
    val transition = rememberInfiniteTransition()
    val ratioAnim by transition.animateFloat(
        initialValue = 0.8F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier
        .size(data.size.starBoxDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier.size(data.star.starNeonBoxFrontDp * ratioAnim)) {
                drawCircle( brush = Brush.radialGradient( colors.star.neongradient ) )
            }
        }
    }
}

@Composable
private fun DrawBackGlowingEffect(
    data: InGameFirstPart,
    colors: InGameColors
) {
    val transition = rememberInfiniteTransition()
    val ratioAnim by transition.animateFloat(
        initialValue = 0.90F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animColor1 by transition.animateColor(
        initialValue = Color(0xAAAAAA15),
        targetValue = Color(0xEEAAAA15),
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animColor2 by transition.animateColor(
        initialValue = Color(0x88AAAA15),
        targetValue = Color(0xAAAAAA15),
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animColor3 by transition.animateColor(
        initialValue = Color(0x44AAAA15),
        targetValue = Color(0x55AAAA15),
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val list = listOf(
        Color(0xFFFFFF15),
        animColor1,
        animColor2,
        animColor3,
        Color.Transparent
    )

    val sizeDp = data.size.starBoxDp
    Box(modifier = Modifier
        .size(sizeDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(sizeDp * ratioAnim)
            ) {
                drawRect(
                    brush = Brush.radialGradient(
                        list
                    ),
                )
//                drawCircle(
//                    brush = Brush.radialGradient( colors.star.backGlowingEffect ),
//                    radius = center.x,
//                    center = center
//                )
            }
        }
    }
}

@Composable
private fun DrawStarPart(
    data: InGameFirstPart,
    colors: InGameColors,
) {
    val sizeDp = data.size.starBoxDp
    Box(modifier = Modifier
        .size(sizeDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(data.size.starCanvasDp)
            ){
                val starPart = Path().apply {
                    moveTo(data.star.pTop.x, data.star.pTop.y)
                    lineTo(data.star.pRight.x, data.star.pRight.y)
                    lineTo(data.star.decenterRight.x, data.star.decenterRight.y)
                    lineTo(data.star.decenterLeft.x, data.star.decenterRight.y)
                    lineTo(data.star.pLeft.x, data.star.pLeft.y)
                }

                for (angle in listOf(0F, 72F, 144F, 216F, 288F)) {
                    rotate(degrees = angle) {
                        drawPath(starPart, color = colors.star.inColor)
                    }
                }
                for (angle in listOf(0F, 72F, 144F, 216F, 288F)) {
                    rotate(degrees = angle) {
                        drawPath(starPart, brush = Brush.radialGradient( colors.star.neongradient ))
                    }
                }
            }
        }
    }
}

//@Composable
//private fun DrawStarPart(
//    data: InGameFirstPart,
//    colorLeftSide: List<Color>,
//    colorRightSide: List<Color>,
//    colorBack: Color,
//    colorLines: List<Color>,
//) {
//    val sizeDp = data.size.starBoxDp
//    Box(modifier = Modifier
//        .size(sizeDp)
//    ){
//        CenterComposable {
//            Canvas(modifier = Modifier
//                .size(data.size.starCanvasDp)
//            ){
////                val stroke = sizeDp / 50F
//                val partRight = Path().apply {
//                    moveTo(data.star.pTop.x, data.star.pTop.y)
//                    lineTo(data.star.pRight.x, data.star.pRight.y)
//                    lineTo(data.star.center.x, data.star.center.y)
//                }
//                val partRight2 = Path().apply {
//                    moveTo(data.star.pTop.x, data.star.pTop.y)
//                    lineTo(data.star.pRight.x, data.star.pRight.y)
//                    lineTo(data.star.decenter.x, data.star.decenter.y)
//                }
//                val partLeft2 = Path().apply {
//                    moveTo(data.star.pTop.x, data.star.pTop.y)
//                    lineTo(data.star.pLeft.x, data.star.pLeft.y)
//                    lineTo(data.star.decenter.x, data.star.decenter.y)
//                }
//                val partLeft = Path().apply {
//                    moveTo(data.star.pTop.x, data.star.pTop.y)
//                    lineTo(data.star.pLeft.x, data.star.pLeft.y)
//                    lineTo(data.star.center.x, data.star.center.y)
//                }
//
//                for (angle in listOf(0F, 72F, 144F, 216F, 288F)) {
//                    rotate(degrees = angle) {
//                        drawPath(partRight2, colorBack)
//                        drawPath(partLeft2, colorBack)
//                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
//                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
//                        drawLine(
//                            brush = Brush.verticalGradient(colorLines),
//                            start = data.star.pRight,
//                            end = data.star.center,
//                            strokeWidth = data.star.stroke
//                        )
//                        drawLine(
//                            brush = Brush.verticalGradient(colorLines),
//                            start = data.star.pLeft,
//                            end = data.star.center,
//                            strokeWidth = data.star.stroke
//                        )
//                    }
//
//                }
//
//                for (angle in listOf(0F, 72F, 144F, 216F, 288F)) {
//                        drawLine(
//                            brush = Brush.verticalGradient(colorLines),
//                            start = data.star.pRight,
//                            end = data.star.center,
//                            strokeWidth = data.star.stroke
//                        )
//                        drawLine(
//                            brush = Brush.verticalGradient(colorLines),
//                            start = data.star.pLeft,
//                            end = data.star.center,
//                            strokeWidth = data.star.stroke
//                        )
//                    }
//
//            }
//        }
//    }
//}