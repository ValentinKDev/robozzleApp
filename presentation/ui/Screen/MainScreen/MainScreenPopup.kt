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
                    topPaddingRatio = vm.data.ratios.popup_topPaddingRatio,
                    bottomPaddingRatio = vm.data.ratios.popup_bottomPaddingRatio,
                    startPaddingRatio = vm.data.ratios.popup_startPaddingRatio,
                    endPaddingRatio = vm.data.ratios.popup_endPaddingRatio,
                ) {
                    Card(
                        backgroundColor = vm.data.color.popupMainColor,
                        elevation = vm.data.color.popupElevation,
                        content = {
                            CenterComposable {
                                Text(
                                    modifier = Modifier,
                                    color = vm.data.color.popupTextColor,
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