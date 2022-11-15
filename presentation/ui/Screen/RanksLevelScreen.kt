package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
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
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.utils.Extensions.IsPair
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RanksLevelScreen(navigator: Navigator,levelId: Int, levelName: String, vm: RanksLevelScreenViewModel = viewModel()) {
    val visibleScreen by remember { vm.visibleScreen }.collectAsState()
    val list by remember(vm) {vm.rankingList}.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(true) {
        infoLog("RanksLevelScreent", "Start")
        vm.load(levelId)
        vm.setVisibleScreenTo(true)
    }

    BackHandler {
        LevelVM(context).getLevel(levelId)?.let {
            NavViewModel(navigator).navigateToScreenLevelByDiff(it.difficulty.toString())
        }
    }

    AnimatedVisibility(
        visible = visibleScreen,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                Text(text = "Level : ${levelName}")
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
}