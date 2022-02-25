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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.Extensions.sizeBy
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.window.UserInfosWindowInfos
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.UserInfosButton
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun UserInfoScreen(navigator: Navigator, screenVM: UserInfosScreenViewModel = viewModel(), w: UserInfosWindowInfos = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")
    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    Column() {
        Column( modifier = Modifier
            .fillMaxWidth()
            .weight(w.firstPartScreenWeight)
        ) {
            Row( modifier = Modifier
                .fillMaxHeight()
            ) {
                val size = w.userInfosButtonSize(UserInfosButton.LogOut, ctxt, dens)
                Box() {
                    Button( modifier = Modifier
                        .sizeBy(size)
                        .align(Alignment.CenterEnd)
                        .padding(end = size.width.dp)
                        ,
                        onClick = {
                            screenVM.logingOut()
                            NavViewModel(navigator).navigateTo(Screens.MainMenu)
                        }
                    ) {
                        Text(text = "Log out")
                    }
                    CenterComposable {
                        Row() {
                            Text(text = "${screenVM.name}", color = whiteDark4)
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(w.secondPartScreenWeight)
            ,
        ) {
            LazyColumn {
                //todo : UI 2 columns
                itemsIndexed(screenVM.levelWinList) { _, _levelWin ->
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

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator, levelMap: List<String>) {
    Box(
        modifier = Modifier
            .background(Color.Yellow)
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
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                }
        ) {
            Box(Modifier.weight(1.0f)) {
//                DisplayLevelImage()
                MapView(widthInt = 100, map = levelMap)
            }
            Box(Modifier.weight(1.0f)) {
                Column() {
                    Text("level ${levelWin.levelId}")
                    Text("${levelWin.points}")
                }
            }
        }
    }
    }
}