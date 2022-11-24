package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

@Composable
internal fun tutoFakeList(vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
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