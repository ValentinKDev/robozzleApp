package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
internal fun tutoFakeList(
    vm: LevelsScreenByDifficultyViewModel,
    navigator: Navigator,
    fromScreen: Screens
) {
    val visibleListState by remember {vm.visibleListState}.collectAsState()
    AnimatedVisibility(
        visibleState = visibleListState ,
        enter = vm.getEnterTransitionForList(fromScreen) ,
        exit = vm.getExitTransitionForList() ,
    ) {
        Column {
            DisplayLevelOverView(vm.levelOverviewList.value[0], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[1], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[2], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[3], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[4], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[5], vm, navigator, false)
            DisplayLevelOverView(vm.levelOverviewList.value[6], vm, navigator, false)
        }
    }
}