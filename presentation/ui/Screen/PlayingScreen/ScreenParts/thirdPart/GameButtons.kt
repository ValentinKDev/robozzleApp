package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.thirdPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.runningInBackgroundIs
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_PLAY
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_RESET
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.BackButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.NextButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.PlayPauseButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.ResetButton
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@Composable
fun GameButtons(vm: GameDataViewModel) {
    val playerAnimationState: PlayerAnimationState by vm.animationLogicVM.data.playerAnimationState.collectAsState()

    CenterComposableVertically {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(Modifier.weight(1F))
//            Box(Modifier.weigt())
            PlayPauseButton(vm = vm)

            Box(Modifier.weight(1F))
            ResetButton(vm)

            Box(Modifier.weight(1F))
            BackButton(vm, enable = playerAnimationState runningInBackgroundIs true)

            Box(Modifier.weight(1F))
            NextButton(vm, enable = playerAnimationState runningInBackgroundIs true)
            Box(Modifier.weight(1F))
        }
    }
}
