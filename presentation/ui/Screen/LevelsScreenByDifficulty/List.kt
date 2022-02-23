package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.graphics.Paint
import android.icu.util.Calendar
import android.provider.CalendarContract
import android.util.Log
import android.util.Size
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.gray6
import com.mobilegame.robozzle.presentation.res.grayDark2
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.NextButton
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun LevelsScreenByDifficultyList(
    navigator: Navigator,
    vm: LevelsScreenByDifficultyViewModel,
) {
    val levelsList: List<LevelOverView> by vm.levelOverViewList.collectAsState()
    Log.e("LevelsScreen", "Start levelsList size ${levelsList.size}")

    Column() {
        Spacer(modifier = Modifier.height(100.dp))
        if (levelsList.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(levelsList) { index, level ->
                    DisplayLevelOverView(level, vm, navigator)
                }
            }
        } else { Text(text = "Can't access the server and no level in the phone internal storage") }
    }
}

@Composable
fun DisplayLevelOverView(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 6.dp,
                end = 6.dp,
            )
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                NavViewModel(navigator).navigateTo(Screens.Playing, level.id.toString())
            }
        ,
//        elevation = 8.dp,
        elevation = 18.dp,
        backgroundColor = grayDark2
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
            Box(Modifier.weight(1.0f)) { DisplayLevelMap(widthInt = 100, map = level.map) }
            Box(Modifier.weight(2.0f)) { DisplayLevelDescription(level) }
            Box(Modifier.weight(1.0f)) { DisplayLevelState(level, vm, navigator) }
        }
    }
}

@Composable
fun DisplayLevelDescription(level: LevelOverView) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${level.id} - ${level.name}",
            color = whiteDark4,
            modifier = Modifier
                .padding(start = 5.dp)
                .align(Alignment.CenterHorizontally)
            ,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DisplayLevelState(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var pressed by remember { mutableStateOf(false) }

    val clickable = Modifier.clickable (
        interactionSource = interactionSource,
        indication = rememberRipple(color = Color.Transparent)
    ,
    ) {
        infoLog("clickable", "ranking icon")
        NavViewModel(navigator).navigateTo(destination = Screens.RanksLevel, argStr = level.id.toString(), delayTiming = 500)
    }

//    when (isPressed) {
    when (pressed) {
        true -> vm.rankingIconIsPressed()
        false -> vm.rankingIconIsReleased()
    }

    var timer: Long = 0

    Box(Modifier.fillMaxSize()){
        Row(
            modifier = Modifier.fillMaxSize() ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
//                        .then(clickable)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                val minute = Calendar.getInstance().time
                                val millisStart = Calendar.getInstance().timeInMillis
                                errorLog("time ", "start $millisStart")
//                                vm.rankingIconIsPressed()
                                    pressed = true
                                    tryAwaitRelease()
                                    pressed = false
                                val millisEnd = Calendar.getInstance().timeInMillis
                                errorLog("time ", "end $millisStart")
//                                    vm.rankingIconIsReleased()
                                val diff = millisEnd - millisStart
                                errorLog("diff ", "$diff")
                                NavViewModel(navigator).navigateTo(destination = Screens.RanksLevel, argStr = level.id.toString(), delayTiming = if (diff < 150) diff * 5 else if (diff < 300) diff * 4 else if (diff < 600) diff * 2 else 600)
                                }
                            )
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Box( modifier = Modifier .align(Alignment.Center)
                    ) {
//                        RankingIconBouncing(sizeAtt = 40, vm = vm, isPressed = isPressed)
                        RankingIconBouncing(sizeAtt = 40, vm = vm, isPressed = pressed)
                    }
                }
            }
            Column(Modifier.weight(1f)) {
                Text(text = "X", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun DisplayLevelMap(widthInt: Int, map: List<String>) {
    Box(modifier = Modifier
        .fillMaxSize()
        ,
    ) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                MapView(widthInt = widthInt, map = map)
            }
        }
    }
}
