package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
//import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyHeader
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyList
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import io.ktor.util.date.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LevelsScreenByDifficulty(
    navigator: Navigator,
    levelsDifficulty: Int,
    fromScreen: Screens,
    vm: LevelsScreenByDifficultyViewModel = viewModel(),
) {
    val visibleHeaderState by remember {vm.visibleHeaderState}.collectAsState()
    val visibleListState by remember {vm.visibleListState}.collectAsState()

    //todo: get rid of this ctxt
    val ctxt = LocalContext.current
//    val headerScale = remember{ Animatable(2F) }

    LaunchedEffect(key1 = true) {
        Log.e("LevelsScreen", "Start")
        vm.setVisibilityAndLoadLevelList(levelsDifficulty, getTimeMillis())
    }
    Log.e("LevelsScreen", "$")

    BackHandler {
        vm.startExitAnimationAndPressBack()
    }
    vm.goingMainMenuListener(navigator, fromScreen, levelsDifficulty)

    vm.goingPlayScreenListener(navigator, ctxt)

    //todo: find a way to triger recomposition to reload list if server no access and need to reload the level list from internal data

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        AnimatedVisibility(
            visibleState = visibleListState ,
            enter = vm.getEnterTransitionForList(fromScreen) ,
            exit = vm.getExitTransitionForHeader() ,
        ) {
            LevelsScreenByDifficultyList(
                navigator = navigator,
                vm = vm
            )
        }
        AnimatedVisibility(
            visibleState = visibleHeaderState,
            enter = fadeIn(initialAlpha = 1F),
            exit = vm.getExitTransitionForList()
        ) {
            LevelsScreenByDifficultyHeader(
                navigator = navigator,
                levelDifficulty = levelsDifficulty,
                vm = vm
            )
        }
    }
//    }
}