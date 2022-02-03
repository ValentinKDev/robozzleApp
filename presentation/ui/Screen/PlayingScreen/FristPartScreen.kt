package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.Extensions.isDirection
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.GameDataViewModel
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.ui.*

@Composable
fun MapLayout(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    val mapVM: MutableList<String> by gameDataViewModel.map.collectAsState()

//    val animationIsPlaying: Boolean by gameDataViewModel.animationIsPlaying.observeAsState(false)
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
//    Button(
//        modifier = Modifier .background(Color.Transparent)
//        ,
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
//        onClick= {
//        },
//        //todo : resize the map to smaller and give an effect oh pushing while buttonpressed
//        interactionSource = interactionSource
//    ) {
        when (isPressed) {
            true -> gameDataViewModel.mapLayoutPressedToTrue()
            false -> gameDataViewModel.mapLayoutPressedToFalse()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            mapVM.forEachIndexed { rowIndex, rowString ->
                Row(
                ) {
                    rowString.forEachIndexed { columnIndex, char ->
                        MapBox(rowIndex, columnIndex, lvl, gameDataViewModel, screenConfig)
                    }
                }
            }
        }
    }
}

@Composable
fun MapBox(rowIndex: Int, columnIndex: Int, lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    //todo adjust variable on map below to display any kind of maps
//    val charColor = lvl.map[rowIndex].get(columnIndex)


    val mapVM: MutableList<String> by gameDataViewModel.map.collectAsState()
    val charColor = mapVM[rowIndex][columnIndex]
//    val mapVM: MutableList<String> by gameDataViewModel.map.observeAsState(mutableListOf())

    val animatedStarsMap: MutableList<Position> by gameDataViewModel.animatedStarsMaped.collectAsState()

//    val animatedStarsList: MutableList<Position> by gameDataViewModel.animatedStarsList.observeAsState(lvl.starsList)

    val animationIsPlaying: Boolean by gameDataViewModel.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by gameDataViewModel.animationIsOnPause.observeAsState(false)
    val animationGoBack: Boolean by gameDataViewModel.animationGoBack.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause || animationGoBack
    val animatedPlayer: PlayerInGame by gameDataViewModel.playerAnimated.observeAsState(lvl.playerInitial)
//    val afsd: PlayerInGame by gameDataViewModel.playerAnimated.observeAsState(PlayerInGame(Position(0,0), com.mobilegame.robozzle.domain.InGame.Direction(0,1)))
    val playerDir: Char = if (animatedPlayer.pos.Match(Position(rowIndex, columnIndex))) animatedPlayer.direction.ToChar() else 'x'
//    if (!(animatedPlayer.pos.Match(Position(rowIndex, columnIndex))) ) errorLog("not match position")


    Box(modifier = Modifier
        .background(Color.Transparent)
        .size((screenConfig.mapBoxSize - screenConfig.mapBoxPadding / screenConfig.numberRows).dp)
        .padding(screenConfig.mapBoxPadding.dp)
    )
    {
        MapCase(charColor , gameDataViewModel)
//        Star(lvl, gameDataViewModel, rowIndex, columnIndex, if (animationRunningInBackground) {animatedStarsList} else {lvl.starsList})
        Star(rowIndex, columnIndex, if (animationRunningInBackground) {animatedStarsMap} else {lvl.starsList})
        if (playerDir.isDirection()) {
            PlayerDirectionIcon(playerDir, screenConfig.directionIconSize)
        }
//        else {
//            errorLog("playerDir.isDirection()", "")
//        }
    }
}

@Composable
fun MapCase(color: Char, gameDataViewModel: GameDataViewModel){
    Box(
        Modifier
            .gradientBackground(
                ColorsList(
                    color.toString(),
                    gameDataViewModel.displayInstructionsMenu.value == true
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
