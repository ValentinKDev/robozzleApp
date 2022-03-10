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
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart.MapViewInGame
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.MapCleaner

@Composable
fun MapLayout(vm: GameDataViewModel) {
//    val map: MutableList<String> by vm.animationLogicVM.data.map.collectAsState()
//    val playerAnimationState: PlayerAnimationState by vm.animationLogicVM.data.playerAnimationState.collectAsState()
//    val mapVM: MutableList<String> by vm.map.collectAsState()

//    val animationIsPlaying: Boolean by vm.animationIsPlaying.observeAsState(false)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val clickable = Modifier.clickable (
        interactionSource = interactionSource,
        indication = rememberRipple(color = Color.Transparent)
//        indication = LocalIndication.current
    ) { infoLog("clickable", "map") }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .wrapContentSize()
            .then(clickable)
        ,
    ) {
        //todo : resize the map to smaller and give an effect oh pushing while buttonpressed
        when (isPressed) {
            true -> vm.mapLayoutPressedToTrue()
            false -> vm.mapLayoutPressedToFalse()
        }
//        MapView(widthInt = (200).toInt(), mapParam = map)
        CenterComposable {

            MapViewInGame(
                vm = vm,
                widthInt = (0.8F * vm.data.layout.firstPart.size.mapWidth).toInt()
            )
        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//            ,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            mapVM.forEachIndexed { rowIndex, rowString ->
//                Row(
//                ) {
//                    rowString.forEachIndexed { columnIndex, char ->
//                        MapBox(rowIndex, columnIndex, lvl, vm, screenConfig)
//                    }
//                }
//            }
//        }
    }
}

@Composable
fun MapBox(rowIndex: Int, columnIndex: Int, vm: GameDataViewModel) {
    //todo adjust variable on map below to display any kind of maps
//    val charColor = lvl.map[rowIndex].get(columnIndex)


//    val mapVM: MutableList<String> by vm.map.observeAsState(mutableListOf())


//    val animatedStarsList: MutableList<Position> by vm.animatedStarsList.observeAsState(lvl.starsList)

//    val animationIsPlaying: Boolean by vm.animationIsPlaying.observeAsState(false)
//    val animationIsOnPause: Boolean by vm.animationIsOnPause.observeAsState(false)
//    val animationGoBack: Boolean by vm.animationGoBack.observeAsState(false)
//    val animationRunningInBackground = animationIsPlaying || animationIsOnPause || animationGoBack

//    val animatedPlayer: PlayerInGame by vm.playerAnimated.observeAsState(vm.level.playerInitial.toPlayerInGame())



//    val playerDir: Char = if (animatedPlayer.pos.Match(Position(rowIndex, columnIndex))) animatedPlayer.direction.ToChar() else 'x'
//    val animatedStarsMap: MutableList<Position> by vm.animatedStarsMaped.collectAsState()
//    val charColor = mapVM[rowIndex][columnIndex]
//    Box(modifier = Modifier
//        .background(Color.Transparent)
//        .size((screenConfig.mapBoxSize - screenConfig.mapBoxPadding / screenConfig.numberRows).dp)
//        .padding(screenConfig.mapBoxPadding.dp)
//    )
//    {
//        MapCase(charColor , vm)
//        Star(rowIndex, columnIndex, if (animationRunningInBackground) {animatedStarsMap} else {lvl.starsList})
//        if (playerDir.isDirection()) {
//            PlayerDirectionIcon(playerDir, screenConfig.directionIconSize)
//        }
//    }
}

@Composable
fun MapCase(color: Char, vm: GameDataViewModel){
    Box(
        Modifier
            .gradientBackground(
                ColorsList(
                    color,
                    vm.displayInstructionsMenu.value == true
                ), 135f
            )
            .fillMaxSize()
    ) { }
}


@Composable
fun Star(rowIndex: Int, columnIndex: Int, stars: MutableList<Position>) {

    stars.forEachIndexed { index, position ->
        if (position.Match(Position(rowIndex, columnIndex))) {
            Box(modifier = Modifier.fillMaxSize() ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = Color.Yellow,
                    contentDescription = "star",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
