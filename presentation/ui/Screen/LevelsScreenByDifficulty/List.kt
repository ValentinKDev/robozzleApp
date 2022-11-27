package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
fun LevelsScreenByDifficultyList(
    navigator: Navigator,
    vm: LevelsScreenByDifficultyViewModel,
) {
    val levelsList: List<LevelOverView> by remember {vm.levelOverviewList}.collectAsState()
    val scrollState = rememberSaveable(saver = LazyListState.Saver) { vm.lazyListVM.getState() }

    LaunchedEffect(key1 = true) {
        Log.d("LevelsScreenByDifficultyList", "Start levelsList size ${levelsList.size}")
        Log.d("LevelsScreenByDifficultyList", "column state index start ${scrollState.firstVisibleItemIndex}")
    }

    DisposableEffect(true) {
        onDispose {
            errorLog("LevelsScreenByDifficultyList", "saving list index ${scrollState.firstVisibleItemIndex}")
            errorLog("LevelsScreenByDifficultyList", "saving list offet ${scrollState.firstVisibleItemScrollOffset}")
            vm.lazyListVM.saveState(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset)
        }
    }

    Column() {
        Spacer(modifier = Modifier.height(vm.ui.header.sizes.heightDp))
        Box {
            if (levelsList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                    ,
                    state = scrollState
                ) {
                    itemsIndexed(
                        items = levelsList,
                    ) { index, levelOverView ->
                        vm.lazyListVM.state.value.layoutInfo.totalItemsCount
                        vm.lazyListVM.state.value.interactionSource.interactions
                        DisplayLevelOverView(levelOverView, vm, navigator)
                    }
                }
            } else { Text(text = "Can't access the server and no level in the phone internal storage") }
            ListProgressBar(vm, scrollState)
        }
    }

}

