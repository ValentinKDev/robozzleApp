package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
//import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto.Companion.isLevelsScreenByDifficultyOn
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyHeader
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyList
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.tutoSpecialOverlay
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import io.ktor.util.date.*

@Composable
fun LevelsScreenByDifficulty(
    navigator: Navigator,
    levelsDifficulty: Int,
    fromScreen: Screens,
    vm: LevelsScreenByDifficultyViewModel = viewModel(),
) {
    val visibleHeaderState by remember {vm.visibleHeaderState}.collectAsState()
    val visibleListState by remember {vm.visibleListState}.collectAsState()
    val rankingLevelIdSelected by remember {vm.rankingIconVM.levelSelected}.collectAsState()
    val tuto by remember { vm.tutoVM.tuto }.collectAsState()
    //todo: get rid of this ctxt
    val ctxt = LocalContext.current

    LaunchedEffect(key1 = true) {
        Log.e("LevelsScreen", "Start")
        vm.init(levelsDifficulty)
    }

    BackHandler {
        vm.startExitAnimationAndPressBack()
    }

    vm.goingMainMenuListener(navigator, fromScreen, levelsDifficulty)

    vm.goingPlayScreenListener(navigator, ctxt)

    rankingLevelIdSelected?.let {
        vm.goingRankingLevelListener(navigator, it)
    }

    //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        if (tuto.isLevelsScreenByDifficultyOn(levelsDifficulty) && vm.levelOverviewList.value.isNotEmpty()) {
            tutoSpecialOverlay(vm, fromScreen, navigator)
        } else {
            AnimatedVisibility(
                visibleState = visibleListState ,
                enter = vm.getEnterTransitionForList(fromScreen) ,
                exit = vm.getExitTransitionForList() ,
            ) {
                LevelsScreenByDifficultyList(
                    navigator = navigator,
                    vm = vm
                )
            }
        }
        AnimatedVisibility(
            visibleState = visibleHeaderState,
            enter = fadeIn(initialAlpha = 1F),
            exit = vm.getExitTransitionForHeader()
        ) {
            LevelsScreenByDifficultyHeader(
                levelDifficulty = levelsDifficulty,
                vm = vm
            )
        }
    }
}