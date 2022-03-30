package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logAnimMap
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.elements.PlayerIcon
import com.mobilegame.robozzle.presentation.ui.elements.StarIcon
import com.mobilegame.robozzle.presentation.ui.elements.WhiteSquare
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.toCaseColor
import com.mobilegame.robozzle.utils.infixStyle.contain
import gradientBackground

@Composable
fun MapViewInGame(
    vm: GameDataViewModel,
    playerInGame: PlayerInGame? = null,
    stars: List<Position> = emptyList(),
    caseStop: List<Position> = emptyList(),
    filter: Boolean,
) {
    //todo : put those calculs in a VM ?
    val map: List<String> by vm.animData.map.collectAsState()
    logAnimMap?.let {
        infoLog("stars", "$stars")
        infoLog("Map View In Game", "player pos ${playerInGame?.pos}")
        infoLog("action", "${vm.animData.getActionToRead()}")
    }

    var casePosition = Position.Error
    Box(
        Modifier
            .height(vm.data.layout.firstPart.size.mapHeightDp)
            .width(vm.data.layout.firstPart.size.mapWidthDp)
    ) {
        Column( Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            map.forEachIndexed { _rowIndex, rowString ->
                Row {
                    rowString.forEachIndexed { _columnIndex, _color ->
                        casePosition = Position(_rowIndex, _columnIndex)
                        Box {
                            DrawMapCase(
                                playerInGame = playerInGame,
                                stars = stars,
                                casePos = casePosition,
                                caseColor = _color,
                                filter = filter,
                                vm = vm
                            )
                            if (caseStop.contains(casePosition)) {
                                WhiteSquare(
                                    sizeDp = vm.data.layout.firstPart.size.mapCaseDp,
                                    stroke = vm.data.layout.firstPart.size.mapCaseStroke,
                                    vm = vm
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawMapCase(
    playerInGame: PlayerInGame?,
    stars: List<Position>,
    casePos: Position,
    caseColor: Char,
    filter: Boolean,
    vm: GameDataViewModel,
) {
    Box(
        Modifier
            .background(Color.Transparent)
            .size(vm.data.layout.firstPart.size.mapCaseDp),
    ) {
        if (caseColor != '.') {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vm.data.layout.firstPart.size.mapCasePaddingDp),
                shape = RectangleShape,
                elevation = 7.dp,
            ) {
                Box(
                    Modifier
                        .gradientBackground(
                            mapCaseColorList(
                                caseColor,
                                filter
                            ), 135f
                        )
                        .clickable {
                            infoLog("click", "on map case $casePos")
                            vm.animData.mapCaseSelectionHandler(casePos)
                        }
                        .fillMaxSize()
                ) {
                    playerInGame?.let {
                        if (playerInGame.pos == casePos) {
                            CenterComposable {
                                PlayerIcon(direction = it.direction, data = vm.data.layout.firstPart, colors = vm.data.colors, caseColor.toCaseColor())
                            }
                        }
                        else if (stars contain casePos) {
                            if (stars contain casePos) {
                                CenterComposable {
                                    StarIcon(vm.data.layout.firstPart, vm.data.colors)
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            playerInGame?.let {
                if (it.pos == casePos) {
                    errorLog("player out map - postion", "$casePos")
                    CenterComposable {
                        PlayerIcon(direction = it.direction, vm.data.layout.firstPart, colors = vm.data.colors, caseColor.toCaseColor())
                    }
                }
            }
        }
    }
}