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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.header
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.list
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.map
import com.mobilegame.robozzle.utils.Extensions.IsPair

@Composable
fun RanksLevelScreen(navigator: Navigator,levelId: Int, levelName: String, vm: RanksLevelScreenViewModel = viewModel()) {
    val visibleScreen by remember { vm.visibleScreen }.collectAsState()
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
                Row( Modifier .weight(vm.ui.header.ratios.height)
                ) {
                    header(vm)
                }
                Row( Modifier .weight(vm.ui.map.ratios.height)
                ) {
                    map(vm)
                }
                Row(Modifier.weight(vm.ui.list.ratios.height)) {
                    list(vm)
                }
            }
    }
}