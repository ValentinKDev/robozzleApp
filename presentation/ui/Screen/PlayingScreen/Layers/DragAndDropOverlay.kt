package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.res.yellow0
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.EmptySquare
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.FunctionCase
import com.mobilegame.robozzle.presentation.ui.utils.extensions.backColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DragAndDropOverlay(vm: GameDataViewModel) {
    val dragRepresentationOffset: Offset by vm.dragAndDrop.dragRepresentationOffset.collectAsState()
    val itemUnderVisible: Boolean by vm.dragAndDrop.elements.itemUnderVisible.collectAsState()
    val dragStart: Boolean by vm.dragAndDrop.dragStart.collectAsState()
    infoLog("dragStart", "$dragStart---------------------------------------------------------------------------------------")

    if (dragStart) {
        vm.dragAndDrop.elements.getColor() ?.let { _color ->
            vm.dragAndDrop.elements.getInstruction() ?.let { _instruction ->
                Box( Modifier.fillMaxSize() ) {
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
                    if (itemUnderVisible) {
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