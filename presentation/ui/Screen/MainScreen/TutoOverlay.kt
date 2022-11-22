package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
internal fun tutoOverlay(vm: MainScreenViewModel) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)

    Box(
        Modifier
            .fillMaxSize()
            .background(vm.ui.tuto.colors.filter)
    )
    PaddingComposable(
        topPaddingRatio = vm.ui.tuto.popup.topPadding,
        startPaddingRatio = vm.ui.tuto.popup.startPadding,
        endPaddingRatio = vm.ui.tuto.popup.endPadding,
        bottomPaddingRatio = vm.ui.tuto.popup.bottomPadding
    ) {
        AnimatedVisibility(
            visible = visibleElements,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                Modifier
                    .shadow(vm.ui.tuto.popup.shadow)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = vm.ui.tuto.colors.popupBackground)
                    .fillMaxSize()
            ) {
                vm.tutoVM.tuto?.description?.let {
                    CenterText(
                        text = it,
                        color = vm.ui.tuto.colors.popupText
                    )
                }
            }
        }
    }
}