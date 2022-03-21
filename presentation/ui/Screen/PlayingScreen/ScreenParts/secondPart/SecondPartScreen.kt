package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.ActionRowSurronder
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayActionsRow
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayFunctionsPart
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun SecondScreenPart(vm: GameDataViewModel) {
    Log.i("" , "SecondScreenPart")

//    val recomposeSecondPart: Boolean by vm.triggerRecompostion.collectAsState(false)
//    if (recomposeSecondPart) vm.triggerRecompostionToFalse()

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(vm.data.layout.secondPart.ratios.actionRowHeight)
        ) {
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowSurrounderHeight),
                verticalArrangement = Arrangement.Top) {
                ActionRowSurronder(vm)
            }
            Column(
                Modifier.weight(vm.data.layout.secondPart.ratios.actionRowHeight)
            ) {
                PaddingComposable(
                    startPaddingRatio = vm.data.layout.secondPart.ratios.actionRowStartPadding,
                    endPaddingRatio = vm.data.layout.secondPart.ratios.actionRowEndPadding
                ) {
//                    Box(Modifier.backColor(yellow0)) {
                        CenterComposableVertically {
                            DisplayActionsRow(vm)
                        }
//                    }
                }
            }
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowSurrounderHeight),
                verticalArrangement = Arrangement.Top) {
                ActionRowSurronder(vm)
            }
        }
        Row( Modifier.weight(vm.data.layout.secondPart.ratios.functionsRowPartHeight)
        ) {
            DisplayFunctionsPart(vm)
        }
    }
}