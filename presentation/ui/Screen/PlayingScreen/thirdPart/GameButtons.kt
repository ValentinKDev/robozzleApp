package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.thirdPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_PLAY
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_RESET
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.*

@Composable
fun GameButtons(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    val animationIsPlaying: Boolean by gameDataViewModel.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by gameDataViewModel.animationIsOnPause.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .testTag(TAG_BUTTON_PLAY)
                    .height(screenConfig.playButtonsHeight.dp)
                    .width(screenConfig.playButtonsWidth.dp)
                    .background(Color.Gray)
                    .clickable {
                        gameDataViewModel.AnimationIsPlayingChangeStatus()
                        if (animationRunningInBackground) {
                            gameDataViewModel.AnimationIsOnPauseChangeStatus()
                        } else if (animationIsPlaying) {
                            StartAnimation(lvl, gameDataViewModel)
                        }
                    }
            ) {
                Box(Modifier.align(Alignment.Center)) {
                    PlayPauseIcon(animationIsPlaying)
                }
            }
            Box(
                modifier = Modifier
                    .testTag(TAG_BUTTON_RESET)
                    .background(Color.Gray)
                    .height(screenConfig.playButtonsHeight.dp)
                    .width(screenConfig.playButtonsWidth.dp)
                    .clickable {
                        gameDataViewModel.ResetAnimation(lvl)
                    }
            ) {
                //todo : define clearly steps so you can t have 2 different actions in the row while player still is the same position by clicking quickly on the play/pause button
                Box(Modifier.align(Alignment.Center)) {
                    Icon(imageVector = Icons.Default.Stop, contentDescription = "stop")
                }
            }
            BackButton(screenConfig, gameDataViewModel, animationIsOnPause)

            NextButton(screenConfig, lvl,  gameDataViewModel, animationIsOnPause)
        }
    }
}
