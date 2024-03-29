package com.mobilegame.robozzle.presentation.ui.Screen.Tuto

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.mapCaseColorList
import gradientBackground

@Composable
fun enlightItem(modifier: Modifier, initialColor: Color = Color.Transparent) {

    val infiniteTransition = rememberInfiniteTransition()

    val animWhite by infiniteTransition.animateColor(
        initialValue = initialColor,
        targetValue = MyColor.white8,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = FastOutLinearInEasing),
            RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .then(modifier)
            .background(animWhite)
    ) { }
}


