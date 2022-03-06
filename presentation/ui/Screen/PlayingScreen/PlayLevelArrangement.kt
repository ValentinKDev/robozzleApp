package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.thirdPart.GameButtons

//todo : use the navigation to avoid the recall of the function DisplayGameScreen 3 times
//@DelicateCoroutinesApi
@Composable
fun DisplayGameScreen(level: RobuzzleLevel, vm: GameDataViewModel, screenConfig: ScreenConfig) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(Modifier.weight(vm.data.layout.firstPart.ratios.height)){
            MapLayout(level, vm, screenConfig)
        }
        Row(modifier = Modifier
            .weight(vm.data.layout.secondPart.ratios.height)
        ) {
            SecondScreenPart(level, vm, screenConfig,)
        }
        Row(modifier = Modifier
            .weight(vm.data.layout.thirdPart.ratios.height)
        ) {
            GameButtons(lvl = level, gameDataViewModel = vm, screenConfig = screenConfig)
        }
    }
}

