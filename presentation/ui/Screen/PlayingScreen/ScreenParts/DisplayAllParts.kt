package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.thirdPart.GameButtons

//todo : use the navigation to avoid the recall of the function DisplayGameScreen 3 times
@Composable
fun DisplayAllParts(vm: GameDataViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(Modifier.weight(vm.data.layout.firstPart.ratios.height)){
            MapLayout(vm)
        }
        Row(modifier = Modifier
            .weight(vm.data.layout.secondPart.ratios.height)
        ) {
            SecondScreenPart(vm)
        }
        Row(modifier = Modifier
            .weight(vm.data.layout.thirdPart.ratios.height)
        ) {
            GameButtons(vm)
        }
    }
}

