package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun PlayingScreenPopupWin(vm: GameDataViewModel) {
    val win: Boolean by vm.animData.winPop.collectAsState(false)
    if (win) {
        errorLog("WIN", "popup")
        vm.animationLogicVM.AddWin( LocalContext.current )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
//                    vm.popup.changePopupVisibility()
                    vm.animData.setWinPopTo(false)
                },
            content = {
                PaddingComposable(
                    topPaddingRatio = vm.data.layout.popup.ratios.topPadding,
                    bottomPaddingRatio = vm.data.layout.popup.ratios.bottomPadding,
                    startPaddingRatio = vm.data.layout.popup.ratios.startPadding,
                    endPaddingRatio = vm.data.layout.popup.ratios.endPadding,
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
}
