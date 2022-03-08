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
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.InGameData
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.DisplayInstuctionMenu

@Composable
fun PlayingScreen(vm: GameDataViewModel) {
    LaunchedEffect(key1 = "PlayingScreenStart") {
        Log.i("", "_"); Log.e("LAUNCH LEVEL", "${vm.level.id} - ${vm.level.name}"); Log.i("", "_")
    }
//    val data: InGameData? = getLayoutCoordinates()?.let { InGameData(vm.level, LocalDensity.current, it) }

    PlayingScreenLayers(vm)
}

@Composable
fun PlayingScreenLayers(vm: GameDataViewModel) {
    val context = LocalContext.current

    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.observeAsState( false )
    val win: Int by vm.animationLogicVM.win.collectAsState(UNKNOWN)

    Box(
        Modifier
            .fillMaxSize()
            .onGloballyPositioned {
            }
    ) {
        DisplayAllParts(vm)

        DragAndDropOverlay(vm)

        if (displayInstructionMenu) {
            DisplayInstuctionMenu(vm)
        } else if (win == TRUE) {
            PlayingScreenPopupWin(vm = vm)
        }
    }
}

