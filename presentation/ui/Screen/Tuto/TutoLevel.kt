package com.mobilegame.robozzle.presentation.ui.Screen.Tuto

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.isDelete
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.DisplayInstuctionMenu
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.InstructionCase
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers.TrashOverlay
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.thirdPart.GameButtons
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableHorizontally
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import com.mobilegame.robozzle.presentation.ui.utils.tutoOverlay
import com.mobilegame.robozzle.utils.Extensions.Is

@Composable
fun tutoLevel(vm: GameDataViewModel) {
    val tutoLayout by remember { vm.tutoLayout }.collectAsState()
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val visisbleMenu: Boolean by remember (vm) {vm.displayInstructionsMenu}.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(if (displayInstructionMenu) vm.data.colors.darkerBackground else Color.Transparent)
        ,
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Row(Modifier.weight(vm.data.layout.firstPart.ratios.height)){
                MapLayout(vm, enableClickSpeed = false, enableClickStopMark = false)
            }
            Row(modifier = Modifier
                .weight(vm.data.layout.secondPart.ratios.height)
            ) {
                SecondScreenPart(vm, enableMenu = false, enableDragAndDrop = false, enableActionRowDrag = false)
            }
            Row(modifier = Modifier
                .weight(vm.data.layout.thirdPart.ratios.height)
            ) {
                GameButtons(vm, enablePlayPause = false, enableReset = false, enableBack = false, enableNext = false)
            }
        }

        if (vm.data.layout.trash.displayTrash) TrashOverlay(vm)

        DragAndDropOverlay(vm)

        AnimatedVisibility(
            visible = visisbleMenu,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            DisplayInstuctionMenu(vm, false)
        }

        PlayingScreenPopupWin(vm = vm)

        tutoOverlay(
            info = tutoLayout,
            text = vm.tutoVM.tuto.description,
            visibleElements = true,
//            enableFilter = false
        )




        if (vm.isTutoLevel() && vm.tutoVM.tuto.matchStep(Tuto.ClickOnFirstInstructionCase)) {
            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                Row(
                    Modifier
                        .weight(vm.data.layout.firstPart.ratios.height)
                        .fillMaxWidth()){
//                    MapLayout(vm, enableClickSpeed = false, enableClickStopMark = false)
                }
                Row(modifier = Modifier
                    .weight(vm.data.layout.secondPart.ratios.height)
                    .fillMaxWidth()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Column( Modifier.weight(vm.data.layout.secondPart.ratios.actionRowHeight)
                        ) {
//                            ActionRow(vm)
                        }
                        Row( Modifier.weight(vm.data.layout.secondPart.ratios.functionsRowPartHeight )
                        ) {
                            val enableDrag = false
                            val draggedStart : Boolean by vm.dragAndDropCase.dragStart.collectAsState()
                            val levelFunctions = vm.instructionsRows

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
//                                        DisplayFunctionRow(functionNumber, function, vm, displayInstructionMenu, enableMenu)
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
//                                            .onGloballyPositioned {
//                                                vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
//                                            }
                                            ,
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
                                                val listInstructions1 =  function.instructions
                                                Column(
                                                    Modifier
                                                        .height(vm.data.layout.secondPart.sizes.functionRowHeightListDp[functionNumber])
                                                        .width(vm.data.layout.secondPart.sizes.functionRowWidthListDp[functionNumber])
                                                    ,
                                                ) {
                                                    Row( Modifier.fillMaxWidth()
                                                        ,
                                                        horizontalArrangement = Arrangement.SpaceEvenly
                                                    ) {
                                                        listInstructions1.forEachIndexed { _index, _ ->
                                                            val enableFirstInstructionClick = vm.isTutoClickOnFirstInstruction() && _index == 0 && functionNumber == 0

                                                            val clickable = if (enableFirstInstructionClick) Modifier.clickable {
                                                                if (vm.dragAndDropCase.dragStart.value Is false
                                                                    && vm.isInstructionMenuAvailable()
                                                                ) {
                                                                    vm.ChangeInstructionMenuState()
                                                                    vm.setSelectedFunctionCase(functionNumber, _index)
                                                                }
                                                                vm.tutoVM.nextStep()
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
                                                                if (
                                                                    (vm.isTutoClickOnFirstInstruction() && _index == 0 && functionNumber == 0)
                                                                    || (vm.isTutoClickOnSecondInstruction() && _index == 1 && functionNumber == 0)
                                                                ) {
                                                                    Box( Modifier .fillMaxSize()
                                                                    ) {
                                                                        FunctionCase(
                                                                            color = caseColor,
                                                                            instructionChar = instructionChar,
                                                                            vm = vm,
                                                                            filter = displayInstructionMenu && !vm.selectedCase.Match(
                                                                                Position(functionNumber, _index)
                                                                            ))
                                                                    }
                                                                    enlightItem(modifier = Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) , ui = vm.tutoLayout.value)
                                                                } else Box(Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) )
                                                                if ( (vm.isTutoClickOnSecondInstruction() && _index == 1 && functionNumber == 0) ) {
                                                                Box( Modifier .fillMaxSize()
                                                                ) {
                                                                    FunctionCase(
                                                                        color = caseColor,
                                                                        instructionChar = instructionChar,
                                                                        vm = vm,
                                                                        filter = displayInstructionMenu && !vm.selectedCase.Match(
                                                                            Position(functionNumber, _index)
                                                                        ))
                                                                }
                                                                enlightItem(modifier = Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) , ui = vm.tutoLayout.value)

                                                            }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
//                                        DisplayCurrentInstructionHighlighted(functionNumber, function, vm, displayInstructionMenu)
                                    }
                                }
                                VerticalSpace(heightDp = vm.data.layout.secondPart.sizes.functionRowPaddingHeightDp)
                            }
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(vm.data.layout.thirdPart.ratios.height)
                ) {
//                    GameButtons(vm, enablePlayPause = false, enableReset = false, enableBack = false, enableNext = false)
                }
            }
        }

        AnimatedVisibility(
            visible = visisbleMenu,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            val interactionExtMenu = remember { MutableInteractionSource() }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MyColor.black7)
                    .clickable(
                        interactionSource = interactionExtMenu,
                        indication = null
                    ) {
//                        vm.ChangeInstructionMenuState()
                    }
            )
            PaddingComposable(
                topPaddingRatio = vm.data.layout.menu.ratios.topPadding,
                bottomPaddingRatio = vm.data.layout.menu.ratios.bottomPadding,
            ) {
                CenterComposableHorizontally {
                    Box{
                        Column(
                            Modifier
                                .width(vm.data.layout.menu.sizes.windowWidth.dp)
                                .background(Color.Transparent)
                        ) {
                            vm.level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
                                Row( Modifier .background(Color.Transparent) ,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    instructions.instructions.toList().forEachIndexed { index, c ->
                                        if (
                                            (vm.isTutoClickOnFirstInstructionFromMenu() && c == 'u' && instructions.colors.first() == 'B')
                                        ) {
                                            Box( Modifier.clickable {
                                                    vm.ChangeInstructionMenuState()
                                                    vm.tutoVM.nextStep()
                                                }
                                            ) {
                                                InstructionCase(
                                                    vm,
                                                    FunctionInstruction(
                                                        c,
                                                        instructions.colors.first()
                                                    ),
                                                    Modifier.clickable {
                                                        vm.replaceInstruction(
                                                            vm.selectedCase,
                                                            if (FunctionInstruction(
                                                                    c,
                                                                    instructions.colors.first()
                                                                ).isDelete()
                                                            ) FunctionInstruction(
                                                                '.',
                                                                'g'
                                                            ) else FunctionInstruction(
                                                                c,
                                                                instructions.colors.first()
                                                            )
                                                        )
                                                        vm.ChangeInstructionMenuState()
                                                        vm.tutoVM.nextStep()
                                                    }
                                                )
                                            }
                                        }
                                        else
                                            Box(
                                                Modifier
                                                    .size(vm.data.layout.menu.sizes.case.dp)
                                                    .background(Color.Transparent) ) { }
                                    }
                                }
                            }
//                            CenterComposableHorizontally {
//                                InstructionCase(vm = vm, case = FunctionInstruction(instruction = 'x', color = 'g'))
//                            }
                        }
                    }
                }
            }
        }
    }
}