package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.elements.TrashIcone

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrashOverlay(vm: GameDataViewModel) {
    val dragStart: Boolean by vm.dragAndDropCase.dragStart.collectAsState()

    LaunchedEffect(true) {
        errorLog("heightTrash", "${vm.data.layout.trash.heightTrash}")
        errorLog("heightTrashDp", "${vm.data.layout.trash.heightTrashDp}")
    }

    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(4F))
        Column(
            Modifier
                .weight(6F)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1F))
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(5F)
            ) {
                Row {
                    AnimatedVisibility(
                        visible = dragStart,
                        enter = slideInHorizontally(initialOffsetX = {-150}) + fadeIn(),
                        exit = slideOutHorizontally(targetOffsetX = {-150}) + fadeOut()
                    ) {
                        TrashIcone(
                            alignment = Alignment.CenterStart,
                            trash = vm.data.layout.trash,
                            vm = vm
                        )
                    }
                }
                Row {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(vm.data.layout.trash.emptyWidthRatio))
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .weight(vm.data.layout.trash.widthRatioTrash)
                    ) {
                        AnimatedVisibility(
                            visible = dragStart,
                            enter = slideInHorizontally(initialOffsetX = { 150 }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { 150 }) + fadeOut()
                        ) {
                            TrashIcone(
                                alignment = Alignment.CenterEnd,
                                trash = vm.data.layout.trash,
                                vm = vm
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1F))
    }
}