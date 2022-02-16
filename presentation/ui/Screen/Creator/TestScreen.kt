package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.DonationScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@ExperimentalAnimationApi
@Composable
fun TestScreen(navigator: Navigator, animator: Animator, from: String) {
    var listVisible by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(true)}

    LaunchedEffect(key1 = "TestScreen") {
        animator.activate()
        headerVisible = true
        delay(150)
        listVisible = true
    }
    Column( modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(CenterHorizontally)
        ) {
            AnimatedVisibility(
                visible = headerVisible,
                enter = slideInVertically(
                    initialOffsetY = { 138 },
//                    animationSpec = tween()
                )
                ,
//                enter = slideInVertically(),
                exit = slideOutVertically(
                    targetOffsetY = {
                        infoLog("offsetYit", "$it")
                        it - 100
                    }
                ),
//                initiallyVisible = false,
                ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    modifier = Modifier
                        .width(450.dp)
                        .height(50.dp),
                    onClick = {
                        NavViewModel(navigator).navigateTo(Screens.Creator, argStr = from)
                    }
                ) {
//                    when (from) {
//                        ButtonId.LevelDiff1.key -> Text("button 1")
//                        ButtonId.LevelDiff2.key -> Text("button 2")
//                        ButtonId.LevelDiff3.key -> Text("button 3")
//                        ButtonId.LevelDiff4.key -> Text("button 4")
//                        else -> Text(text = "butt")
//                    }
                }
            }
            AnimatedVisibility(
                visible = listVisible,
                enter = expandVertically(animationSpec = tween(300)),
                exit = slideOutVertically(),
//                initiallyVisible = false,
            ) {
            LazyColumn {
                itemsIndexed(DonationScreenViewModel().list) { _, _element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                            }
                    ) { Text(text = _element) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color.Black)
                    ) { }
                }
            }
            }
        }
    }
}

class Animator {
    private val _state = MutableStateFlow<Boolean>(true)
    val state : StateFlow<Boolean> = _state.asStateFlow()



    fun activate() {
        _state.value = true
    }
}
