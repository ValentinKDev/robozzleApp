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
//    widthInt: Int,
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
//    val caseNumberWidth = map[0].length
//    val caseNumberHeight = map.size

//    val calulByWidth = if (caseNumberWidth > caseNumberHeight) true else false

//    val widthInt = vm.data.layout.firstPart.size.mapWidthDp
//    val padingRatio: Float = 1.0f / 20.0f

//    val caseSize: Int =
//        if (calulByWidth) (widthInt.toFloat() / (caseNumberWidth.toFloat() * (padingRatio + 1f))).toInt()
//        else (widthInt.toFloat() / (caseNumberHeight.toFloat() * (padingRatio + 1f))).toInt()
//    val mapHeightDP: Dp = (caseSize * caseNumberHeight).dp
//    val mapWidthtDP: Dp = widthInt.dp
//    val casePaddingDP: Dp = (caseSize / 20.0F).dp
//    val playerIconSize: Int = (caseSize * vm.data.layout.firstPart.ratios.playerBox).toInt()

//    infoLog("mapViewinGame", "in")
    var casePosition = Position.Zero

//    Center
    Box(
        Modifier
            .height(vm.data.layout.firstPart.size.mapHeightDp)
            .width(vm.data.layout.firstPart.size.mapWidthDp)
//            .background(green9)
//            .height(mapHeightDP)
//            .width(mapWidthtDP)
    ) {
//        Row {
//            for (i in 0..9) {
//                Box(Modifier.height(10.dp).width(vm.data.layout.firstPart.size.mapCaseDp).background( yellow1) ) { }
//                Spacer(Modifier.size(vm.data.layout.firstPart.size.mapCasePaddingDp))
//            }
//        }
//        Box(Modifier.height(10.dp).width(vm.data.layout.firstPart.size.mapCase.dp).background( yellow1) ) { }
//        Box(Modifier.height(20.dp).width(vm.data.layout.firstPart.size.mapCaseDp).background( yellow1) ) { }
//        Box(Modifier.height(10.dp).width(vm.data.layout.firstPart.size.mapWidthDp).background( yellow1) ) { }
//        Box(Modifier.width(10.dp).height(vm.data.layout.firstPart.size.mapHeightDp).background( yellow1) ) { }
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
//                                .size(caseSize.dp),
                                .size(vm.data.layout.firstPart.size.mapCaseDp),
//                                .size(vm.data.layout.firstPart.size.mapCase.dp),
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
//                                        PlayerIcon(dir = playerInGame.direction.ToChar(), size = playerIconSize)
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

//@Composable
//fun PlayerIcon(dir: Char, size: Int) {
//    Box(modifier = Modifier.fillMaxSize() ) {
//        Icon(
//            imageVector = when (dir) {
//                'r' -> Icons.Outlined.ArrowForward
//                'l' -> Icons.Outlined.ArrowBack
//                'u' -> Icons.Outlined.ArrowUpward
//                'd' -> Icons.Outlined.ArrowDownward
//                else -> Icons.Default.Home
//            },
//            contentDescription = "playerDir",
//            modifier = Modifier
//                .size(size.dp)
//                .align(Alignment.Center)
//        )
//    }
//}
