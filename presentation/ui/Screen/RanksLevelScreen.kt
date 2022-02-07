package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.DisplayWinOverView
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun RanksLevelScreen(levelId: Int, levelName: String, vm: RanksLevelScreenViewModel = viewModel()) {
    vm.load(levelId, LocalContext.current)

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
        Row(
            modifier = Modifier
                .weight(10F)
                .fillMaxWidth()
        ) {
            ColumnPlyOverView(list = vm.firstColRankingList)
            ColumnPlyOverView(list = vm.secondColRankingList)
            ColumnPlyOverView(list = vm.thirdColRankingList)
        }
    }
}

@Composable
fun ColumnPlyOverView(list: List<PlayerWin>) {
    LazyColumn(
        modifier = Modifier
//            .weight(1F)
            .fillMaxHeight()
    ) {
        itemsIndexed(list) { index, element ->
            PlayerWinOverview(playerWin = element)
        }
    }
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