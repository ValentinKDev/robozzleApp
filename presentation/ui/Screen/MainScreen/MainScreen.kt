package com.mobilegame.robozzle.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
fun MainScreen(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos = MainScreenWindowsInfos(),
    vm: MainScreenViewModel = viewModel(),
) {
    val visiblePopup by vm.popup.visiblePopup.collectAsState()

    BackHandler { }

    LaunchedEffect(key1 = true) {
        vm.changeVisibility()
        vm.upDateTuto()
        vm.updateButtonSelected(Screens.None)
        verbalLog("MainScreen", "launch / fromRoute: ${fromScreen.route}")
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

        if (vm.tutoVM.isMainScreenTutoActivated()) {
            tutoOverlay(vm)
        } else if (visiblePopup) {
            MainScreenPopup(vm)
        }
    }
}