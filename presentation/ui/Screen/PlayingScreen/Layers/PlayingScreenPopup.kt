package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.clickable
import com.mobilegame.robozzle.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.GifImage
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun PlayingScreenPopupWin(vm: GameDataViewModel) {
    val win: Boolean by vm.animData.winPop.collectAsState(false)
    
    val tutoLevel = remember { vm.level.id == 0 }
    
    if (win) {
        vm.animationLogicVM.AddWin( LocalContext.current )
        Box {
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
                        if (tutoLevel) {
                            Box {
                                GifImage(data = R.drawable.shialabeouf)
                                CenterComposable {
                                    Text(
                                        modifier = Modifier,
                                        color = vm.data.colors.popupTextColor,
                                        text = vm.data.text.popupWinTuto,
                                    )
                                }
                            }
                        } else {
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
                }
            )
//            if (tutoLevel) {
//                Box(
////                    Modifier
//                    contentAlignment = Alignment.BottomEnd
//                ) {
//                    GifImage(data = R.drawable.shialabeouf)
//                }
//            }
        }
    }
}
