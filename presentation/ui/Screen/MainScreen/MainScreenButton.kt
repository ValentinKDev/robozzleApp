package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

const val goingTopTiming = 450
const val goingBottomTiming = goingTopTiming - 150
const val goingTopSizeButton = 360
@ExperimentalAnimationApi
@Composable
fun MainScreenButton(navigator: Navigator, info: NavigationButtonInfo, from: Int, vm: MainScreenViewModel) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
//    val buttonSelected by vm.buttonSelected.collectAsState()
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

    AnimatedVisibility(
        visible = visibleElements,
        enter = if (from == info.buttonId)
            slideInVertically(
                animationSpec = tween(goingTopTiming)
//                animationSpec = tween(800)
            )
        else
            fadeIn(
                initialAlpha = 0.2F,
//                animationSpec = tween(800)
            )
        ,
//        exit = if (buttonState == ButtonState.OnTop) {
        exit = exitTransitionByState(buttonState, info.buttonId)
        ) {
        Button(
            modifier = Modifier
                .background(info.color)
//                .width(info.width.dp)
                .width(animWidth)
                .height(info.height.dp),
            onClick = {
//                vm.changeButtonState(info.buttonId)
                vm.updateButtonSelected(info.buttonId)
                vm.changeVisibility()
                NavViewModel(navigator).navigateTo(
                    destination = info.destination,
                    argStr = info.arg,
                    delayTiming = 400
//                    delayTiming = goingTopTiming.toLong()
                )
            },
            enabled = info.enable
        ) {
            Text(text = info.text)
        }
    }
}

enum class ButtonState {
    OnPlace, OnTop, OnLeftSide, OnRightSide, OnBottom, Fade
}

enum class ButtonSelected(value: Int) {
    None(-1),
    ButtonProfile(0),
    Button1(1),
    Button2(2),
    Button3(3),
    Button4(4),
    Button5(5),
    ButtonCreator(6),
    ButtonDonation(7),
    ButtonConfig(8)
}

@ExperimentalAnimationApi
fun exitTransitionByState(buttonState: ButtonState, id : Int): ExitTransition {
    infoLog("id ${id}", "state ${buttonState}")
    return when (buttonState) {
        ButtonState.OnBottom -> slideOutVertically(targetOffsetY = {it + 50}, animationSpec = tween(500))
        ButtonState.OnTop -> slideOutVertically(targetOffsetY = {
            infoLog("$id offsetY", "${it}")
//            it - 450
            when (id) {
                ButtonId.LevelDiff1.key -> -400
                ButtonId.LevelDiff2.key -> -550
                ButtonId.LevelDiff3.key -> -700
                ButtonId.LevelDiff4.key -> -900
                ButtonId.LevelDiff5.key -> -1100
                else -> -500
            }
                                                                }, animationSpec = tween(500))
        ButtonState.OnRightSide -> slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
        ButtonState.OnLeftSide -> slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
//        ButtonState.OnLeftSide -> fadeOut()
        else -> fadeOut(animationSpec = tween(250))
    }
}