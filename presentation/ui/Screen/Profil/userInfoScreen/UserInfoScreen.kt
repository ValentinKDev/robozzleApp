package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.window.UserInfoScreenData
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable


@Composable
fun UserInfoScreen(navigator: Navigator, screenVM: UserInfosScreenViewModel = viewModel(), w: UserInfoScreenData = viewModel(), rankingIconVM: RankingIconViewModel = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")
    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    Column() {
        Box( Modifier .fillMaxWidth() .weight(w.firstPartScreenWeight)
        ) {
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .weight(w.secondPartScreenWeight)
        ) {
            Column(Modifier.weight(1F)) {
                PaddingComposable(
                    startPaddingRatio = 0.1F,
                    endPaddingRatio = 0.025F
                ) {
                    LazyColumn( Modifier
                        .fillMaxHeight()
                    ) {
                        itemsIndexed(screenVM.levelWinList1) { _, _levelWin ->
                            DisplayWinOverView(
                                _levelWin,
                                navigator,
                                screenVM.levelList.find { it.id == _levelWin.levelId }?.map ?: emptyList(),
                                screenVM,
                                rankingIconVM
                            )
                        }
                    }
                }
            }
            Column(Modifier.weight(1F)) {
                PaddingComposable(
                    startPaddingRatio = 0.025F,
                    endPaddingRatio = 0.1F
                ) {
                    LazyColumn( Modifier.fillMaxHeight()
                    ) {
                        itemsIndexed(screenVM.levelWinList2) { _, _levelWin ->
                            DisplayWinOverView(
                                _levelWin,
                                navigator,
                                screenVM.levelList.find { it.id == _levelWin.levelId }?.map ?: emptyList(),
                                screenVM,
                                rankingIconVM
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator, levelMap: List<String>, screenVM: UserInfosScreenViewModel, rankingIconVM: RankingIconViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> rankingIconVM.rankingIconIsPressed()
        false -> rankingIconVM.rankingIconIsReleased(navigator, levelWin.levelId)
    }
    Box(
        modifier = Modifier
            .clickable {
                infoLog("click on card", "start")
                NavViewModel(navigator).navigateTo(Screens.RanksLevel, levelWin.levelId.toString())
            }
    ) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 6.dp,
                end = 6.dp,
            )
            .fillMaxWidth()
            .height(150.dp)
        ,
        elevation = 8.dp,
        backgroundColor = grayDark3
    ) {
        Column( modifier = Modifier.fillMaxSize()
        ) {
            Box( modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() )
            {
                Text("level ${levelWin.levelId}", color = whiteDark4, modifier = Modifier.align(Alignment.Center))
            }
            Box(Modifier) {
                Row() {
                    Box(
                        Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()) {
                        MapView(widthInt = 50, map = levelMap)
                    }
                }
            }
            Box(Modifier.fillMaxWidth()) {
                Column() {
                    Box(Modifier.fillMaxWidth()) {
                        Text("level ${levelWin.levelName}", color = whiteDark4, modifier = Modifier.align(Alignment.Center))
                    }
                    Box(Modifier.fillMaxWidth()) {
                        Text( "instructions ${levelWin.winDetails.instructionsNumber}", color = whiteDark4, modifier = Modifier.align(Alignment.Center))
                    }
                    Box( modifier = Modifier
                        .wrapContentSize()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(color = Color.Transparent)
                        ) {
                            infoLog("clickable", "ranking icon")
                        }
                    ) {
                        RankingIconBouncing(sizeAtt = 35, rankingIconVM = rankingIconVM, isPressed = isPressed)
                    }
                }
            }
        }
    }
    }
}