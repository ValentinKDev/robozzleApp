package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun RanksLevelScreen(levelId: Int, levelName: String, vm: RanksLevelScreenViewModel = viewModel()) {
//    vm.load(levelId, LocalContext.current)
    val list by remember(vm) {vm.rankingList}.collectAsState(initial = emptyList())

    infoLog("list", "size ${list.size}")
    if (list.isEmpty()) vm.load(levelId)


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
                        .background(Color.Gray)
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