package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun LevelsScreenByDifficulty(
    navigator: Navigator,
    difficulty: Int,
    levelScreenVM: LevelsScreenViewModel = viewModel(),
) {
    val levelsList: List<LevelOverView> by levelScreenVM.levelOverViewList.collectAsState()
    Log.e("LevelsScreen", "Start levelsList size ${levelsList.size}")
    levelScreenVM.loadLevelListById(difficulty)

    if (levelsList.isNotEmpty()) {
    //todo: Use a normal Column and make it scrollable using modifier and state so the fckning UI won t recompose for nothing ???
        LazyColumn {
            itemsIndexed(levelsList) { index, level ->
                DisplayLevelOverView(level, navigator)
            }
        }
    } else { Text(text = "Can't access the server and no level in the phone internal storage") }
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
            }
        ,
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
//                    Log.v("OnClick", "route = ${Screens.InGameScreen.route + "/" + level.id} ")
//                    navController.navigate(Screens.InGameScreen.route + "/" + level.id)
//                    LevelsScreenByDifficultyViewModel(navigator).navigateTo(level.id)
//                    NavViewModel(navigator).navigateTo(Screens.LevelsByID, level.id.toString())
                    NavViewModel(navigator).navigateTo(Screens.Playing, level.id.toString())
                }
        ) {
            Box(Modifier.weight(1.0f)) { DisplayLevelImage() }
            Box(Modifier.weight(2.0f)) { DisplayLevelDescription(level) }
            Box(Modifier.weight(1.0f)) { DisplayLevelState(level) }
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
fun DisplayLevelState(level: LevelOverView) {
    Box(modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .background(Color.White)
    ) {
            Text(modifier = Modifier.align(Alignment.Center),text = "X")
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
