package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.presentation.res.*

@Composable
fun RankingIconBouncing(sizeAtt: Int, rankingIconVM: RankingIconViewModel, isPressed: Boolean) {
    val height = sizeAtt * 0.85F
    val width = height * (6.0F / 7.0F)

//    val currentState by remember(screenVM) {screenVM.rankingIconTouchState}.collectAsState(OnTouchBounceState.Released)
    val currentState by remember(rankingIconVM) {rankingIconVM.rankingIconTouchState}.collectAsState(OnTouchBounceState.Released)

    val animatedHeightGreen: Dp by animateDpAsState(
        when {
            currentState == OnTouchBounceState.Pressed && isPressed -> (0.75 * (3.0F/5.0F) * height).dp
            else -> ((3.0F/5.0F) * height).dp
        },
        when {
            currentState == OnTouchBounceState.Pressed && isPressed ->
                spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow)
            else ->
                spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = (Spring.StiffnessLow + Spring.StiffnessMedium) / 2.0F)
        }
    )

    val animatedHeightBlue: Dp by animateDpAsState(
        when {
            currentState == OnTouchBounceState.Pressed && isPressed -> (0.70F  * height).dp
            else -> (height).dp
        },
        when {
            currentState == OnTouchBounceState.Pressed && isPressed-> spring(dampingRatio = Spring.DampingRatioLowBouncy)
            else -> { spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow) }
        }
    )

    val animatedHeightRed: Dp by animateDpAsState(
        when {
            currentState == OnTouchBounceState.Pressed && isPressed ->
                (0.65 * (4.0F/5.0F) * height).dp
            else ->
                ((4.0F/5.0F) * height).dp
        },
        when {
            currentState == OnTouchBounceState.Pressed && isPressed-> { spring(dampingRatio = Spring.DampingRatioLowBouncy) }
            else -> { spring(dampingRatio = 0.18F, stiffness = Spring.StiffnessMedium) }
        }
    )

    Row(
        modifier = Modifier
            .width(width.dp)
            .height(sizeAtt.dp)
        ,
        verticalAlignment = Alignment.Bottom
    ) {
        Column( modifier = Modifier
            .height(animatedHeightGreen)
            .weight(1.0F)
            .gradientBackground(listOf(greendark3, greendark9), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
        Column( modifier = Modifier
            .height(animatedHeightBlue)
            .weight(1.0F)
            .gradientBackground(listOf(blueDark0, blueDark6), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
        Column( modifier = Modifier
            .height(animatedHeightRed)
            .weight(1.0F)
            .gradientBackground(listOf(redDark3, redDark9), 0F)
            .graphicsLayer { shadowElevation = 15.dp.toPx() }
            ,
        ) { }
    }
}

enum class OnTouchBounceState { Pressed, Released }