package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonState

@Composable
fun RankingIcon(sizeAtt: Int) {
    Canvas(
        modifier = Modifier
            .width((sizeAtt.toFloat() * (6.0F / 7.0F)).dp)
            .height(sizeAtt.dp)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
        ,
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

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

@Composable
fun RankingIconBouncing(sizeAtt: Int) {
    val height = sizeAtt * 0.7F
    val width = height * (6.0F / 7.0F)

    var currentState: OnTouchBounceState by remember { mutableStateOf(OnTouchBounceState.Released) }

    val heightGreenAnimated: Dp by animateDpAsState(
        when (currentState) {
            OnTouchBounceState.Released -> ((3.0F/5.0F) * height).dp
            OnTouchBounceState.Pressed -> (0.75 * (3.0F/5.0F) * height).dp
        },
        when (currentState) {
            OnTouchBounceState.Released -> {
                spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = (Spring.StiffnessLow + Spring.StiffnessMedium) / 2.0F)
            }
            OnTouchBounceState.Pressed -> {
                spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow)
            }
        }
    )

    val YoffsetAnimationBlue: Dp by animateDpAsState(
        when (currentState) {
            OnTouchBounceState.Released -> (height).dp
            OnTouchBounceState.Pressed -> (0.70F  * height).dp
        },
        when (currentState) {
            OnTouchBounceState.Released -> { spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow) }
            else -> spring(dampingRatio = Spring.DampingRatioLowBouncy)
        }
    )

    val heightRedAnimated: Dp by animateDpAsState(
        when (currentState) {
            OnTouchBounceState.Released -> ((4.0F/5.0F) * height).dp
            OnTouchBounceState.Pressed -> (0.65 * (4.0F/5.0F) * height).dp
        },
        when (currentState) {
            OnTouchBounceState.Released -> {
                spring(dampingRatio = 0.18F, stiffness = Spring.StiffnessMedium)
            }
            OnTouchBounceState.Pressed -> {
                spring(dampingRatio = Spring.DampingRatioLowBouncy)
            }
        }
    )

    Row(
        modifier = Modifier
            .width(width.dp)
            .height(sizeAtt.dp)
            .pointerInput(Unit) { detectTapGestures(
                    onPress = {
                        currentState = OnTouchBounceState.Pressed
                        tryAwaitRelease()
                        currentState = OnTouchBounceState.Released
                    }
            ) }
        ,
        verticalAlignment = Alignment.Bottom
    ) {
        Column( modifier = Modifier
            .height(heightGreenAnimated)
            .weight(1.0F)
            .gradientBackground(listOf(greendark3, greendark9), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
        Column( modifier = Modifier
            .height(YoffsetAnimationBlue)
            .weight(1.0F)
            .gradientBackground(listOf(blueDark0, blueDark6), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
        Column( modifier = Modifier
            .height(heightRedAnimated)
            .weight(1.0F)
            .gradientBackground(listOf(redDark3, redDark9), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
    }
}

private enum class OnTouchBounceState { Pressed, Released }