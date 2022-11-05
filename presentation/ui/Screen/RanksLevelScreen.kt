package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.utils.Extensions.IsPair
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun RanksLevelScreen(levelId: Int, levelName: String, vm: RanksLevelScreenViewModel = viewModel()) {
//    vm.load(levelId, LocalContext.current)
    val list by remember(vm) {vm.rankingList}.collectAsState(initial = emptyList())

    LaunchedEffect(true) {
        infoLog("RanksLevelScreent", "Start")
        vm.load(levelId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Text(text = "Level ${levelId} : ${levelName}")
        }
        LazyColumn(
            modifier = Modifier
                .weight(10F)
                .fillMaxHeight()
        ) {
            itemsIndexed(list) { index, _rowElement ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F)
                        .background(if (index.IsPair()) MyColor.grayDark4 else MyColor.grayDark5)
                    ,
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                    ) {
                        Text("player ${_rowElement.playerName}")
                        Text("instruction ${_rowElement.winDetails.instructionsNumber}")
                        Text("points ${_rowElement.points}")
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnPlyOverView(list: List<PlayerWin>) {
    infoLog("check", "${list}")
}

@Composable
fun PlayerWinOverview(playerWin: PlayerWin) {
    Column(
        modifier = Modifier
    ) {
        Text("player ${playerWin.playerName}")
        Text("instruction ${playerWin.winDetails.instructionsNumber}")
        Text("points ${playerWin.points}")
    }
}