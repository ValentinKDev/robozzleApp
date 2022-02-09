package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@Composable
fun UserInfoScreen(navigator: Navigator, screenVM: UserInfosScreenViewModel = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")
    val ctxt = LocalContext.current

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
            ,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3F)
                ,
            ) {
                Text(text = "UserInfoScreen")
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "user name ${screenVM.name}")
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                ,
            ) {
                Button(
                    modifier = Modifier
                        .size(20.dp)
                    ,
                    onClick = {
                        screenVM.logingOut()
                        NavViewModel(navigator).navigateTo(Screens.MainMenu)
                    }
                ) {
                    Text(text = "Log out")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9F)
            ,
        ) {
            LazyColumn {
                //todo : UI 2 columns
                itemsIndexed(screenVM.levelWinList) { _, levelWin ->
                    DisplayWinOverView(levelWin, navigator)
                }
            }
        }
    }
}

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator) {
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
                DisplayLevelImage()
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