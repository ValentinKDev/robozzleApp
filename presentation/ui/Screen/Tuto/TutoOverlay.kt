package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
internal fun tutoOverlay(
    info: TutoObj,
    text: String,
//    visibleElements: Boolean?,
    visibleElements: Boolean,
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(info.colors.filter)
    )
    PaddingComposable(
        topPaddingRatio = info.popup.topPadding,
        startPaddingRatio = info.popup.startPadding,
        endPaddingRatio = info.popup.endPadding,
        bottomPaddingRatio = info.popup.bottomPadding
    ) {
            AnimatedVisibility(
                visible = visibleElements,
                enter = fadeIn(animationSpec = tween(700)),
                exit = fadeOut(animationSpec = tween(300)),
            ) {
                Box(
                    Modifier
                        .shadow(info.popup.shadow)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = info.colors.popupBackground)
                        .fillMaxSize()
                ) {
                    CenterText(
                        text = text,
                        color = info.colors.popupText
                    )
                }
            }
    }
}
