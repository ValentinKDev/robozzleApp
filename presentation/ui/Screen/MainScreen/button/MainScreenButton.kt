package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button

import android.view.animation.OvershootInterpolator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import io.ktor.util.date.*

const val goingTopTiming = 450
//@Preview
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenButton(navigator: Navigator, info: NavigationButtonInfo, fromScreen: Screens, vm: MainScreenViewModel, w: MainScreenWindowsInfos, enable: Boolean = true, anim: MainMenuAnimationViewModel = viewModel() ) {
    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    var buttonState by remember { mutableStateOf(ButtonState.Unknown)}

    val xScale = remember{ Animatable(2F) }

    LaunchedEffect(key1 = true) {
        anim.setVisibleButtonTargetSate(true)
        xScale.animateTo(
            targetValue = 1F,
            animationSpec = tween(
                durationMillis = 600,
                easing = {OvershootInterpolator(2F).getInterpolation(it)}
            )
        )
    }

    val visibleButtonState by remember {anim.visibleButton}.collectAsState()

    if (buttonState != ButtonState.Selected) buttonState = anim.getAState(info.button, fromScreen)


    val transition = updateTransition(targetState = buttonState, label = "")
    val animSize by transition.animateSize(
        label = "animSizeExit",
        transitionSpec = {
            when (buttonState) {
                ButtonState.Selected -> tween(goingTopTiming)
                else -> tween(200)
            }
        }
    ) { state ->
        when {
            state == ButtonState.Selected -> w.getButtonSizeTarget(info.button.key, ctxt, dens)
            state == ButtonState.NotSelected && anim.animationEnd() -> w.getButtonSizeTarget(info.button.key, ctxt, dens)
            else -> w.getButtonSize(info.button.key, ctxt, dens)
        }
    }

    if (anim.animationEnd() && buttonState == ButtonState.Selected) {
        vm.changeScreen(navigator, info)
        buttonState = ButtonState.NotSelected
    }

    Box(
        Modifier
            .wrapContentSize()
            .background(Color.Transparent)
            .onGloballyPositioned { _layoutCoordinates ->
                val offset = _layoutCoordinates.boundsInRoot().topLeft
                vm.setOffset(info.button, offset)
            }
    ) {
        AnimatedVisibility(
//            visible = visibleElements,
            visibleState = visibleButtonState,
            //todo : from is not update when use press the back button, MainScreenButton is loaded with the previous from (the one it was originaly launched with)
            enter = anim.enterTransitionByFrom(info.button.key, fromScreen.key, vm.getOffset(info.button)) ,
            exit = anim.exitTransitionByState(vm.buttonSelected.value.key, info.button.key, vm.getOffset(info.button), vm.animTriggeredButton.animationTime.value)
        ) {
            Card(
                modifier = Modifier
                    .size(
                        width = if (buttonState == ButtonState.From) xScale.value.times(animSize.width).dp else animSize.width.dp,
                        height = if (buttonState == ButtonState.From) xScale.value.times(animSize.height).dp else animSize.height.dp,
                    )
                    .clickable(enabled = info.enable) {
                        //todo : make the screen unsensitive to click/tap during the whole animation + navigation process
                        anim.setVisibleButtonTargetSate(false)
                        buttonState = ButtonState.Selected
                        vm.clickHandler(navigator, info)
                    }
                ,
                elevation = 15.dp,
                shape = MaterialTheme.shapes.medium,
//                backgroundColor = info.color,
                backgroundColor =
                if (enable)
                    w.buttonColor
                else
                    MyColor.grayDark4Plus
                ,
            ) {
                CenterText(
                    text = info.text,
                    color =
                    if (enable)
                        whiteDark4
//                        w.buttonColor
                    else
                        MyColor.whiteDark7
                )
            }
        }
    }
}

enum class ButtonState {
    Selected, NotSelected, Unknown, From
}