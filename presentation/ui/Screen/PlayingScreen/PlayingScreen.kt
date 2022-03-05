package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.Print_List_Position
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.res.normalInGameBackGround
import com.mobilegame.robozzle.presentation.res.whiteDark4

//@DelicateCoroutinesApi
@Composable
fun PlayingScreen(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel = viewModel()) {
    val win: Int by gameDataViewModel.win.collectAsState(UNKNOWN)

    gameDataViewModel.lvlId = level.id
    gameDataViewModel.lvlDifficulty = level.difficulty
    gameDataViewModel.lvlName = level.name

    Log.i("", "_")
    Log.e("LAUNCH LEVEL", "${level.id} - ${level.name}")
    Log.i("", "_")
    infoLog("player position ", "${level.playerInitial.pos.line}, ${level.playerInitial.pos.column}")
    Print_List_Position("stars", level.starsList)
//    Print_List_String(level.map)
//        todo: Display a card with a bit of a delay to explain clearly the player has won this level
    when (win) {
        TRUE -> { Log.e("", "win trigger recompostion of LaunchLevel") }
        FALSE -> { Log.e("", "lost trigger recompostion of LaunchLevel") }
    }
    //todo init the the launchEffect?
    gameDataViewModel.init(level)

    level.breadcrumb.CreateBreadcrumb()

    PlayLevelCompose(level, gameDataViewModel)
}

@Composable
fun PlayLevelCompose(level: RobuzzleLevel, vm: GameDataViewModel) {
    val context = LocalContext.current
    val screenConfig = ScreenConfig(context, level)
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.observeAsState( false )

    Box(Modifier.fillMaxSize()) {
        DisplayGameScreen(level, vm, screenConfig)

        DragAndDropOverlay(vm)
        if (displayInstructionMenu) {
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable { vm.ChangeInstructionMenuState() }
            ) {
                DisplayInstuctionMenu(level, vm, screenConfig)
            }
        }
    }
}
