package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greendark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterText

@Composable
fun LevelsScreenByDifficultyList(
    navigator: Navigator,
    vm: LevelsScreenByDifficultyViewModel,
//    rankingIconVM: RankingIconViewModel = viewModel()
    rankingIconVM: RankingIconViewModel = RankingIconViewModel().create(35)
) {
//    val levelsList: List<LevelOverView> by vm.levelOverViewList.collectAsState()
    val levelsList: List<LevelOverView> = vm.levelOverViewList
    val context = LocalContext.current
    val density = LocalDensity.current

    LaunchedEffect(key1 = true) {
        Log.d("LevelsScreenByDifficultyList", "Start levelsList size ${levelsList.size}")
    }


    Column() {
        Spacer(modifier = Modifier.height( MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density).height.dp))
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
            }
        ,
        elevation = 18.dp,
        backgroundColor = grayDark3
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
//            Box(Modifier.weight(1.0f)) { DisplayLevelMap(widthInt = 80, map = level.map) }
            Box(Modifier.weight(1.2f)) { vm.mapViewParamList.find { it.first == level.id } ?.let { DisplayLevelMap(it.second) } }
            Box(Modifier.weight(1.6f)) { DisplayLevelDescription(level, vm, rankingIconVM,  navigator) }
            Box(Modifier.weight(0.5f)) { DisplayLevelState(level, vm, navigator) }
            Box(Modifier.weight(0.7f)) { DisplayRankingIcon(rankingIconVM, navigator, level.id)}
        }
    }
}

@Composable
fun DisplayRankingIcon(rankingIconVM: RankingIconViewModel, navigator: Navigator, id: Int) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> rankingIconVM.rankingIconIsPressed()
        false -> rankingIconVM.rankingIconIsReleased()
    }

    Box( modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = Color.Transparent)
        ) {
            infoLog("clickable", "ranking icon")
        }
    ) {
        CenterComposable {
            RankingIconBouncing(
                vm = rankingIconVM,
                enableShadow = true,
                navigator = navigator,
                levelId = id
            )
        }
    }
}

@Composable
fun DisplayLevelDescription(level: LevelOverView, screenVM: LevelsScreenByDifficultyViewModel, rankingIconVM: RankingIconViewModel, navigator: Navigator) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
//        Box(modifier = Modifier.weight(4f)
//        ){
            CenterText(
//                text = "${level.id} - ${level.name}",
                text = "${level.name}",
                color = whiteDark4,
                modifier = Modifier
                    .padding(start = 5.dp)
                ,
            )
//        }
    }
}

@Composable
fun DisplayLevelState(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
//    Box(Modifier.fillMaxSize()){
//        Row(
//            modifier = Modifier.fillMaxSize() ,
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Column(Modifier.weight(1f)) {
    if (level.state.isWin()) {
        CenterComposable {
            Icon(
                imageVector = Icons.Outlined.Check,
                tint = greendark3,
                contentDescription = "win",
            )
        }
    }
//            }
//        }
//    }
}

@Composable
fun DisplayLevelMap(param: MapViewParam) {
    Box(modifier = Modifier
        .fillMaxSize()
        ,
    ) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                    Box(modifier = Modifier.padding(5.dp)) {
                        MapView(param)
                    }
            }
        }
    }
}
