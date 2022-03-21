package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.grayDark6
import com.mobilegame.robozzle.presentation.res.grayDark7
import com.mobilegame.robozzle.presentation.res.whiteDark6
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.DisplayInstuctionMenu

@Composable
fun PlayingScreen( vm: GameDataViewModel = viewModel()) {
    errorLog("Launch", "Playing Screen")

    LaunchedEffect(key1 = "PlayingScreenStart") {
        Log.i("", "_"); Log.e("LAUNCH LEVEL", "${vm.level.id} - ${vm.level.name}"); Log.i("", "_")
    }

    PlayingScreenLayers(vm) {
        DisplayAllParts(vm)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayingScreenLayers(vm: GameDataViewModel, content: @Composable () -> Unit) {
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val visisbleMenu: Boolean by remember (vm) {vm.displayInstructionsMenu}.collectAsState()

    Box( Modifier
        .fillMaxSize()
        .background(if (displayInstructionMenu) vm.data.colors.darkerBackground else Color.Transparent)
        ,
        content = {
            content.invoke()

            DragAndDropOverlay(vm)

            AnimatedVisibility(
                visible = visisbleMenu,
                enter = fadeIn(),
                exit = fadeOut()
            ) { DisplayInstuctionMenu(vm) }

            PlayingScreenPopupWin(vm = vm)
        }
    )
}

