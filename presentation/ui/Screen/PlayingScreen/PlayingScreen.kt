package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

//@DelicateCoroutinesApi
@Composable
fun PlayingScreen(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel = viewModel()) {
//    val win: Int by gameDataViewModel.win.collectAsState(UNKNOWN)


    LaunchedEffect(key1 = "PlayingScreenStart") {
        Log.i("", "_")
        Log.e("LAUNCH LEVEL", "${level.id} - ${level.name}")
        Log.i("", "_")
        gameDataViewModel.init(level)
    }
//    when (win) {
//        TRUE -> { Log.e("", "win trigger recompostion of LaunchLevel") }
//        FALSE -> { Log.e("", "lost trigger recompostion of LaunchLevel") }
//    }



    PlayingScreenLayers(level, gameDataViewModel)
}

@Composable
fun PlayingScreenLayers(level: RobuzzleLevel, vm: GameDataViewModel) {
    val context = LocalContext.current
    val screenConfig = ScreenConfig(context, level)

    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.observeAsState( false )
    val win: Int by vm.win.collectAsState(UNKNOWN)

    Box(Modifier.fillMaxSize()) {
        DisplayGameScreen(level, vm, screenConfig)

        DragAndDropOverlay(vm)

        if (displayInstructionMenu) {
            Box( Modifier
                .fillMaxSize()
                .clickable { vm.ChangeInstructionMenuState() }
            ) {
                DisplayInstuctionMenu(level, vm, screenConfig)
            }
        } else if (win == TRUE) {
            PlayingScreenPopupWin(vm = vm)
        }
    }
}

