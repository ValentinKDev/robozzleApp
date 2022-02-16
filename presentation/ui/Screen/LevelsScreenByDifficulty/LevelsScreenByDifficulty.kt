package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyHeader
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@ExperimentalAnimationApi
@Composable
fun LevelsScreenByDifficulty(
    navigator: Navigator,
    levelsDifficulty: Int,
    levelScreenVM: LevelsScreenByDifficultyViewModel = viewModel(),
) {
//    var listVisible by remember { mutableStateOf(false) }
//    var headerVisible by remember { mutableStateOf(true) }
    val listVisible by remember(levelScreenVM){ levelScreenVM.listVisible}.collectAsState()
    val headerVisible by remember(levelScreenVM) { levelScreenVM.headerVisible}.collectAsState()

    Log.e("LevelsScreen", "Start")
    LaunchedEffect(key1 = "Launch LevelsScreenByDifficulty") {
        levelScreenVM.setVisibilityAtLaunch()
        levelScreenVM.loadLevelListById(levelsDifficulty)
    }

    //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data

    Box(modifier = Modifier
        .fillMaxSize()
        .background(gray6)) {
        AnimatedVisibility(
            visible = listVisible,
            enter = slideInVertically(),
            exit = slideOutVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        ) {
            LevelsScreenByDifficultyList(
                navigator = navigator,
                vm = levelScreenVM
            )
        }
        AnimatedVisibility(
            visible = headerVisible,
            enter = slideInVertically(initialOffsetY = { -70 }),
            exit = slideOutVertically(targetOffsetY = { +70 })
        ) {
            LevelsScreenByDifficultyHeader(
                navigator = navigator,
                levelDifficulty = levelsDifficulty,
                vm = levelScreenVM
            )
        }
    }
//        }
//    }
}

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
                    DisplayLevelOverView(level, navigator)
                }
            }
        } else { Text(text = "Can't access the server and no level in the phone internal storage") }
    }
}


@Composable
fun DisplayLevelOverView(level: LevelOverView, navigator: Navigator) {
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
        elevation = 8.dp,
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
            Box(Modifier.weight(1.0f)) { DisplayLevelImage() }
            Box(Modifier.weight(2.0f)) { DisplayLevelDescription(level) }
            Box(Modifier.weight(1.0f)) { DisplayLevelState(level, navigator) }
        }
    }
}

@Composable
fun DisplayLevelDescription(level: LevelOverView) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row() {
            Row(
                modifier = Modifier
                    .weight(2.0f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                    ,
                    text = "${level.id} - "
                )
                Text(text = level.name)
            }
        }
    }
}

@Composable
fun DisplayLevelState(level: LevelOverView, navigator: Navigator) {
    Box(modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .background(Color.White)
    ) {
            Text(modifier = Modifier.align(Alignment.Center),text = "X")
        Button(
            modifier = Modifier
                .height(15.dp)
                .width(15.dp)
        ,
            onClick = {
                NavViewModel(navigator).navigateTo(Screens.RanksLevel, level.id.toString())
            }
        ) {

        }
    }
}


@Composable
fun DisplayLevelImage() {
    Box(modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .background(Color.Gray)) {
        Text(modifier = Modifier.align(Alignment.Center),text = "image")
    }
}
