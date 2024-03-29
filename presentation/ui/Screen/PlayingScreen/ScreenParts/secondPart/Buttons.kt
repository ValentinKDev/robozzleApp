package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_PLAY
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_PREV
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_RESET
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.PlayPauseIcon
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@Composable
fun NextButton(vm: GameDataViewModel, enable: Boolean) {
    IconButton(
        onClick = {
            Log.e("NEXT", "clicked")
            vm.clickNextButtonHandler()
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
            .background(vm.data.colors.buttonGameBackground)
            .graphicsLayer { shadowElevation = vm.data.colors.buttonElevation.toPx()}
        ,
        //todo : make it possible to start and finish all the game whith forward and backward buttons
        enabled = enable,
    ) {
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = "skipNext",
            tint = if (enable) Color.Black else Color.DarkGray)
    }
}

@Composable
fun BackButton(vm: GameDataViewModel, enable: Boolean) {
    IconButton(
        onClick = {
            Log.e("BACK", "clicked")
            vm.clickBackButtonHandler()
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
            .background(vm.data.colors.buttonGameBackground)
        ,
        enabled = enable,
    ) {
        Icon(
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = "skipPrevious",
            tint = if (enable) Color.Black else Color.DarkGray)
    }
}

@Composable
fun PlayPauseButton(vm: GameDataViewModel, enablePlayPause: Boolean) {
    val playerAnimationState by remember { vm.animData.playerAnimationState }.collectAsState()
    Box(
        modifier = Modifier
            .testTag(TAG_BUTTON_PLAY)
            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
            .background(vm.data.colors.buttonGameBackground)
            .clickable {
                if (enablePlayPause)
                    vm.clickPlayPauseButtonHandler()
            }
    ) {
        Box(Modifier.align(Alignment.Center)) {
//            PlayPauseIcon(isPlaying = vm.animData.isPlaying())
            PlayPauseIcon(isPlaying = playerAnimationState == PlayerAnimationState.IsPlaying)
        }
    }
}
@Composable
fun ResetButton(vm: GameDataViewModel, enableReset: Boolean) {
    Box(
        Modifier
            .testTag(TAG_BUTTON_RESET)
            .background(vm.data.colors.buttonGameBackground)
            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
            .clickable {
                if (enableReset)
                    vm.clickResetButtonHandler()
            }
    ) {
        //todo : define clearly steps so you can t have 2 different actions in the row while player still is the same position by clicking quickly on the play/pause button
        Box(Modifier.align(Alignment.Center)) {
            Icon(imageVector = Icons.Default.Stop, contentDescription = "stop")
        }
    }
}

//@DelicateCoroutinesApi
//fun StartAnimation(lvl: RobuzzleLevel, vm: GameDataViewModel) {
//    todo: lauchn this with a VM in a VMScope
//    vm.gameLogicJob = GlobalScope.launch( Dispatchers.Default + CoroutineName( GAME_LOGIC_COROUTINE ) )
//    {
//        Log.e("", "call StartAnimation")
//        AnimationLogic(lvl.breadcrumb, vm).Start()
//    }
//}
