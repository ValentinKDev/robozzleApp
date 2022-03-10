package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.utils.Extensions.Is
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.EmptySquare
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@Composable
fun DisplayFunctionsPart(vm: GameDataViewModel) {
    val draggedStart : Boolean by vm.dragAndDrop.dragStart.collectAsState()
//    val levelFunctions = vm.instructionsRows.value
    val levelFunctions by vm.instructionsRows.collectAsState()

    val functions =
        if ( draggedStart )
            vm.dragAndDrop.elements.onHoldItem(levelFunctions.toMutableList())
        else
            levelFunctions

    Column(Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            vm.dragAndDrop.elements.setDraggableParentOffset(it)
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, _ ->
                    infoLog("onDrag", "position ${change.position}")
                    vm.dragAndDrop.onDrag(pointerInputChange = change, list = functions)
                    infoLog("vm.dragstrt", "${vm.dragAndDrop.dragStart.value}")
                },
                onDragStart = { _offset ->
                    infoLog("onDragStart", "started")
                    vm.dragAndDrop.onDragStart(_offset, functions)
                    infoLog("vm.dragstrt", "${vm.dragAndDrop.dragStart.value}")
                },
                onDragEnd = {
                    vm.dragAndDrop.onDragEnd(vm)
                    errorLog("onDragEnd", "end")
                },
                onDragCancel = {
                    vm.dragAndDrop.onDragCancel()
                    errorLog("onDragCanceled", "cancel")
                }
            )
        }
    ) {
        CenterComposable {
            functions.forEachIndexed { functionNumber, function ->
                DisplayFunctionRow(functionNumber, function, vm)
            }
        }
    }
}

@Composable
fun DisplayFunctionRow(functionNumber: Int, function: FunctionInstructions, vm: GameDataViewModel) {
    val currentAction: Int by vm.animationLogicVM.data.actionToRead.collectAsState()

    val playerAnimationState: PlayerAnimationState by vm.animationLogicVM.data.playerAnimationState.collectAsState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            vm.dragAndDrop.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = vm.data.text.functionText(functionNumber),
            color = vm.data.colors.functionText
        )
        Row( Modifier
            .height((vm.data.layout.secondPart.size.functionCase + (2 * vm.data.layout.secondPart.size.functionCasePadding)).dp)
            .width(((function.instructions.length * (vm.data.layout.secondPart.size.functionCase + vm.data.layout.secondPart.size.functionCasePadding)) + vm.data.layout.secondPart.size.functionCasePadding).dp)
            .background(vm.data.colors.functionBorder)
            ,
        ) {
            CenterComposableVertically {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    function.instructions.forEachIndexed { _index, c ->
                        val caseColor = function.colors[_index]
                        Box(Modifier
                            .size(vm.data.layout.secondPart.size.functionCase.dp)
                            .onGloballyPositioned {
                                vm.dragAndDrop.elements.addDroppableCase(
                                    rowIndex = functionNumber,
                                    columnIndex = _index,
                                    it
                                )
                            }
                            .clickable {
                                if (playerAnimationState.runningInBackground() Is true
                                    && vm.dragAndDrop.dragStart.value Is false
                                ) {
                                    vm.ChangeInstructionMenuState()
                                    vm.setSelectedFunctionCase(functionNumber, _index)
                                }
                            }
                        ) {
                            val instructionChar = function.instructions[_index]
                            Box( Modifier .fillMaxSize()
                            ) {
                                FunctionCase(color = caseColor, instructionChar = instructionChar, vm = vm)
                                if ( vm.breadcrumb.currentInstructionList.isNotEmpty()
                                    && ( (currentAction == 0 && functionNumber == 0 && _index == 0) || vm.breadcrumb.currentInstructionList[currentAction].Match( Position(functionNumber, _index) ) )
                                    && playerAnimationState.runningInBackground()
                                ) {
                                    EmptySquare(
                                        size =  vm.data.layout.secondPart.size.functionCase,
                                        ratio = vm.data.layout.secondPart.ratios.selectionCaseHalo,
                                        color = vm.data.colors.functionCaseSelection
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
