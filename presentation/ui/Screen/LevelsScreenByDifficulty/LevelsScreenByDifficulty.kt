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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.Extensions.backColor
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyHeader
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyList
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@ExperimentalAnimationApi
@Composable
fun LevelsScreenByDifficulty(
    navigator: Navigator,
    levelsDifficulty: Int,
    levelScreenVM: LevelsScreenByDifficultyViewModel = viewModel(),
) {
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
        .background(grayDark6)
//        .backColor(grayDark6)
    )
    {
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
            enter = slideInVertically(initialOffsetY = { -70 }, animationSpec = tween(200)),
            exit = slideOutVertically(targetOffsetY = { +70 })
        ) {
            LevelsScreenByDifficultyHeader(
                navigator = navigator,
                levelDifficulty = levelsDifficulty,
                vm = levelScreenVM
            )
        }
    }
}

