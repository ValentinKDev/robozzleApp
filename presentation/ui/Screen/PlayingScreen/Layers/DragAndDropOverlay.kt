package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.res.yellow0
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.EmptySquare
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.goingTopTiming
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.FunctionCase
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DragAndDropOverlay(vm: GameDataViewModel) {
    val dragRepresentationOffset: Offset by vm.dragAndDrop.dragRepresentationOffset.collectAsState()
    val itemUnderVisible: Boolean by vm.dragAndDrop.elements.itemUnderVisible.collectAsState()
    val visibleItermUnder: Boolean by remember(vm) {vm.dragAndDrop.elements.itemUnderVisible}.collectAsState()
    val dragStart: Boolean by vm.dragAndDrop.dragStart.collectAsState()
    val visibleDragDropOverLay by remember(vm) {vm.dragAndDrop.dragStart}.collectAsState(false)

    val transition = updateTransition(targetState = visibleDragDropOverLay, label = "")
    val animSize by transition.animateDp(
        label = "",
        transitionSpec = {
            when (dragStart) {
                true -> tween(200)
                false -> tween(200)
            }
        }
    ) { _dragStart ->
        when (_dragStart) {
            true -> vm.data.layout.secondPart.size.bigFunctionCase.dp
            false -> vm.data.layout.secondPart.size.functionCase.dp
        }
    }

    if (dragStart) {
        vm.dragAndDrop.elements.getColor() ?.let { _color ->
            vm.dragAndDrop.elements.getInstruction() ?.let { _instruction ->
                Box( Modifier.fillMaxSize() ) {
//                    if (itemUnderVisible) {
                    AnimatedVisibility(
                        visible = itemUnderVisible,
                        enter = fadeIn(0.7F, animationSpec = tween(100)),
                        exit = fadeOut(0.7F, animationSpec = tween(100)),
                    ) {
                        vm.dragAndDrop.elements.itemUnderTopLeftOffset?.let {
                            Box(Modifier
                                .offset {
                                    IntOffset(
                                        x = it.x.toInt(),
                                        y = it.y.toInt(),
                                    )
                                }
                            ) {
                                EmptySquare(
                                    size =  vm.data.layout.secondPart.size.functionCase,
                                    ratio = vm.data.layout.secondPart.ratios.selectionCaseHalo,
                                    color = vm.data.colors.functionCaseSelection
                                )
                            }
                        }
                    }

//                    Box(Modifier.size(vm.data.layout.secondPart.size.bigFunctionCase.dp)) {
//                        CenterComposable {
//
//                        }
//                    }
                    AnimatedVisibility(
                        visible = visibleDragDropOverLay,
                        enter = fadeIn(0.7F, animationSpec = tween(200)),
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
//                            Card(
//                                modifier = Modifier
//                                    .height(animSize)
//                                    .width(animSize)
//                                ,
//                                shape = RectangleShape
//                                ,
//                                elevation = 50.dp
//                            ) {
//                                Box( Modifier.gradientBackground(ColorsList(_color, vm.displayInstructionsMenu.value == true), 175f) ) {
//                                    if (_instruction != '.'){
//                                        CenterComposable {
//                                            InstructionsIconsFunction(_instruction, vm)
//                                        }
//                                    }
//                                }
//                            }
                            FunctionCase(color = _color, vm = vm, instructionChar = _instruction, bigger = true)
                        }
                        Box(Modifier
                            .offset {
                                IntOffset(
                                    x = vm.dragAndDrop.underPointerOffset.x.toInt(),
                                    y = vm.dragAndDrop.underPointerOffset.y.toInt(),
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

enum class ItemState {
    NotDragged, phase0, phase1, phase2, phase3, phase4, onHold
}
