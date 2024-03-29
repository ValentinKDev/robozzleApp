package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logClick
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart.MapViewInGame
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun MapLayout(vm: GameDataViewModel, enableClickSpeed: Boolean = true, enableClickStopMark: Boolean = true) {
    val stars: List<Position> by vm.animData.animatedStarsMaped.collectAsState()
    val caseStop: List<Position> by vm.animData.mapCaseSelection.collectAsState()
    val playerInGame: PlayerInGame by vm.animData.playerAnimated.collectAsState()

    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> vm.mapLayoutIsPressed()
        false -> vm.mapLayoutIsReleased()
    }

    val clickable = if (enableClickSpeed)
        Modifier.clickable (
        interactionSource = interactionSource,
        indication = rememberRipple(color = Color.Transparent)
    ) {
        logClick?.let { infoLog("click", "map") }
    } else Modifier

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .wrapContentSize()
            .then(clickable)
        ,
    ) {
        //todo : resize the map to smaller and give an effect oh pushing while buttonpressed
        CenterComposable {
            MapViewInGame(
                vm = vm,
                playerInGame,
                stars,
                caseStop = caseStop,
                filter = displayInstructionMenu,
                enableClickStopMark = enableClickStopMark,
            )
        }
    }
}