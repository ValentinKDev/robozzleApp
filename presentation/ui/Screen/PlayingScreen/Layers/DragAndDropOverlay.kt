package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.yellow0
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.FunctionCase
import com.mobilegame.robozzle.presentation.ui.elements.WhiteSquare

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DragAndDropOverlay(vm: GameDataViewModel) {
    val dragRepresentationOffset: Offset by vm.dragAndDropCase.dragRepresentationOffset.collectAsState()
//    val itemUnderVisible: Boolean by vm.dragAndDrop.elements.itemUnderVisible.collectAsState()
//    val visibleItermUnder: Boolean by remember(vm) {vm.dragAndDrop.elements.itemUnderVisible}.collectAsState()
    val itemUnderCornerOffset: Offset? by vm.dragAndDropCase.elements.itemUnderCorner.collectAsState()
    val dragStart: Boolean by vm.dragAndDropCase.dragStart.collectAsState()
    val visibleDragDropOverLay by remember(vm) {vm.dragAndDropCase.dragStart}.collectAsState(false)

    if (dragStart) {
        vm.dragAndDropCase.elements.getColor() ?.let { _color ->
            vm.dragAndDropCase.elements.getInstruction() ?.let { _instruction ->
                Box( Modifier.fillMaxSize() ) {
                    AnimatedVisibility(
//                        visible = itemUnderVisible,
                        visible = itemUnderCornerOffset?.let { true }?: false,
                        enter = fadeIn(0.7F, animationSpec = tween(100)),
                        exit = fadeOut(0.7F, animationSpec = tween(100)),
                    ) {
                        itemUnderCornerOffset?.let {
//                        vm.dragAndDrop.elements.itemUnderTopLeftOffset?.let {
                            Box(Modifier
                                .offset {
                                    IntOffset(
                                        x = it.x.toInt(),
                                        y = it.y.toInt(),
                                    )
                                }
                            ) {
                                WhiteSquare(
                                    sizeDp =  vm.data.layout.secondPart.size.functionCaseDp,
                                    stroke = vm.data.layout.secondPart.size.selectionCaseHaloStroke,
                                    vm = vm
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = visibleDragDropOverLay,
//                        enter = scaleIn
                        enter = fadeIn(0.5F, animationSpec = tween(200)),
                        exit = fadeOut(0.7F, animationSpec = tween(200)),
                    ) {
                        Box(Modifier
                            .offset {
                                IntOffset(
                                    x = dragRepresentationOffset.x.toInt(),
                                    y = dragRepresentationOffset.y.toInt(),
                                )
                            }
                        ) {
                            FunctionCase(color = _color, vm = vm, instructionChar = _instruction, bigger = true)
                        }
                        Box(Modifier
                            .offset {
                                IntOffset(
                                    x = vm.dragAndDropCase.underPointerOffset.x.toInt(),
                                    y = vm.dragAndDropCase.underPointerOffset.y.toInt(),
                                )
                            }
                            .size(5.dp)
                            .backColor(yellow0)
                        ) { }
                    }
                }
            }
        }
    }
}