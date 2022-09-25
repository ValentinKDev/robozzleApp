package com.mobilegame.robozzle.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.*
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton

@Composable
fun MainScreen(
    navigator: Navigator,
//    screenConfig: ScreenConfig,
    fromButton: Int = MainMenuButton.None.key,
    w: MainScreenWindowsInfos = MainScreenWindowsInfos(),
    vm: MainScreenViewModel = viewModel(),

) {
    //todo : use the fromButton to make coherent animations
    val visiblePopup by vm.popup.visiblePopup.collectAsState()

    infoLog("MainScreen", "launch")

    LaunchedEffect(key1 = "Launch MainScreen") {
        vm.changeVisibility()
        vm.updateButtonSelected(MainMenuButton.None.key)
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize() ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.firstPartScreenWeight),
                content = { MainScreenFirstPart(navigator = navigator, w = w, vm = vm) }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.secondPartScreenWeight),
                content = { MainScreenSecondPart(navigator = navigator, w = w, vm = vm) }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(w.thirdPartScreenWeight),
                content = { MainScreenThirdPart(navigator = navigator, w = w, vm = vm) }
            )
        }

        if (visiblePopup) {
            MainScreenPopup(vm)
        }
    }
}