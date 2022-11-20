package com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel

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
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.IsPair

@Composable
internal fun list(vm: RanksLevelScreenViewModel) {
    val list: List<PlayerWin>? by remember(vm) {vm.rankingList}.collectAsState(initial = emptyList())

    list?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            itemsIndexed(it) { index, _rowElement ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (index.IsPair())
                                vm.ui.list.colors.pair
                            else
                                vm.ui.list.colors.notPair
                        ),
                ) {
                    Column(
                        Modifier.padding(
                            start = vm.ui.list.padding.startDp,
                            top = vm.ui.list.padding.topDp,
                            bottom = vm.ui.list.padding.bottomDp
                        )
                    ) {
                        Text(
                            text = vm.ui.list.text.firstRow + _rowElement.playerName,
                            color = vm.ui.list.colors.text,
                        )
                        Text(
                            text = vm.ui.list.text.secondRow + _rowElement.winDetails.instructionsNumber,
                            color = vm.ui.list.colors.text,
                        )
                        Text(
                            text = vm.ui.list.text.thirdRow + _rowElement.points,
                            color = vm.ui.list.colors.text,
                        )
                    }
                }
            }
        }
    } ?: run {
        Row() {
            CenterComposable {
                Text(text = vm.ui.list.text.errorMessage)
            }
        }
    }
}