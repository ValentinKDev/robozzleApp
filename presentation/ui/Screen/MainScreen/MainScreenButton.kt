package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import com.mobilegame.robozzle.presentation.ui.utils.CenterText

const val goingTopTiming = 450
const val goingBottomTiming = goingTopTiming - 150
const val goingTopSizeButton = 360

@ExperimentalAnimationApi
@Composable
fun MainScreenButton(navigator: Navigator, info: NavigationButtonInfo, from: Int, vm: MainScreenViewModel) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    var buttonState by remember { mutableStateOf(ButtonState.OnPlace)}

    buttonState = vm.updateButtonStates(info.buttonId)
    val transition = updateTransition(targetState = buttonState, label = "")
    val animWidth by transition.animateDp(
        label = "",
        transitionSpec = {
            when (buttonState) {
                ButtonState.OnTop -> tween(goingTopTiming)
                ButtonState.OnLeftSide -> tween(300)
                ButtonState.OnRightSide -> tween(300)
                ButtonState.Fade -> tween(200)
                else -> tween(250)
            }
        }
    ) { state ->
        when (state) {
            ButtonState.OnTop -> goingTopSizeButton.dp
            else -> info.width.dp
        }
    }

    Box(Modifier
        .wrapContentSize()
        .background(Color.Transparent)
        .onGloballyPositioned { _layoutCoordinates ->
            val offset = _layoutCoordinates.boundsInRoot().topLeft
            vm.setOffset(info.buttonId, offset)
        }
    ) {
        AnimatedVisibility(
            visible = visibleElements,
            //todo : from is not update when use press the back button, MainScreenButton is loaded with the previous from (the one it was originaly launched with)
            enter = enterTransitionByFrom(info.buttonId, from) ,
            exit = exitTransitionByState(buttonState, info.buttonId, vm.getOffset(info.buttonId), vm.animationTime.value)
        ) {
            Card(
                modifier = Modifier
                    .width(animWidth)
                    .height(info.height.dp)
                    .clickable(enabled = info.enable) {
                        vm.updateButtonSelected(info.buttonId)
                        vm.setAnimationTime(info.buttonId)
                        buttonState = ButtonState.OnTop
                        vm.changeVisibility()
                        infoLog("vm.animationTime", "${vm.animationTime.value}")
                        NavViewModel(navigator).navigateTo(
                            destination = info.destination,
                            argStr = info.arg,
//                            delayTiming = 400
                            delayTiming = vm.animationTime.value
                        )
                    }
                ,
                elevation = 15.dp,
                shape = MaterialTheme.shapes.medium,
                backgroundColor = info.color,
            ) {
                CenterText(text = info.text, color = whiteDark4)
            }
        }
    }
}

enum class ButtonState {
    OnPlace, OnTop, OnLeftSide, OnRightSide, OnBottom, Fade
}

@ExperimentalAnimationApi
fun enterTransitionByFrom(id: Int, from: Int): EnterTransition {
    return when (from) {
        ButtonId.None.key -> {
            when (id) {
                0 -> { slideInHorizontally( initialOffsetX = { +500 }, animationSpec = tween(400) ) }
                in 1..5 -> { slideInHorizontally( initialOffsetX = {if (id == 4 || id == 2) +500 else -500 }, animationSpec = tween (500) ) }
                6 -> { slideInHorizontally( initialOffsetX = { -200 }, animationSpec = tween(400) ) }
                7 -> { slideInVertically( initialOffsetY = { -200 }, animationSpec = tween(400) ) }
                8 -> { slideInHorizontally( initialOffsetX = { +200 }, animationSpec = tween(400) ) }
                else -> {fadeIn()}
            }
        }
        else -> {
            when {
                id == from -> {
                    slideInVertically(
                        animationSpec = tween (500),
                        initialOffsetY = {
                            -150 }
                    ) + fadeIn(animationSpec = tween(300))
                }
                id in (from + 1)..5 -> {
                    slideInHorizontally(
                        initialOffsetX = {if (id == 4 || id == 2) +500 else -500 },
                        animationSpec = tween (300)
                    ) + fadeIn(animationSpec = tween(300))
                }
                else -> fadeIn(animationSpec = tween(500))
            }
        }
    }
}

@ExperimentalAnimationApi
fun exitTransitionByState(buttonState: ButtonState, id: Int, offset: Offset, animationTime: Long): ExitTransition {
    return when (buttonState) {
        ButtonState.OnBottom -> slideOutVertically(
            targetOffsetY = {it + 50},
            animationSpec = tween(
                when (id) {
                    in ButtonId.LevelDiff1.key..ButtonId.LevelDiff5.key -> animationTime
                    else -> -500
                }.toInt()
            )
        )
        ButtonState.OnTop -> slideOutVertically(
            targetOffsetY = {
                verbalLog("offset", "$offset")
                when (id) {
//                    ButtonId.LevelDiff1.key -> -400
//                    ButtonId.LevelDiff2.key -> -550
//                    ButtonId.LevelDiff3.key -> -700
//                    ButtonId.LevelDiff4.key -> -900
//                    ButtonId.LevelDiff5.key -> -1100
                    in ButtonId.LevelDiff1.key..ButtonId.LevelDiff5.key -> offset.y.toInt() * -1
                    else -> -500
                }
            },
//            animationSpec = tween()
            animationSpec = tween(
                when (id) {
            //                ButtonId.LevelDiff1.key -> tween(500)
            //                ButtonId.LevelDiff2.key -> tween(500)
            //                ButtonId.LevelDiff3.key -> tween(500)
            //                ButtonId.LevelDiff4.key -> tween(500)
            //                ButtonId.LevelDiff5.key -> tween(500)
            //                else -> tween(500)
                    in ButtonId.LevelDiff1.key..ButtonId.LevelDiff5.key -> animationTime
                    else -> -500
                }.toInt()
            )
        )
        ButtonState.OnRightSide -> slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
        ButtonState.OnLeftSide -> slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
//        ButtonState.OnLeftSide -> fadeOut()
        else -> fadeOut(animationSpec = tween(250))
    }
}