package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logAnimMap
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.res.yellowDark2
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.infixStyle.contain
import gradientBackground

@Composable
fun MapViewInGame(
    vm: GameDataViewModel,
    widthInt: Int,
    playerInGame: PlayerInGame? = null,
    stars: List<Position> = emptyList()
) {
    //todo : put those calculs in a VM ?
    val map: List<String> by vm.animData.map.collectAsState()
    logAnimMap?.let {
        infoLog("stars", "$stars")
        infoLog("Map View In Game", "player pos ${playerInGame?.pos}")
        infoLog("action", "${vm.animData.getActionToRead()}")
    }

    val caseNumberWidth = map[0].length
    val caseNumberHeight = map.size

    val calulByWidth = if (caseNumberWidth > caseNumberHeight) true else false

    val padingRatio: Float = 1.0f / 20.0f

    val caseSize: Int =
        if (calulByWidth) (widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))).toInt()
        else (widthInt.toFloat() / (caseNumberHeight.toFloat() * (padingRatio + 1f))).toInt()
    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
    val mapWidthtDP: Dp = widthInt.dp
    val casePaddingDP: Dp = (caseSize / 20.0F).dp
    val playerIconSize: Int = (caseSize * vm.data.layout.firstPart.ratios.playerIcon).toInt()

    var casePosition = Position.Zero

    Box(
        Modifier
            .height(mapHeightDP)
            .width(mapWidthtDP)
    ) {
        Column( Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            map.forEachIndexed { _rowIndex, rowString ->
                Row {
                    rowString.forEachIndexed { _columnIndex, _color ->
                        casePosition = Position(_rowIndex, _columnIndex)
//                        DrawMapCase(caseSize = caseSize, caseColor = _color) {
                        Box(
                            Modifier
                                .background(Color.Transparent)
                                .size(caseSize.dp),
                        ) {
                            if (_color != '.') {
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(1.dp),
                                    shape = RectangleShape,
                                    elevation = 7.dp,
                                ) {
                                    Box(
                                        Modifier
                                            .gradientBackground(ColorsList(_color), 135f)
                                            .fillMaxSize()
                                    ) {
//                                        content.invoke()
                                        playerInGame?.let {
                                            if (playerInGame.pos == casePosition) {
                                                PlayerIcon(dir = playerInGame.direction.ToChar(), size = playerIconSize)
                                            }
                                            else if (stars contain casePosition) {
                                                if (stars contain casePosition) {
                                                    StarIcon()
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
                                        PlayerIcon(dir = playerInGame.direction.ToChar(), size = playerIconSize)
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
                        .gradientBackground(ColorsList(caseColor), 135f)
                        .fillMaxSize()
                ) {
                    content.invoke()
                }
            }
        }
    }
}

    @Composable
fun StarIcon() {
    CenterComposable {
        Box(
            Modifier
                .size(10.dp)
                .background(yellowDark2))
    }
}

@Composable
fun PlayerIcon(dir: Char, size: Int) {
    Box(modifier = Modifier.fillMaxSize() ) {
        Icon(
            imageVector = when (dir) {
                'r' -> Icons.Outlined.ArrowForward
                'l' -> Icons.Outlined.ArrowBack
                'u' -> Icons.Outlined.ArrowUpward
                'd' -> Icons.Outlined.ArrowDownward
                else -> Icons.Default.Home
            },
            contentDescription = "playerDir",
            modifier = Modifier
                .size(size.dp)
                .align(Alignment.Center)
        )
    }
}
