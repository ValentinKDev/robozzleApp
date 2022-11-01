package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.red9
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.ActionRowSurronder
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayActionsRow
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayFunctionsPart
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun SecondScreenPart(vm: GameDataViewModel) {
    LaunchedEffect(true) {
        Log.i("" , "SecondScreenPart")
    }

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
                    CenterComposableVertically {
                        DisplayActionsRow(vm)
                    }
                }
            }
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowSurrounderHeight),
                verticalArrangement = Arrangement.Top) {
                ActionRowSurronder(vm)
            }
        }
        Row( Modifier.weight(vm.data.layout.secondPart.ratios.functionsRowPartHeight )
        ) {
            DisplayFunctionsPart(vm)
        }
    }
}