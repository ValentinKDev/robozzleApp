package com.mobilegame.robozzle.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto.Companion.isMainScreenTutoOn
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.tutoOverlay

@Composable
fun MainScreen(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos = MainScreenWindowsInfos(),
    vm: MainScreenViewModel = viewModel(),
) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    val visiblePopup by vm.popup.visiblePopup.collectAsState()
    val tuto by remember { vm.tutoVM.tuto }.collectAsState()

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

        if (tuto.isMainScreenTutoOn()) {
            tutoOverlay(
                info = vm.ui.tuto,
                visibleElements = visibleElements,
                text = if (vm.tutoVM.getTuto().matchStep(Tuto.ClickOnProfile))
                    Tuto.ClickOnProfile.description
                else
                    Tuto.ClickOnDifficultyOne.description,
            )
        } else if (visiblePopup) {
            MainScreenPopup(vm)
        }
    }
}