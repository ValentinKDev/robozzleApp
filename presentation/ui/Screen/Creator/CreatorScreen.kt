package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.view.View
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import com.mobilegame.robozzle.presentation.res.gray0
import com.mobilegame.robozzle.presentation.res.gray5
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel()) {
    infoLog("launch", "CreatorScreen()")
//    val visibleButton2 by remember(testShared) {testShared.vGreen}.collectAsState(initial = true)
    val visibleButton2 by remember { mutableStateOf(true)}
//    val visibleButton2 by remember(testShared) {testShared.shared}.collectAsState()
//    var visibleGreen by remember { mutableStateOf(true) }
    var visibleMagenta by remember { mutableStateOf(true) }
    var visiblethird by remember { mutableStateOf(true) }
    var visibleCol by remember { mutableStateOf(true) }

    var buttonState by remember { mutableStateOf(ButtonState.OnPlace)}

    val transition = updateTransition(targetState = buttonState, label = "")

    val col: Long

    val sizeButton by transition.animateDp(
        label = "",
        transitionSpec = {
            if (targetState == ButtonState.OnTop) {
                tween(3000)
            } else {
                tween(250)
            }
        }
    ) { state ->
        when (state) {
            ButtonState.OnPlace -> 300.dp
            ButtonState.OnTop -> 500.dp
        }
    }
        
    Column( modifier = Modifier
        .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .align(CenterHorizontally)
            ,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                onClick= {
                    buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                    testShared.changeVGreen()
                    visibleMagenta = !visibleMagenta
                    visiblethird = !visiblethird
                    visibleCol = !visibleCol
                    testShared.changeShared()
                },
            ) { Text(text = "1") }
        }
        Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
//                    .background(gray0)
                ,
            ) {
                Column(
                    modifier = Modifier
                        .align(CenterHorizontally)
                    ,
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick= {
                            buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                            testShared.changeVGreen()
                            visibleMagenta = !visibleMagenta
                            visiblethird = !visiblethird
                            visibleCol = !visibleCol
                        },
//                        shape = RoundRect(Rect, 5)
                    ) { Text(text = "2") }
                }
            }
        Column(
            modifier = Modifier
//                .fillMaxHeight()
//                .width(400.dp)
                .align(CenterHorizontally)
//                .background(gray5)
            ,
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .height(50.dp)
            ) {
                AnimatedVisibility(
                    visible = visibleMagenta,
                    enter = slideInHorizontally(),
//                    exit = slideOutVertically(targetOffsetY = ,animationSpec = )
                    exit = slideOutVertically(targetOffsetY = { it - 370})
//                    exit = slideOutVertically(
//                        targetOffsetY = { it - 370},
//
//                    )
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        modifier = Modifier
                            .height(50.dp)
                            .width(sizeButton)
                        ,
                        onClick = {
                            buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                            testShared.changeVGreen()
                            visibleMagenta = !visibleMagenta
                            visiblethird = !visiblethird
                            visibleCol = !visibleCol
                            testShared.changeShared()
                            NavViewModel(navigator).navigateTo(Screens.Test, 1.toString())
                        }
                    ) {
                        Text("button 1")
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
//            Row(
//                modifier = Modifier
//                    .height(50.dp)
//            ) {
                AnimatedVisibility(
//                    visibleGreen,
                    visible = visibleButton2,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        modifier = Modifier
                            .height(50.dp)
                            .width(300.dp)
                        ,
                        onClick = {
                            buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                            testShared.changeVGreen()
                            visibleMagenta = !visibleMagenta
                            visiblethird = !visiblethird
                            visibleCol = !visibleCol
                            testShared.changeShared()
                            NavViewModel(navigator).navigateTo(Screens.Test, 2.toString())
                        }
                    ) { Text("button 2") }
                }
//            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .height(50.dp)
            ) {
                AnimatedVisibility(
                    visible = visiblethird,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .width(300.dp)
                        ,
                        onClick = { }
                    ) {
                        Text("button")
                    }
                }
            }
        }
    }
//    }
}

private enum class ButtonState {
    OnPlace, OnTop
}

//enum class ButtonOrigin {
//
//}

class TestShared(): ViewModel() {
    private val _shared = MutableSharedFlow<Boolean>()
    val shared = _shared.asSharedFlow()
    fun changeShared() {
        viewModelScope.launch() {
            _shared.tryEmit(shared.last())
        }
    }

    private val _vGreen = MutableStateFlow<Boolean>(true)
    val vGreen: StateFlow<Boolean> = _vGreen
    fun changeVGreen() {_vGreen.value = !_vGreen.value}

    suspend fun navig(str: String) {

    }
}

//class