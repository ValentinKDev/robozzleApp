package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.heightRatio
import com.mobilegame.robozzle.Extensions.widthRatioTotalWidth
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
fun MainScreenButton(navigator: Navigator, info: NavigationButtonInfo, from: Int, vm: MainScreenViewModel, w: MainScreenWindowsInfos) {
    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    var buttonState by remember { mutableStateOf(ButtonState.OnPlace)}

    buttonState = vm.updateButtonStates(info.buttonKey)
    val transition = updateTransition(targetState = buttonState, label = "")
    val animSize by transition.animateSize(
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
            ButtonState.OnTop -> MainScreenWindowsInfos().getButtonSizeTarget(info.button, ctxt, dens)
            else -> MainScreenWindowsInfos().getButtonSize(info.button, ctxt, dens)
        }
    }
    Box(
        Modifier
            .wrapContentSize()
            .background(Color.Transparent)
            .onGloballyPositioned { _layoutCoordinates ->
                val offset = _layoutCoordinates.boundsInRoot().topLeft
                vm.setOffset(info.buttonKey, offset)
            }
    ) {
        AnimatedVisibility(
            visible = visibleElements,
            //todo : from is not update when use press the back button, MainScreenButton is loaded with the previous from (the one it was originaly launched with)
            enter = enterTransitionByFrom(info.buttonKey, from) ,
            exit = exitTransitionByState(buttonState, info.buttonKey, vm.getOffset(info.buttonKey), vm.animationTime.value)
        ) {
            Card(
                modifier = Modifier
                    .size(width = animSize.width.dp, height = animSize.height.dp)
                    .clickable(enabled = info.enable) {
                        vm.updateButtonSelected(info.buttonKey)
                        vm.setAnimationTime(info.buttonKey)
                        buttonState = ButtonState.OnTop
                        vm.changeVisibility()
                        infoLog("vm.animationTime", "${vm.animationTime.value}")
                        NavViewModel(navigator).navigateTo(
                            destination = info.destination,
                            argStr = info.arg,
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

class SizeRatio(widhtRatio: Float, heightRatio: Float) {

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
                    in ButtonId.LevelDiff1.key..ButtonId.LevelDiff5.key -> offset.y.toInt() * -1
                    else -> -500
                }
            },
            animationSpec = tween(
                when (id) {
                    in ButtonId.LevelDiff1.key..ButtonId.LevelDiff5.key -> animationTime
                    else -> -500
                }.toInt()
            )
        )
        ButtonState.OnRightSide -> slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
        ButtonState.OnLeftSide -> slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
        else -> fadeOut(animationSpec = tween(250))
    }
}

//    val animWidth by transition.animateDp(
//        label = "",
//        transitionSpec = {
//            when (buttonState) {
//                ButtonState.OnTop -> tween(goingTopTiming)
//                ButtonState.OnLeftSide -> tween(300)
//                ButtonState.OnRightSide -> tween(300)
//                ButtonState.Fade -> tween(200)
//                else -> tween(250)
//            }
//        }
//    ) { state ->
//        when (state) {
//            ButtonState.OnTop -> goingTopSizeButton.dp
//            else -> info.width.dp
//        }
//    }
//    val animWidthRatio by transition.(
