package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import com.mobilegame.robozzle.presentation.ui.utils.CenterText

const val goingTopTiming = 450

@OptIn(ExperimentalAnimationApi::class)
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
            enter = MainMenuAnimationViewModel().enterTransitionByFrom(info.buttonKey, from) ,
            exit = MainMenuAnimationViewModel().exitTransitionByState(buttonState, info.buttonKey, vm.getOffset(info.buttonKey), vm.animationTime.value)
        ) {
            Card(
                modifier = Modifier
                    .size(width = animSize.width.dp, height = animSize.height.dp)
                    .clickable(enabled = info.enable) {
                        vm.updateButtonSelected(info.buttonKey)
                        vm.setAnimationTime(info.buttonKey)
                        buttonState = ButtonState.OnTop
                        vm.changeVisibility()
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

enum class ButtonState {
    OnPlace, OnTop, OnLeftSide, OnRightSide, OnBottom, Fade
}