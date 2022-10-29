package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterText

@Composable
fun LevelsScreenByDifficultyList(
    navigator: Navigator,
    vm: LevelsScreenByDifficultyViewModel,
    rankingIconVM: RankingIconViewModel = viewModel()
) {
    val levelsList: List<LevelOverView> by vm.levelOverViewList.collectAsState()

    LaunchedEffect(key1 = true) {
        Log.d("LevelsScreenByDifficultyList", "Start levelsList size ${levelsList.size}")
    }

    Column() {
        Spacer(modifier = Modifier.height(100.dp))
        if (levelsList.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(levelsList) { index, level ->
                    DisplayLevelOverView(level, vm, rankingIconVM, navigator)
                }
            }
        } else { Text(text = "Can't access the server and no level in the phone internal storage") }
    }
}

@Composable
fun DisplayLevelOverView(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, rankingIconVM: RankingIconViewModel, navigator: Navigator) { val ctxt = LocalContext.current
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
                vm.startExitAnimationAndPressLevel(level.id)
//                vm.startExitAnimationAndPressBack()
//                vm.navigateToLevel(navigator, level.id, ctxt)
            }
        ,
        elevation = 18.dp,
        backgroundColor = grayDark3
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
            Box(Modifier.weight(1.0f)) { DisplayLevelMap(widthInt = 80, map = level.map) }
            Box(Modifier.weight(2.0f)) { DisplayLevelDescription(level, vm, rankingIconVM,  navigator) }
            Box(Modifier.weight(1.0f)) { DisplayLevelState(level, vm, navigator) }
        }
    }
}

@Composable
fun DisplayLevelDescription(level: LevelOverView, screenVM: LevelsScreenByDifficultyViewModel, rankingIconVM: RankingIconViewModel, navigator: Navigator) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
//        true -> screenVM.rankingIconIsPressed()
//        false -> screenVM.rankingIconIsReleased(navigator, level.id)
        true -> rankingIconVM.rankingIconIsPressed()
        false -> rankingIconVM.rankingIconIsReleased(navigator, level.id)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(4f)
//            .background(Color.Red)
        ){
            CenterText(
                text = "${level.id} - ${level.name}",
                color = whiteDark4,
                modifier = Modifier
                    .padding(start = 5.dp)
                ,
            )
        }
        Box(
            modifier = Modifier .weight(5f)
            ,
            contentAlignment = Alignment.Center
        ) {
            Column( modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Box( modifier = Modifier
                        .wrapContentSize()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(color = Color.Transparent)
                        ) {
                            infoLog("clickable", "ranking icon")
                        }
                    ) {
                        RankingIconBouncing(sizeAtt = 35, rankingIconVM = rankingIconVM, isPressed = isPressed, enableShadow = true)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayLevelState(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
    Box(Modifier.fillMaxSize()){
        Row(
            modifier = Modifier.fillMaxSize() ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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
//                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                    Box(modifier = Modifier.padding(5.dp)) {
                        MapView(widthInt = widthInt, mapParam = map)
                    }
//                }
            }
        }
    }
}
