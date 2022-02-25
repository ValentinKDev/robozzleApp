package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.Extensions.sizeBy
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.window.UserInfosWindowInfos
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.UserInfosButton
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable


@Composable
fun UserInfoScreen(navigator: Navigator, screenVM: UserInfosScreenViewModel = viewModel(), w: UserInfosWindowInfos = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")
    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    Column() {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(w.firstPartScreenWeight)
        ) {
            PaddingComposable(
                topPaddingRatio = 1f/3f,
                bottomPaddingRatio = 1f/3f,
                startPaddingRatio = 0.8f,
                endPaddingRatio = 0.1f,
            ) {
                Button(
                    onClick = {
                        screenVM.logingOut()
                        NavViewModel(navigator).navigateTo(Screens.MainMenu)
                    }
                ) { Text(text = "Log out") }
            }
            PaddingComposable(
                topPaddingRatio = 0.1f,
                bottomPaddingRatio = 0.1f,
                startPaddingRatio = 0.3f,
                endPaddingRatio = 0.3f,
//                enableColor = true
            ) {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = w.cardNameColor,
                    elevation = w.cardNameElevation,
                    modifier = Modifier
                    ,
                    content = { CenterComposable { Text(text = "${screenVM.name}", color = whiteDark4) } }
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .weight(w.secondPartScreenWeight)
        ) {
            Column(Modifier.weight(1F)) {
                PaddingComposable(
                    horizontalPadding = 0.25f,
                ) {
                    LazyColumn( Modifier
                        .fillMaxHeight()
                    ) {
                        itemsIndexed(screenVM.levelWinList1) { _, _levelWin ->
                            DisplayWinOverView(
                                _levelWin,
                                navigator,
                                screenVM.levelList.find { it.id == _levelWin.levelId }?.map ?: emptyList()
                            )
                        }
                    }
                }
            }
            Column(Modifier.weight(1F)) {
                PaddingComposable(
                    horizontalPadding = 0.25f,
                ) {
                    LazyColumn( Modifier.fillMaxHeight()
                    ) {
                        itemsIndexed(screenVM.levelWinList2) { _, _levelWin ->
                            DisplayWinOverView(
                                _levelWin,
                                navigator,
                                screenVM.levelList.find { it.id == _levelWin.levelId }?.map ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator, levelMap: List<String>) {
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
            .height(100.dp)
        ,
        elevation = 8.dp,
        backgroundColor = grayDark3
    ) {
        Column( modifier = Modifier.fillMaxSize()
        ) {
            Text("level ${levelWin.levelId}", color = whiteDark4)
            Box(Modifier) {
                MapView(widthInt = 100, map = levelMap)
            }
            Box(Modifier) {
                Column() {
                    Text("${levelWin.points}", color = whiteDark4)
                }
            }
            BoxWithConstraints() {
                
            }
        }
    }
    }
}