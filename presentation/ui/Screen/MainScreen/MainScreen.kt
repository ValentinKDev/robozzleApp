package com.mobilegame.robozzle.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
fun MainScreen(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos = MainScreenWindowsInfos(),
    vm: MainScreenViewModel = viewModel(),

    ) {
    //todo : use the fromButton to make coherent animations
    val visiblePopup by vm.popup.visiblePopup.collectAsState()


    LaunchedEffect(key1 = true) {
        verbalLog("MainScreen", "launch / fromRoute: ${fromScreen.route}")
        vm.changeVisibility()
        vm.updateButtonSelected(Screens.None)
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize() ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.firstPartScreenWeight),
                content = { MainScreenFirstPart(navigator = navigator, fromScreen = fromScreen, w = w, vm = vm) }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.secondPartScreenWeight),
                content = { MainScreenSecondPart(navigator = navigator, fromScreen = fromScreen, w = w, vm = vm) }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.thirdPartScreenWeight),
                content = { MainScreenThirdPart(navigator = navigator, fromScreen = fromScreen,w = w, vm = vm) }
            )
        }

        if (visiblePopup) {
            MainScreenPopup(vm)
        }
    }
}