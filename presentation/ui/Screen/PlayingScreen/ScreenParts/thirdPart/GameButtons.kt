package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.thirdPart

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.runningInBackgroundIs
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.BackButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.NextButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.PlayPauseButton
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.ResetButton
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@Composable
fun GameButtons(vm: GameDataViewModel, enablePlayPause: Boolean = true, enableReset: Boolean = true, enableNext: Boolean = true, enableBack: Boolean = true) {
    val playerAnimationState: PlayerAnimationState by vm.animData.playerAnimationState.collectAsState()

    CenterComposableVertically {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(Modifier.weight(1F))
            PlayPauseButton(vm = vm, enablePlayPause)

            Box(Modifier.weight(1F))
            ResetButton(vm, enableReset)

            Box(Modifier.weight(1F))
            BackButton(vm, enable = playerAnimationState runningInBackgroundIs true && enableBack)

            Box(Modifier.weight(1F))
            NextButton(vm, enable = playerAnimationState runningInBackgroundIs true && enableNext)
            Box(Modifier.weight(1F))
        }
    }
}
