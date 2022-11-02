package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WhiteSquare(sizeDp: Dp, stroke: Float, vm: GameDataViewModel, enableAnimation: Boolean = false) {
    val playerState by vm.animData.playerAnimationState.collectAsState()
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 3800, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

//    var visibleSquare by remember{ mutableStateOf(!enableAnimation) }

//    LaunchedEffect(true) {
//        visibleSquare = true
//    }

//    AnimatedVisibility(
//        visible = visibleSquare,
//        enter = fadeIn(),
//        exit = fadeOut()
//    ) {
        Canvas(
            Modifier
                .height(sizeDp)
                .width(sizeDp)) {
            drawRect(
                color = Color.Gray,
                style = Stroke(stroke)
            )
            if (playerState == PlayerAnimationState.OnPause) {
                val brush = Brush.linearGradient(
                    colors = vm.data.colors.emptySquare.shimmerColorShades,
                    start = Offset(10f, 10f),
                    end = Offset(translateAnim, translateAnim)
                )
                drawRect(
                    brush = brush,
                    style = Stroke(stroke)
                )
            }
            else
                drawRect(
                    color = Color.LightGray,
                    style = Stroke(stroke)
                )
        }
//    }
}
