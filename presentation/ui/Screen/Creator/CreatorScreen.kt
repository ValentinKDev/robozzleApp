package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.view.View
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
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
    val visibleGreen by remember(testShared) {testShared.vGreen}.collectAsState()
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
                tween(500)
            } else {
                tween(250)
            }
        }
    ) { state ->
        when (state) {
            ButtonState.OnPlace -> 300.dp
            ButtonState.OnTop -> 400.dp
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
                },
            ) { Text(text = "1") }
        }
        Spacer(modifier = Modifier.size(20.dp))
        AnimatedVisibility(
            visible = visibleCol,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
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
                        },
                    ) { Text(text = "2") }
                }
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
                    enter = slideInVertically(),
//                    exit = slideOutVertically()
                    exit = slideOutVertically(targetOffsetY = { it - 300})
                ) {
                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .width(sizeButton)
//                            .width(300.dp)
                        ,
                        onClick = {
                            buttonState = if (buttonState == ButtonState.OnTop) ButtonState.OnPlace else ButtonState.OnTop
                            testShared.changeVGreen()
                            visibleMagenta = !visibleMagenta
                            visiblethird = !visiblethird
                            visibleCol = !visibleCol
                            NavViewModel(navigator).navigateTo(Screens.Test)
                        }
                    ) {
                        Text("button 1")
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .height(50.dp)
            ) {
                AnimatedVisibility(
                    visibleGreen,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(300.dp)
                            .background(Color.Green)
                    ) {
                    }
                }

            }
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

class TestShared(): ViewModel() {
    private val _vGreen = MutableStateFlow<Boolean>(true)
    val vGreen: StateFlow<Boolean> = _vGreen
    fun changeVGreen() {_vGreen.value = !_vGreen.value}

    suspend fun navig(str: String) {

    }
}