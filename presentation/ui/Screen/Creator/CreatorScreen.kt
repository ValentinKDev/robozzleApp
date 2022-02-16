package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopSizeButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopTiming
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.*


@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel(), from: String) {
    infoLog("launch", "CreatorScreen()")
}

@ExperimentalAnimationApi
@Composable
fun ButtonAnimated(navigator: Navigator, testShared: TestShared, from: String, button: String) {
    val visibleElements by remember(testShared) {testShared.visibleElements}.collectAsState(false)
    var buttonState by remember { mutableStateOf(ButtonState.OnPlace)}

    val transition = updateTransition(targetState = buttonState, label = "")
    val animWidth by transition.animateDp( label = "",
    ) { state ->
        when (state) {
            ButtonState.OnPlace -> 300.dp
            ButtonState.OnTop -> goingTopSizeButton.dp
            ButtonState.OnLeftSide -> 200.dp
            ButtonState.OnRightSide -> 200.dp
            ButtonState.Fade -> 200.dp
        }
    }
    AnimatedVisibility(
        visible = visibleElements,
//        enter = if (from == ButtonId.LevelDiff2.key)
//            slideInVertically(animationSpec = tween(goingTopTiming))
//        else fadeIn()
//        ,
//        exit = if (buttonSelected == ButtonSelected.Button1) {
        exit = if (buttonState == ButtonState.OnTop) {
            slideOutVertically() + fadeOut()
        } else {
            fadeOut()
        },
        ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            modifier = Modifier
                .height(50.dp)
                .width(animWidth)
            ,
            onClick = {
//                buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                buttonState = ButtonState.OnTop
                testShared.changeVisibility()
                NavViewModel(navigator).navigateTo(Screens.Test, button)
            }
        ) {
            when(button) {
//                ButtonId.LevelDiff1.key -> Text("button 1")
//                ButtonId.LevelDiff2.key -> Text("button 2")
//                ButtonId.LevelDiff3.key -> Text("button 3")
//                ButtonId.LevelDiff4.key -> Text("button 4")
                else -> Text("butt")
            }
        }
    }
}


//sealed class Button
//enum class ButtonSelected {
//    None, Button1, Button2, Button3, Button4, ButtonConfig
//}

private enum class ButtonState {
    OnPlace, OnTop, OnLeftSide, OnRightSide, Fade
}

class TestShared(): ViewModel() {
//    private val _buttonState = MutableStateFlow<ButtonState>(ButtonState.OnPlace)
//    val buttonState: StateFlow<ButtonState> = _buttonState
//    fun buttonStateToTop() {_buttonState.value = ButtonState.OnTop}


//    private val _visibleElements = MutableStateFlow<Boolean>(true)
    private val _visibleElements = MutableStateFlow<Boolean>(false)
    val visibleElements: StateFlow<Boolean> = _visibleElements
    fun changeVisibility() {_visibleElements.value = !_visibleElements.value}
}