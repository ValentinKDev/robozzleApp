package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.data.configuration.ScreenConfig
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun MainScreenPopup(vm: MainScreenViewModel) {
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
                                text =
//                                if (screenConfig.popUp == PopUpState.Update)
//                                vm.data.text.popupUpdateAvailable
//                                else
//                                    vm.data.text.popupUnReachableServer
                                vm.popupState.toString()
                            )
                        }
                    }
                )
            }
        }
    )
}