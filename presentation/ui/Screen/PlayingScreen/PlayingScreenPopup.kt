package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun PlayingScreenPopupWin(vm: GameDataViewModel) {
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
                    backgroundColor = vm.data.colors.popupMainColor,
                    elevation = vm.data.colors.popupElevation,
                    content = {
                        CenterComposable {
                            Text(
                                modifier = Modifier,
                                color = vm.data.colors.popupTextColor,
                                text = vm.data.text.popupWinText
                            )
                        }
                    }
                )
            }
        }
    )
}
