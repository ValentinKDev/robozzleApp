package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logClick
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart.MapViewInGame
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import gradientBackground

@Composable
fun MapLayout(vm: GameDataViewModel) {
    val stars: List<Position> by vm.animData.animatedStarsMaped.collectAsState()
    val playerInGame: PlayerInGame by vm.animData.playerAnimated.collectAsState()
//    val map: MutableList<String> by vm.animationLogicVM.data.map.collectAsState()
//    val playerAnimationState: PlayerAnimationState by vm.animationLogicVM.data.playerAnimationState.collectAsState()
//    val mapVM: MutableList<String> by vm.map.collectAsState()

//    val animationIsPlaying: Boolean by vm.animationIsPlaying.observeAsState(false)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
//            true -> vm.mapLayoutPressedToTrue()
//            false -> vm.mapLayoutPressedToFalse()
        true -> vm.mapLayoutIsPressed()
        false -> vm.mapLayoutIsReleased()
    }

    val clickable = Modifier.clickable (
        interactionSource = interactionSource,
        indication = rememberRipple(color = Color.Transparent)
    ) {
        logClick?.let { infoLog("click", "map") }
    }

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
                widthInt = (0.8F * vm.data.layout.firstPart.size.mapWidth).toInt() ,
                playerInGame,
                stars
            )
        }
    }
}