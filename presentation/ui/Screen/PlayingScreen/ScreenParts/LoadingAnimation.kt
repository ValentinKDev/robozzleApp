package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.gray9

@Composable
fun LoadingAnimation() {
    var squareScale by remember {
        mutableStateOf(0f)
    }
    val squareScaleAnimate = animateFloatAsState(
        targetValue = squareScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 600
            )
        )
    )
    LaunchedEffect(key1 = true) {
        Log.e("LoadingAnimation", "Start")
        squareScale = 1f
    }
    Box(
        modifier = Modifier
            .size(size = 64.dp)
            .scale(scale = squareScaleAnimate.value)
            .border(
                width = 4.dp,
                color = gray9.copy(alpha = 1 - squareScaleAnimate.value),
                shape = RectangleShape
            )
    ) { }
}