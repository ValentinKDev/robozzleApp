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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.MainMenuViewModel
import com.mobilegame.robozzle.toREMOVE.PlayerData
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun LevelsScreen(
    navController: NavController,
//    levelsList: List<RobuzzleLevel>,
    playerData: PlayerData,
    levelScreenVM: 
//    mLevelViewModel: MainMenuViewModel
) {
    val levelsList: List<RobuzzleLevel> by mLevelViewModel.rbAllLevelList.observeAsState(initial = emptyList())
    Log.e("LevelsScreen", "Start levelsList size ${levelsList.size}")

    if (levelsList.isNotEmpty()) {
//        Log.e("LevelsScreen", "size levelsList ${levelsList.size}")
    //todo: Use a normal Column and make it scrollable using modifier and state so the fckning UI won t recompose for nothing ???
        LazyColumn {
            itemsIndexed(levelsList) { index, level ->
                DisplayLevelOverView(level, navController, playerData)
            }
        }
    } else { Text(text = "Can't access the server and no level in the phone internal storage") }
}

@Composable
fun DisplayLevelOverView(level: RobuzzleLevel, navController: NavController, playerData: PlayerData) {
//        Log.e("Level ${level.id}", "name ${level.name}")
//        level.map.forEach {
//            Log.v("", it)
//        }
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
//                    val level
                    Log.v("OnClick", "route = ${Screens.InGameScreen.route + "/" + level.id} ")
//                    level.guideline.CreateGuideline()
                    navController.navigate(Screens.InGameScreen.route + "/" + level.id)
                }
        ) {
            Box(Modifier.weight(1.0f)) { DisplayLevelImage() }
            Box(Modifier.weight(2.0f)) { DisplayLevelDescription(level) }
            Box(Modifier.weight(1.0f)) { DisplayLevelState(level, playerData) }
        }
    }
}

@Composable
fun DisplayLevelDescription(level: RobuzzleLevel) {
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
fun DisplayLevelState(level: RobuzzleLevel, playerData: PlayerData) {
    Box(modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .background(Color.White)
    ) {
//        if (playerData.WinsContain(level.id)) Text(modifier = Modifier.align(Alignment.Center),text = "V")
//        else
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
