package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenData
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@ExperimentalAnimationApi
@Composable
fun UserInfoScreenThirdPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    Row(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1F)
        ) {
            PaddingComposable(
                startPaddingRatio = 0.1F,
                endPaddingRatio = 0.025F
            ) {
                Column(
                    Modifier.onGloballyPositioned { vm.dimension.setWinOverViewDimensions(it) }
                ) {
                    LazyColumn( Modifier.fillMaxHeight()) {
                        itemsIndexed(vm.data.levelWinList1.value) { _, _levelWin ->
//                            AnimatedVisibility(
//                                visible = listVisible,
//                                enter = slideInVertically(),
//                                exit = slideOutVertically(animationSpec = tween(300)) + fadeOut(
//                                    animationSpec = tween(
//                                        300
//                                    )
//                                )
//                            ) {
                                DisplayWinOverView(
                                    _levelWin,
                                    navigator,
                                    vm.data.levelList.value.find { it.id == _levelWin.levelId }?.map
                                        ?: emptyList(),
                                    vm,
                                )
//                            }
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

