package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.GAME_LOGIC_COROUTINE
import com.mobilegame.robozzle.presentation.res.TAG_BUTTON_PREV
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenConfig
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@Composable
fun NextButton(vm: GameDataViewModel, enable: Boolean) {
    IconButton(
        onClick = {
            Log.e("NEXT", "clicked")
            if (animationIsOnPause) {
                infoLog("next", "animation is on pause ${animationIsOnPause}")
                infoLog("next", "animation going next change status")
                vm.AnimationGoingNextChangeStatus()
            }
            else {
                infoLog("next", "else")
                vm.AnimationIsOnPauseChangeStatus()
                StartAnimation(lvl, vm)
                vm.AnimationGoingNextChangeStatus()
            }
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(screenConfig.playButtonsHeight.dp)
            .width(screenConfig.playButtonsWidth.dp)
            .background(Color.Gray)
        ,
        //todo : make it possible to start and finish all the game whith forward and backward buttons
        enabled = animationIsOnPause,
    ) {
//                Icon(imageVector = Icons.Default.Redo, contentDescription = "redo", tint = Color.Black)
        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "skipNext", tint = if (animationIsOnPause) Color.Black else Color.DarkGray)
    }
}

@Composable
fun BackButton(vm: GameDataViewModel, enable: Boolean) {
    IconButton(
        onClick = {
            Log.e("BACK", "clicked")
            vm.AnimationGoingBackChangeStatus()
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(screenConfig.playButtonsHeight.dp)
            .width(screenConfig.playButtonsWidth.dp)
            .background(Color.Gray)
        ,
        enabled = animationIsOnPause,
    ) {
//                Icon(imageVector = Icons.Default.Undo, contentDescription = "undo", tint = Color.Black)
        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "skipPrevious", tint = if (animationIsOnPause) Color.Black else Color.DarkGray)
    }
}

//@DelicateCoroutinesApi
fun StartAnimation(lvl: RobuzzleLevel, vm: GameDataViewModel) {
    //todo: lauchn this with a VM in a VMScope
    vm.gameLogicJob = GlobalScope.launch( Dispatchers.Default + CoroutineName( GAME_LOGIC_COROUTINE ) )
    {
        Log.e("", "call StartAnimation")
        AnimationLogic(lvl.breadcrumb, vm).Start()
    }
}
