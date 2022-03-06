package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.DisplayInstuctionMenu

@Composable
fun PlayingScreen(level: RobuzzleLevel, vm: GameDataViewModel = viewModel()) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var layoutCoordinates: LayoutCoordinates

    Box(Modifier.fillMaxSize().onGloballyPositioned {
        vm.data.init(level, context, density, it)
    }
    )

    LaunchedEffect(key1 = "PlayingScreenStart") {
        Log.i("", "_"); Log.e("LAUNCH LEVEL", "${level.id} - ${level.name}"); Log.i("", "_")
        vm.init(level, context, density)
    }

    PlayingScreenLayers(level, vm)
}

@Composable
fun PlayingScreenLayers(level: RobuzzleLevel, vm: GameDataViewModel) {
    val context = LocalContext.current
    val screenConfig = ScreenConfig(context, level)

    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.observeAsState( false )
    val win: Int by vm.win.collectAsState(UNKNOWN)

    Box(Modifier
        .fillMaxSize()
        .onGloballyPositioned {
        }
    ) {
        DisplayGameScreen(level, vm, screenConfig)

        DragAndDropOverlay(vm)

        if (displayInstructionMenu) {
            DisplayInstuctionMenu(level, vm)
        } else if (win == TRUE) {
            PlayingScreenPopupWin(vm = vm)
        }
    }
}

