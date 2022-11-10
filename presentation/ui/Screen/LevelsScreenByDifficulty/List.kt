package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.content.ClipData
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.data.store.KeyProvider
import com.mobilegame.robozzle.domain.model.data.store.LazyListStateDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blue8
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blue9
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greendark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red8
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@Composable
fun LevelsScreenByDifficultyList(
    navigator: Navigator,
    vm: LevelsScreenByDifficultyViewModel,
    rankingIconVM: RankingIconViewModel = RankingIconViewModel().create(35)
) {
    val context = LocalContext.current
    val density = LocalDensity.current
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
        Spacer(modifier = Modifier.height( MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density).height.dp))
        if (levelsList.isNotEmpty()) {
            LazyColumn(
                state = scrollState
            ) {
                items(
                    items = levelsList,
                    key = { levelsOverView ->
                        levelsOverView.id
                    }
                ) { levelOverView ->
                    DisplayLevelOverView(levelOverView, vm, rankingIconVM, navigator)
                }
            }
        } else { Text(text = "Can't access the server and no level in the phone internal storage") }
    }
}


class LazyListStateViewModel(
    context: Context,
    id: Int
): ViewModel() {
    val dataStoreService = LazyListStateDataStoreViewModel(context, id)

    private val _state = MutableStateFlow(dataStoreService.getState())
    val state = _state.asStateFlow()

    fun getState(): LazyListState {
        return dataStoreService.getState()
    }
    fun saveState(index: Int, offset: Int) {
        dataStoreService.saveState(index, offset)
    }
}

///**
// * Static field, contains all scroll values
// */
//private val SaveMap = mutableMapOf<String, KeyParams>()
//
//private data class KeyParams(
//    val params: String = "",
//    val index: Int,
//    val scrollOffset: Int
//)
//
///**
// * Save scroll state on all time.
// * @param key value for comparing screen
// * @param params arguments for find different between equals screen
// * @param initialFirstVisibleItemIndex see [LazyListState.firstVisibleItemIndex]
// * @param initialFirstVisibleItemScrollOffset see [LazyListState.firstVisibleItemScrollOffset]
// */
//@Composable
//fun rememberForeverLazyListState(
//    key: String,
//    params: String = "",
//    initialFirstVisibleItemIndex: Int = 0,
//    initialFirstVisibleItemScrollOffset: Int = 0
//): LazyListState {
//    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
//        var savedValue = SaveMap[key]
//        if (savedValue?.params != params) savedValue = null
//        val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
//        val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
//        LazyListState(
//            savedIndex,
//            savedOffset
//        )
//    }
//    DisposableEffect(Unit) {
//        onDispose {
//            val lastIndex = scrollState.firstVisibleItemIndex
//            val lastOffset = scrollState.firstVisibleItemScrollOffset
//            SaveMap[key] = KeyParams(params, lastIndex, lastOffset)
//        }
//    }
//    return scrollState
//}

