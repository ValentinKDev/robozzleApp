package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun PlayingScreenPopupWin(vm: GameDataViewModel) {
    vm.animationLogicVM.AddWin( LocalContext.current )
//    vm.SetWinTo(
//        value = result,
//        winDetails = WinDetails(
//            instructionsNumber = breadcrumb.funInstructionsList.countInstruction(),
//            actionsNumber = breadcrumb.actionsCount,
//            solutionFound = breadcrumb.funInstructionsList.toList()
//        ),
//        context = LocalContext.current
//    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                vm.popup.changePopupVisibility()
            }
        ,
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
