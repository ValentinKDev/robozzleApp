package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.DisplayInstuctionMenu
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.TrashOverlay
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.tutoLevel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayingScreen( navigator: Navigator, vm: GameDataViewModel = viewModel()) {
    val backPress = remember { mutableStateOf(false) }
    val animScreen = remember { MutableTransitionState(false) }
    val tuto by remember { vm.tutoVM.tuto }.collectAsState()

    LaunchedEffect(key1 = true) {
        Log.i("", "_");
        Log.e("LAUNCH LEVEL", "${vm.level.id} - ${vm.level.name}"); Log.i("", "_")
        animScreen.targetState = true
        if (!tuto.matchStep(Tuto.End)) vm.tutoVM.setTutoTo(Tuto.ClickOnFirstInstructionCase)
//        if (!tuto.matchStep(Tuto.End)) vm.tutoVM.setTutoTo(Tuto.Mar)
    }

    BackHandler {
        //reset Game
        //create a level
        //replace level in room
        vm.backNavHandler()
        animScreen.targetState = false
        backPress.value = true
    }

    if (!animScreen.currentState && !animScreen.targetState && backPress.value) NavViewModel(navigator).navigateToScreenLevelByDiff(vm.level.difficulty.toString())

    if (vm.isTutoLevel() && !tuto.matchStep(Tuto.End)) {
        tutoLevel(vm)
    }
    else {
        AnimatedVisibility(
            visibleState = animScreen,
//            enter = slideInHorizontally(initialOffsetX = {200}),
            enter = fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = {300}, animationSpec = tween(150)) + fadeOut(animationSpec = tween(150)),
        ) {
            PlayingScreenLayers(vm) { DisplayAllParts(vm) }
        }
    }
}

@Composable
fun PlayingScreenLayers(vm: GameDataViewModel, content: @Composable () -> Unit) {
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val visisbleMenu: Boolean by remember (vm) {vm.displayInstructionsMenu}.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .background(if (displayInstructionMenu) vm.data.colors.darkerBackground else Color.Transparent)
        ,
        content = {
            content.invoke()

            if (vm.data.layout.trash.displayTrash) TrashOverlay(vm)

            DragAndDropOverlay(vm)

            AnimatedVisibility(
                visible = visisbleMenu,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                DisplayInstuctionMenu(vm)
            }

            PlayingScreenPopupWin(vm = vm)
        }
    )
}