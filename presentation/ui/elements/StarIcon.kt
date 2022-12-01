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
import com.mobilegame.robozzle.presentation.ui.Navigation.levelTuto

@Preview
@Composable
fun testView() {
    val ctxt = LocalContext.current
    val data = InGameFirstPart
    data.init(ctxt, levelTuto)
    Canvas(Modifier.size(data.sizes.starBoxDp)) {
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
fun StarIcon(data: InGameFirstPart, colors: InGameColors, enableGlow: Boolean = true) {
    if (enableGlow)
        DrawBackGlowingEffect( data = data)
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
        .size(data.sizes.starBoxDp)
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

    val sizeDp = data.sizes.starBoxDp
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
            }
        }
    }
}

@Composable
private fun DrawStarPart(
    data: InGameFirstPart,
    colors: InGameColors,
) {
    val sizeDp = data.sizes.starBoxDp
    Box(modifier = Modifier
        .size(sizeDp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier
                .size(data.sizes.starCanvasDp)
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