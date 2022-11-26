package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.utils.Extensions.Is
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.elements.WhiteSquare
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import com.mobilegame.robozzle.utils.Extensions.getSafe

@Composable
fun DisplayFunctionsPart(vm: GameDataViewModel, enableMenu: Boolean = true, enableDrag: Boolean = true, enableWhiteSquare: Boolean = true) {
    val draggedStart : Boolean by vm.dragAndDropCase.dragStart.collectAsState()
    val levelFunctions = vm.instructionsRows
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val functions =
        if ( draggedStart )
            vm.dragAndDropCase.elements.onHoldItem(levelFunctions)
        else
            levelFunctions

    val dragAndDrop = if (enableDrag) Modifier.dragAndDropCase(vm, levelFunctions) else Modifier
    Column(
        Modifier
            .then(dragAndDrop)
            .fillMaxSize()
            .onGloballyPositioned {
                vm.dragAndDropCase.elements.setDraggableParentOffset(it)
            }
    ) {
        functions.forEachIndexed { functionNumber, function ->
            VerticalSpace(heightDp = vm.data.layout.secondPart.sizes.functionRowPaddingHeightDp)
            Box{
                DisplayFunctionRow(functionNumber, function, vm, displayInstructionMenu, enableMenu)
                DisplayCurrentInstructionHighlighted(functionNumber, function, vm, displayInstructionMenu, enableWhiteSquare)
            }
        }
        VerticalSpace(heightDp = vm.data.layout.secondPart.sizes.functionRowPaddingHeightDp)
    }
}


@Composable
fun DisplayFunctionRow(functionNumber: Int, function: FunctionInstructions, vm: GameDataViewModel, displayInstructionMenu: Boolean, enableClick: Boolean) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Card(
            backgroundColor = Color.Transparent,
            elevation = 10.dp,
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = iconByInt(functionNumber),
                tint = if (displayInstructionMenu) vm.data.colors.functionTextDark else vm.data.colors.functionText,
                contentDescription = "function $functionNumber",
                modifier = Modifier
                    .size(vm.data.layout.secondPart.sizes.twoThirdFunctionCaseDp)
            )
        }
        Box(Modifier.size(5.dp)) { }
        Column {
            val doubleRow: Boolean = function.instructions.length == 10
            val listInstructions1 = if (doubleRow) function.instructions.substring(0..4) else function.instructions
            Column(
                Modifier
                    .height(vm.data.layout.secondPart.sizes.functionRowHeightListDp[functionNumber])
                    .width(vm.data.layout.secondPart.sizes.functionRowWidthListDp[functionNumber])
                    .background(vm.data.colors.functionBorder)
                ,
            ) {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listInstructions1.forEachIndexed { _index, _ ->
                        val clickable = if (enableClick) Modifier.clickable {
                            if (vm.dragAndDropCase.dragStart.value Is false
                                && vm.isInstructionMenuAvailable()
                            ) {
                                vm.ChangeInstructionMenuState()
                                vm.setSelectedFunctionCase(functionNumber, _index)
                            }
                        } else Modifier
                        val caseColor = function.colors[_index]
                        Box(Modifier
                            .size(vm.data.layout.secondPart.sizes.functionCaseDp)
                            .onGloballyPositioned {
                                vm.dragAndDropCase.elements.addDroppableCase(
                                    rowIndex = functionNumber,
                                    columnIndex = _index,
                                    it
                                )
                            }
                            .then(clickable)
                        ) {
                            val instructionChar = function.instructions[_index]
                            Box( Modifier .fillMaxSize()
                            ) {
                                FunctionCase(
                                    color = caseColor,
                                    instructionChar = instructionChar,
                                    vm = vm,
                                    filter = (displayInstructionMenu && !vm.selectedCase.Match(Position(functionNumber, _index))))
//                                            || !(vm.isTutoLevel() && vm.tutoVM.tuto.matchStep(Tuto.ClickOnFirstInstructionCase) && _index == 0 && functionNumber == 0)
                            }
//                            if (vm.isTutoLevel() && vm.tutoVM.tuto.matchStep(Tuto.ClickOnFirstInstructionCase) && _index == 0 && functionNumber == 0) {
//                                enlightItem(modifier = Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) , ui = vm.tutoLayout.value)
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
                        val clickable2 = if (enableClick) Modifier.clickable {
                            if (vm.dragAndDropCase.dragStart.value Is false
                                && vm.isInstructionMenuAvailable()
                            ) {
                                vm.ChangeInstructionMenuState()
                                vm.setSelectedFunctionCase(functionNumber, _index)
                            }
                        } else Modifier
                        val index = _index + 5
                        val caseColor = function.colors[index]
                        Box(Modifier
                            .size(vm.data.layout.secondPart.sizes.functionCaseDp)
                            .onGloballyPositioned {
                                vm.dragAndDropCase.elements.addDroppableCase(
                                    rowIndex = functionNumber,
                                    columnIndex = index,
                                    it
                                )
                            }
                            .then(clickable2)
                        ) {
                            val instructionChar = function.instructions[index]
                            Box( Modifier .fillMaxSize()
                            ) {
                                FunctionCase(
                                    color = caseColor,
                                    instructionChar = instructionChar,
                                    vm = vm,
                                    filter = displayInstructionMenu && !vm.selectedCase.Match(Position(functionNumber, index))
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

@Composable
fun DisplayCurrentInstructionHighlighted(
    functionNumber: Int,
    function: FunctionInstructions,
    vm: GameDataViewModel,
    displayInstructionMenu: Boolean,
    enableWhiteSquare: Boolean = true
) {
    val currentAction: Int by vm.animData.actionToRead.collectAsState()

    val playerAnimationState: PlayerAnimationState by vm.animData.playerAnimationState.collectAsState()

    val doubleRow: Boolean = function.instructions.length == 10
    val listInstructions1 = if (doubleRow) function.instructions.substring(0..4) else function.instructions

    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = iconByInt(functionNumber),
            tint = Color.Transparent,
            contentDescription = "function $functionNumber",
            modifier = Modifier.size(vm.data.layout.secondPart.sizes.twoThirdFunctionCaseDp)
        )
        Box(Modifier.size(5.dp)) { }
        Column {
            Column(
                Modifier
                    .height(vm.data.layout.secondPart.sizes.functionRowHeightListDp[functionNumber])
                    .width(vm.data.layout.secondPart.sizes.functionRowWidthListDp[functionNumber])
            ) {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listInstructions1.forEachIndexed { _index, _ ->
                        Box( Modifier.size(vm.data.layout.secondPart.sizes.functionCaseDp)
                        ) {
                            if ( ( ( vm.breadcrumb.currentInstructionList.isNotEmpty()
                                        && ( (currentAction == 0 && functionNumber == 0 && _index == 0) ||
                                        vm.breadcrumb.currentInstructionList.getSafe(currentAction).Match(Position(functionNumber, _index)) )
                                        && playerAnimationState.runningInBackground() )
                                        || (vm.selectedCase.Match(Position(line = functionNumber, column = _index))
                                        && vm.animData.getPlayerAnimationState().isTheBreadcrumbModifiable()
                                        && displayInstructionMenu )
                                        ) && enableWhiteSquare )
                            {
                                WhiteSquare(
                                    sizeDp =  vm.data.layout.secondPart.sizes.functionCaseDp,
                                    stroke = vm.data.layout.secondPart.sizes.selectionCaseHaloStroke,
                                    vm = vm,
                                )
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
                            Box(Modifier
                                .size(vm.data.layout.secondPart.sizes.functionCaseDp)
                            ) {
                                if (
                                    ( vm.breadcrumb.currentInstructionList.isNotEmpty()
                                            && ( (currentAction == 0 && functionNumber == 0 && _index == 0) || vm.breadcrumb.currentInstructionList.getSafe(currentAction).Match(Position(functionNumber, index)))
                                            && playerAnimationState.runningInBackground() )
                                    || (vm.selectedCase.Match(Position(line = functionNumber, column = index))
                                            && vm.animData.getPlayerAnimationState().isTheBreadcrumbModifiable()
                                            && displayInstructionMenu
                                            )
                                )
                                {
                                    WhiteSquare(
                                        sizeDp =  vm.data.layout.secondPart.sizes.functionCaseDp,
                                        stroke = vm.data.layout.secondPart.sizes.selectionCaseHaloStroke,
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
