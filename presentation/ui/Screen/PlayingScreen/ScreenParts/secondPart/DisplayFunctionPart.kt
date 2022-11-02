package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import com.mobilegame.robozzle.utils.Extensions.Is
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.elements.WhiteSquare
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import com.mobilegame.robozzle.utils.Extensions.getSafe

@Composable
fun DisplayFunctionsPart(vm: GameDataViewModel) {
    val draggedStart : Boolean by vm.dragAndDropCase.dragStart.collectAsState()
    val levelFunctions = vm.instructionsRows
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val functions =
        if ( draggedStart )
            vm.dragAndDropCase.elements.onHoldItem(levelFunctions)
        else
            levelFunctions

    Column(Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            vm.dragAndDropCase.elements.setDraggableParentOffset(it)
        }
        .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        infoLog("onDrag", "position ${change.position}")
                        vm.dragAndDropCase.onDrag(
                            pointerInputChange = change,
                            list = vm.instructionsRows
                        )
                    },
                    onDragStart = { _offset ->
                        errorLog("TEST", "${vm.animData.playerAnimationState.value.key}")
                        infoLog("onDragStart", "started")
                        if (vm.isDragAndDropAvailable()) {
                            vm.dragAndDropCase.onDragStart(_offset, levelFunctions)
                        }
                        infoLog("vm.dragstrt", "${vm.dragAndDropCase.dragStart.value}")
                    },
                    onDragEnd = {
                        vm.dragAndDropCase.onDragEnd(vm)
                        errorLog("onDragEnd", "end")
                    },
                    onDragCancel = {
                        vm.dragAndDropCase.onDragCancel()
                        errorLog("onDragCanceled", "cancel")
                    }
                )
        }
    ) {
        functions.forEachIndexed { functionNumber, function ->
            VerticalSpace(heightDp = vm.data.layout.secondPart.size.functionRowPaddingHeightDp)
            Box{
                DisplayFunctionRow(functionNumber, function, vm, displayInstructionMenu)
                DisplayCurrentInstructionHighlighted(functionNumber, function, vm)
            }
        }
        VerticalSpace(heightDp = vm.data.layout.secondPart.size.functionRowPaddingHeightDp)
    }
}


@Composable
fun DisplayFunctionRow(functionNumber: Int, function: FunctionInstructions, vm: GameDataViewModel, displayInstructionMenu: Boolean) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = vm.data.text.functionText(functionNumber),
            color = vm.data.colors.functionText
        )
        Column {
            val doubleRow: Boolean = function.instructions.length == 10
            val listInstructions1 = if (doubleRow) function.instructions.substring(0..4) else function.instructions
            Column(
                Modifier
                    .height(vm.data.layout.secondPart.size.functionRowHeightListDp[functionNumber])
                    .width(vm.data.layout.secondPart.size.functionRowWidthListDp[functionNumber])
                    .background(vm.data.colors.functionBorder)
                ,
            ) {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listInstructions1.forEachIndexed { _index, _ ->
                        val caseColor = function.colors[_index]
                        Box(Modifier
                            .size(vm.data.layout.secondPart.size.functionCaseDp)
                            .onGloballyPositioned {
                                vm.dragAndDropCase.elements.addDroppableCase(
                                    rowIndex = functionNumber,
                                    columnIndex = _index,
                                    it
                                )
                            }
                            .clickable {
                                if (vm.dragAndDropCase.dragStart.value Is false
                                    && vm.isInstructionMenuAvailable()
                                ) {
                                    vm.ChangeInstructionMenuState()
                                    vm.setSelectedFunctionCase(functionNumber, _index)
                                }
                            }
                        ) {
                            val instructionChar = function.instructions[_index]
                            Box( Modifier .fillMaxSize()
                            ) {
                                FunctionCase(color = caseColor, instructionChar = instructionChar, vm = vm, filter = displayInstructionMenu)
                            }
                        }
                    }
                }
                if (doubleRow) {
                    Row( Modifier.fillMaxWidth()
                        ,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                    function.instructions.substring(5..9).forEachIndexed { _index, _ ->
                        val index = _index + 5
                            val caseColor = function.colors[index]
                            Box(Modifier
                                .size(vm.data.layout.secondPart.size.functionCaseDp)
                                .onGloballyPositioned {
                                    vm.dragAndDropCase.elements.addDroppableCase(
                                        rowIndex = functionNumber,
                                        columnIndex = index,
                                        it
                                    )
                                }
                                .clickable {
                                    if (vm.dragAndDropCase.dragStart.value Is false
                                        && vm.isInstructionMenuAvailable()
                                    ) {
                                        vm.ChangeInstructionMenuState()
                                        vm.setSelectedFunctionCase(functionNumber, index)
                                    }
                                }
                            ) {
                                val instructionChar = function.instructions[index]
                                Box( Modifier .fillMaxSize()
                                ) {
                                    FunctionCase(color = caseColor, instructionChar = instructionChar, vm = vm, filter = displayInstructionMenu)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DisplayCurrentInstructionHighlighted(functionNumber: Int, function: FunctionInstructions, vm: GameDataViewModel) {
    val currentAction: Int by vm.animData.actionToRead.collectAsState()

    val playerAnimationState: PlayerAnimationState by vm.animData.playerAnimationState.collectAsState()

    val doubleRow: Boolean = function.instructions.length == 10
    val listInstructions1 = if (doubleRow) function.instructions.substring(0..4) else function.instructions
    var visibleElement by remember{ mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = vm.data.text.functionText(functionNumber),
            color = vm.data.colors.functionText
        )
        Column {
            Column(
                Modifier
                    .height(vm.data.layout.secondPart.size.functionRowHeightListDp[functionNumber])
                    .width(vm.data.layout.secondPart.size.functionRowWidthListDp[functionNumber])
            ) {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listInstructions1.forEachIndexed { _index, _ ->
                        Box( Modifier.size(vm.data.layout.secondPart.size.functionCaseDp)
                        ) {
                            var display = false
                            if ( vm.breadcrumb.currentInstructionList.isNotEmpty()
                                && ( (currentAction == 0 && functionNumber == 0 && _index == 0) ||
                                                vm.breadcrumb.currentInstructionList.getSafe(currentAction).Match(Position(functionNumber, _index)) )
                                && playerAnimationState.runningInBackground() )
                            {
//                                visibleElement = true
                                WhiteSquare(
                                    sizeDp =  vm.data.layout.secondPart.size.functionCaseDp,
                                    stroke = vm.data.layout.secondPart.size.selectionCaseHaloStroke,
                                    vm = vm,
//                                    enableAnimation = true
                                )
                            }
//                            AnimatedVisibility(
//                                visible = visibleElement,
//                                enter = fadeIn(),
//                                exit = fadeOut()
//                            ) {
//                            }
                        }
                    }
                }
                if (doubleRow) {
                    Row( Modifier.fillMaxWidth()
                        ,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        function.instructions.substring(5..9).forEachIndexed { _index, _ ->
                            val index = _index + 5
                            Box(Modifier
                                .size(vm.data.layout.secondPart.size.functionCaseDp)
                            ) {
                                if ( vm.breadcrumb.currentInstructionList.isNotEmpty()
                                    && ( (currentAction == 0 && functionNumber == 0 && _index == 0) || vm.breadcrumb.currentInstructionList.getSafe(currentAction).Match(Position(functionNumber, _index)))
                                    && playerAnimationState.runningInBackground() )
                                {
                                    WhiteSquare(
                                        sizeDp =  vm.data.layout.secondPart.size.functionCaseDp,
                                        stroke = vm.data.layout.secondPart.size.selectionCaseHaloStroke,
                                        vm = vm
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
