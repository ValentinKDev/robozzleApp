package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.header
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.list
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.map

@Composable
fun RanksLevelScreen(
    navigator: Navigator,
    levelId: Int,
    vm: RanksLevelScreenViewModel = viewModel(),
    fromScreen: Screens
) {
    val visibleScreen by remember { vm.visibleScreen }.collectAsState()

    LaunchedEffect(true) {
        infoLog("RanksLevelScreent", "Start")
        vm.load(levelId)
        vm.setVisibleScreenTo(true)
    }

    BackHandler {
        vm.backHandler(navigator, fromScreen)
    }

    AnimatedVisibility(
        visible = visibleScreen,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row( Modifier .weight(vm.ui.header.ratios.height)
                ) {
                    header(vm)
                }
                Row( Modifier .weight(vm.ui.map.ratios.height)
                ) {
                    map(vm)
                }
                Row(Modifier.weight(vm.ui.list.ratios.height)) {
                    list(vm)
                }
            }
    }
}