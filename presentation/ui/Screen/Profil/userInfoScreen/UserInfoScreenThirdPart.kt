package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@ExperimentalAnimationApi
@Composable
fun UserInfoScreenThirdPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    val listElementVisible by remember(vm){ vm.logic.doubleListVisible}.collectAsState()
    val context = LocalContext.current

    Row(Modifier.fillMaxSize()) {
            Column( Modifier.weight(1F) ) {
                PaddingComposable(
                    startPaddingRatio = 0.1F,
                    endPaddingRatio = 0.025F
                ) {
                    Column( Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { vm.dimension.setWinOverViewDimensions(it, context) }
                    ) {
                        AnimatedVisibility(
                            visible = listElementVisible,
                            enter = expandVertically(),
                        ) {
                        LazyColumn( Modifier.fillMaxHeight()) {
                            itemsIndexed(vm.data.levelWinList1.value) { _index, _levelWin ->
                                Column() {
                                    DisplayWinOverView(
                                        _levelWin,
                                        navigator,
                                        vm.data.levelList.value.find { it.id == _levelWin.levelId }?.map
                                            ?: emptyList(),
                                        vm,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Column(Modifier.weight(1F)) {
            PaddingComposable(
                startPaddingRatio = 0.025F,
                endPaddingRatio = 0.1F
            ) {
                LazyColumn( Modifier.fillMaxHeight() ) {
                    itemsIndexed(vm.data.levelWinList2.value) { _, _levelWin ->
                        DisplayWinOverView(
                            _levelWin,
                            navigator,
                            vm.data.levelList.value.find { it.id == _levelWin.levelId }?.map ?: emptyList(),
                            vm,
                        )
                    }
                }
            }
        }
    }
}

