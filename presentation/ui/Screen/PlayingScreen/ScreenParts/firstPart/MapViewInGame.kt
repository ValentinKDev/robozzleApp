package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart

import androidx.compose.foundation.background
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
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.elements.PlayerIcon
import com.mobilegame.robozzle.presentation.ui.elements.StarIcon
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.toCaseColor
import com.mobilegame.robozzle.utils.infixStyle.contain
import gradientBackground

@Composable
fun MapViewInGame(
    vm: GameDataViewModel,
    playerInGame: PlayerInGame? = null,
    stars: List<Position> = emptyList(),
    filter: Boolean,
) {
    //todo : put those calculs in a VM ?
    val map: List<String> by vm.animData.map.collectAsState()
    logAnimMap?.let {
        infoLog("stars", "$stars")
        infoLog("Map View In Game", "player pos ${playerInGame?.pos}")
        infoLog("action", "${vm.animData.getActionToRead()}")
    }
    var casePosition = Position.Zero

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
                        Box(
                            Modifier
                                .background(Color.Transparent)
                                .size(vm.data.layout.firstPart.size.mapCaseDp),
                        ) {
                            if (_color != '.') {
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
//                                        .padding(1.dp),
                                        .padding(vm.data.layout.firstPart.size.mapCasePaddingDp),
                                shape = RectangleShape,
                                    elevation = 7.dp,
                                ) {
                                    Box(
                                        Modifier
                                            .gradientBackground(
                                                mapCaseColorList(
                                                    _color,
                                                    filter
                                                ), 135f
                                            )
                                            .fillMaxSize()
                                    ) {
//                                        content.invoke()
                                        playerInGame?.let {
                                            if (playerInGame.pos == casePosition) {
                                                CenterComposable {
                                                    PlayerIcon(direction = it.direction, data = vm.data.layout.firstPart, _color.toCaseColor())
                                                }
                                            }
                                            else if (stars contain casePosition) {
                                                if (stars contain casePosition) {
                                                    CenterComposable {
                                                        StarIcon(vm.data.layout.firstPart)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                playerInGame?.let {
                                    if (it.pos == casePosition) {
                                        errorLog("player out map - postion", "$casePosition")
                                        CenterComposable {
                                            PlayerIcon(direction = it.direction, vm.data.layout.firstPart, _color.toCaseColor())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawMapCase(caseSize: Int, caseColor: Char, content: @Composable () -> Unit) {
    Box(
        Modifier
            .background(Color.Transparent)
            .size(caseSize.dp),
    ) {
        if (caseColor != '.') {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                shape = RectangleShape,
                elevation = 7.dp,
            ) {
                Box(
                    Modifier
                        .gradientBackground(mapCaseColorList(caseColor), 135f)
                        .fillMaxSize()
                ) {
                    content.invoke()
                }
            }
        }
    }
}