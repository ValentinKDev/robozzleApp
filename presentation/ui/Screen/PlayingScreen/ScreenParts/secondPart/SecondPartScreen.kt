package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.ActionRowSurronder
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayActionsRow
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayFunctionsPart

@Composable
fun SecondScreenPart(lvl: RobuzzleLevel, vm: GameDataViewModel, screenConfig: ScreenConfig) {
    Log.i("" , "SecondScreenPart")

    val recomposeSecondPart: Boolean by vm.triggerRecompostion.collectAsState(false)
    if (recomposeSecondPart) vm.triggerRecompostionToFalse()

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
//                .weight(1.0f)
                .weight(vm.data.layout.secondPart.ratios.actionRowHeight)
//                .background(Color.Gray)
        ) {
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowSurronderHeight),
                verticalArrangement = Arrangement.Top) {
                ActionRowSurronder(screenConfig = screenConfig, vm)
            }
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowHeight)) {
//                if (lvl.breadcrumb.actions.instructions.isNotEmpty())
                    DisplayActionsRow(lvl, vm, screenConfig)
//                else
//                    errorLog("action Row", "${lvl.breadcrumb.actionsList}")
            }
            Column(Modifier.weight(vm.data.layout.secondPart.ratios.actionRowSurronderHeight),
                verticalArrangement = Arrangement.Top) {
                ActionRowSurronder(screenConfig = screenConfig, vm)
            }
        }
        Row( Modifier
            .weight(vm.data.layout.secondPart.ratios.functionsRowHeight)
        ) {
            DisplayFunctionsPart(lvl = lvl, vm)
        }
    }
}