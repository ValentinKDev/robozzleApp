package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenPopup(vm: MainScreenViewModel) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)

    AnimatedVisibility(
        visible = visibleElements,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    vm.popup.changePopupVisibility()
                }
            ,
            content = {
                PaddingComposable(
                    topPaddingRatio = vm.ui.popup.ratios.topPaddingRatio,
                    bottomPaddingRatio = vm.ui.popup.ratios.bottomPaddingRatio,
                    startPaddingRatio = vm.ui.popup.ratios.startPaddingRatio,
                    endPaddingRatio = vm.ui.popup.ratios.endPaddingRatio,
                ) {
                    Card(
                        backgroundColor = vm.ui.popup.color.background,
                        elevation = vm.ui.popup.color.elevation,
                        content = {
                            CenterComposable {
                                Text(
                                    color = vm.ui.popup.color.text,
                                    text = vm.getPopupText(),
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}