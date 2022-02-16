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
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonSelected
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopSizeButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopTiming
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.*


@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel(), from: String) {
    infoLog("launch", "CreatorScreen()")
//    val visibleElements by remember(testShared) {testShared.visibleElements}.collectAsState(true)
    val visibleElements by remember(testShared) {testShared.visibleElements}.collectAsState(false)
    var buttonState by remember { mutableStateOf(ButtonState.OnPlace)}
    var buttonSelected by remember { mutableStateOf(ButtonSelected.None)}

//    val anim = upd

    LaunchedEffect(key1 = "truc") { if (!visibleElements) testShared.changeVisibility() }

//    val goingTopTiming = 350
    val goingTopFading = 0.0F
    val goingTopOffsetY = -350
//    val goingTopSizeButton = 350
    val fadingOutDeg = 0.22F

    val transition = updateTransition(targetState = buttonState, label = "")
    val sizeButton by transition.animateDp(
        label = "",
//        transitionSpec = {
//            when (targetState) {
//                ButtonState.OnTop -> tween(goingTopTiming)
//                ButtonState.OnLeftSide -> tween(300)
//                ButtonState.OnRightSide -> tween(300)
//                ButtonState.Fade -> tween(200)
//                else -> tween(250)
//            }
//        }
    ) { state ->
        when (state) {
            ButtonState.OnPlace -> 300.dp
            ButtonState.OnTop -> goingTopSizeButton.dp
            ButtonState.OnLeftSide -> 200.dp
            ButtonState.OnRightSide -> 200.dp
            ButtonState.Fade -> 200.dp
        }
    }

    Column( modifier = Modifier.fillMaxSize() )
    {
        Column(modifier=Modifier.weight(1F)) {
            AnimatedVisibility(
                visible = visibleElements,
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.align(CenterHorizontally)
                ) {

                    Spacer(modifier = Modifier.size(20.dp))
                    Column( modifier = Modifier .align(CenterHorizontally) )
                    {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            onClick= {
                                buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                                testShared.changeVisibility()
                            },
                        ) { Text(text = "1") }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Column( modifier = Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                    ) {
                        Column( modifier = Modifier .align(CenterHorizontally) )
                        {
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                onClick= {
                                    buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                                    testShared.changeVisibility()
                                },
//                        shape = RoundRect(Rect, 5)
                            ) { Text(text = "2") }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
        Column( modifier = Modifier
            .align(CenterHorizontally)
            .weight(3F) )
        {
            Row(
                modifier = Modifier
                    .height(50.dp)

            ) {
                AnimatedVisibility (
                    visible = visibleElements,
//                    enter = if (from == ButtonId.LevelDiff1.key) {
//                        slideInVertically(
//                            animationSpec = tween(goingTopTiming)
//                        )
//                    }
//                    else slideInHorizontally()
//                    ,
                    exit = if (buttonSelected == ButtonSelected.Button1) {
                        slideOutVertically() + fadeOut()
                    } else {
                        slideOutHorizontally() + fadeOut()
                    },
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        modifier = Modifier
                            .height(50.dp)
                            .width(sizeButton)
                        ,
                        onClick = {
                            buttonSelected = ButtonSelected.Button1
                            buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                            testShared.changeVisibility()
                            NavViewModel(navigator).navigateTo(
                                destination = Screens.Test,
                                // 1.toString(),
//                                argStr = ButtonId.LevelDiff1.key,
                                delayTiming = goingTopTiming.toLong()
                            )
                        }
                    ) {
                        Text("button 1")
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
//                ButtonAnimated( navigator = navigator, testShared = testShared, from = from, button = ButtonId.LevelDiff2.key )
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .height(50.dp)
            ) {
//                ButtonAnimated(navigator = navigator, testShared = testShared, from = from, button = ButtonId.LevelDiff3.key)
            }
            Spacer(modifier = Modifier.size(10.dp))
//                ButtonAnimated(navigator = navigator, testShared = testShared, from = from, button = ButtonId.LevelDiff4.key)
        }
    }
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