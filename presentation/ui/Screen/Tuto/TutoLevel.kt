package com.mobilegame.robozzle.presentation.ui.Screen.Tuto

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.runningInBackgroundIs
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
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
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.firstPart.DrawMapCase
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.*
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.thirdPart.GameButtons
import com.mobilegame.robozzle.presentation.ui.elements.PlayerIcon
import com.mobilegame.robozzle.presentation.ui.elements.WhiteSquare
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableHorizontally
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import com.mobilegame.robozzle.presentation.ui.utils.tutoOverlay
import com.mobilegame.robozzle.utils.Extensions.Is
import com.mobilegame.robozzle.utils.Extensions.toCaseColor

@Composable
fun tutoLevel(vm: GameDataViewModel) {
    val tutoLayout by remember { vm.tutoVM.tutoLayout }.collectAsState()
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val visisbleMenu: Boolean by remember (vm) {vm.displayInstructionsMenu}.collectAsState()

    val tuto by remember { vm.tutoVM.tuto }.collectAsState()
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
                SecondScreenPart(vm, enableMenu = false, enableDragAndDrop = false, enableActionRowDrag = false, enableWhiteSquare = !tuto.matchStep(Tuto.ClickDragAndDropThirdCase))
            }
            Row(modifier = Modifier
                .weight(vm.data.layout.thirdPart.ratios.height)
            ) {
                GameButtons(vm, enablePlayPause = false, enableReset = false, enableBack = false, enableNext = false)
            }
        }

        if (vm.data.layout.trash.displayTrash) TrashOverlay(vm)


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
//            text = vm.tutoVM.tuto.description,
//            text = vm.tutoVM.getTuto().description,
            text = tuto.description,
            visibleElements = true,
        )

//        if (vm.isTutoLevel() && vm.tutoVM.tuto.matchStep(Tuto.ClickOnFirstInstructionCase)) {
        if (vm.isTutoLevel()) {
            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                Row(
                    Modifier
                        .weight(vm.data.layout.firstPart.ratios.height)
                        .fillMaxWidth()){
                    /** Map Layout **/
//                    MapLayout(vm, enableClickSpeed = false, enableClickStopMark = false)
                    val map: List<String> by vm.animData.map.collectAsState()
                    var casePosition = Position.Error
                    val playerInGame: PlayerInGame by remember { vm.animData.playerAnimated }.collectAsState()
                    val stars: List<Position> by remember { vm.animData.animatedStarsMaped }.collectAsState()
                    val caseStop: List<Position> by remember { vm.animData.mapCaseSelection }.collectAsState()
                    val enableClickStopMark = false

                    CenterComposable {
                    Box(
                        Modifier
                            .height(vm.data.layout.firstPart.sizes.mapHeightDp)
                            .width(vm.data.layout.firstPart.sizes.mapWidthDp)
                    ) {
                        Column( Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            map.forEachIndexed { _rowIndex, rowString ->
                                Row {
                                    rowString.forEachIndexed { _columnIndex, _color ->
                                        casePosition = Position(_rowIndex, _columnIndex)
                                        Box(Modifier.size(vm.data.layout.firstPart.sizes.mapCaseDp)) {
                                            if ( playerInGame.pos.Match(Position(_rowIndex, _columnIndex))
                                                && ( vm.tutoVM.isTutoClickOnPlayButton()
                                                        || vm.tutoVM.isTutoClickOnResetButton()
                                                        || vm.tutoVM.isTutoDragActionBar()
                                                        )
                                            ) {
                                                CenterComposable {
                                                    PlayerIcon(direction = playerInGame.direction, data = vm.data.layout.firstPart, colors = vm.data.colors, _color.toCaseColor())
                                                }
                                            }
                                            if (
                                                (vm.tutoVM.isTutoClickOnStopMark() && _rowIndex == 4 && _columnIndex == 6)
                                            ) {
                                                DrawMapCase(
                                                    playerInGame = playerInGame,
                                                    stars = stars,
                                                    casePos = casePosition,
                                                    caseColor = _color,
                                                    filter = false,
                                                    vm = vm,
                                                )
                                                val position = Position( _rowIndex, _columnIndex )
                                                if (vm.animData.mapCaseSelection.value.contains(position)) {
                                                    vm.animData.deleteMapCaseStop(position)
                                                }
                                                    vm.animData.deleteMapCaseStop(position)
                                                enlightItem(modifier = Modifier
                                                    .fillMaxSize()
                                                    .clickable {
                                                        vm.animData.mapCaseSelectionHandler(
                                                            Position(
                                                                _rowIndex,
                                                                _columnIndex
                                                            )
                                                        )
                                                        vm.tutoVM.nextTuto()
                                                    })
                                            }

                                            if (caseStop.contains(casePosition) && enableClickStopMark) {
                                                Box() {
                                                    WhiteSquare(
                                                        sizeDp = vm.data.layout.firstPart.sizes.mapCaseDp,
                                                        stroke = vm.data.layout.firstPart.sizes.mapCaseStroke,
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
                }
                Row(modifier = Modifier
                    .weight(vm.data.layout.secondPart.ratios.height)
                    .fillMaxWidth()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Column( Modifier.weight(vm.data.layout.secondPart.ratios.actionRowHeight)
                        ) {
                            /** Action Row **/
//                            ActionRow(vm)
                            if (vm.tutoVM.isTutoDragActionBar()){
                                Box {
                                    ActionRow(vm = vm, enableActionRowDrag = false)
                                    enlightItem(modifier = Modifier.dragActionRow(vm, vm.tutoVM).fillMaxSize())
                                }
                            }
                        }
                        Row( Modifier.weight(vm.data.layout.secondPart.ratios.functionsRowPartHeight )
                        ) {
                            /** Function Row **/
                            val draggedStart : Boolean by vm.dragAndDropCase.dragStart.collectAsState()
                            val levelFunctions = vm.instructionsRows

                            val functions =
                                if ( draggedStart )
                                    vm.dragAndDropCase.elements.onHoldItem(levelFunctions)
                                else
                                    levelFunctions

                            val dragAndDrop = if (vm.tutoVM.isTutoDragAndDropInstruction()) Modifier.dragAndDropCaseTuto(
                                vm,
                                levelFunctions,
//                                vm.tutoVM
                            ) else Modifier
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
//                                        val dragAndDrop = if (vm.tutoVM.isTutoDragAndDropInstruction()) Modifier.dragAndDropCase(vm, levelFunctions, vm.tutoVM) else Modifier
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
//                                            .then(dragAndDrop)
//                                            .onGloballyPositioned {
//                                                vm.dragAndDropCase.elements.addDroppableRow(functionNumber, it)
//                                            }
                                            ,
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(vm.data.layout.secondPart.sizes.twoThirdFunctionCaseDp)
                                                    .background(Color.Transparent)
                                            ) { }
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
                                                            val enableFirstInstructionClick = (vm.tutoVM.isTutoClickOnFirstInstruction() && _index == 0 && functionNumber == 0)
                                                                    || (vm.tutoVM.isTutoClickOnSecondInstruction() && _index == 1 && functionNumber == 0)
                                                                    || (vm.tutoVM.isTutoClickOnThirdInstruction() && _index == 2 && functionNumber == 0)

                                                            val clickable = if (enableFirstInstructionClick) Modifier.clickable {
                                                                if (vm.dragAndDropCase.dragStart.value Is false
                                                                    && vm.isInstructionMenuAvailable()
                                                                ) {
                                                                    vm.ChangeInstructionMenuState()
                                                                    vm.setSelectedFunctionCase(functionNumber, _index)
                                                                }
                                                                vm.tutoVM.nextTuto()
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
                                                                    (vm.tutoVM.isTutoClickOnFirstInstruction() && _index == 0 && functionNumber == 0)
                                                                    || (vm.tutoVM.isTutoClickOnSecondInstruction() && _index == 1 && functionNumber == 0)
                                                                    || (vm.tutoVM.isTutoClickOnThirdInstruction() && _index == 2 && functionNumber == 0)
                                                                    || (vm.tutoVM.isTutoDragAndDropInstruction() && (_index == 2 && functionNumber == 0 || _index == 6 && functionNumber == 0))
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
                                                                    enlightItem(modifier = Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp))
                                                                } else Box(Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) )
//                                                                if ( (vm.isTutoClickOnSecondInstruction() && _index == 1 && functionNumber == 0) ) {
//                                                                Box( Modifier .fillMaxSize()
//                                                                ) {
//                                                                    FunctionCase(
//                                                                        color = caseColor,
//                                                                        instructionChar = instructionChar,
//                                                                        vm = vm,
//                                                                        filter = displayInstructionMenu && !vm.selectedCase.Match(
//                                                                            Position(functionNumber, _index)
//                                                                        ))
//                                                                }
//                                                                enlightItem(modifier = Modifier.size( vm.data.layout.secondPart.sizes.functionCaseDp ) , ui = vm.tutoLayout.value)
//                                                            }
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
                    /** Game buttons **/
//                    GameButtons(vm, enablePlayPause = false, enableReset = false, enableBack = false, enableNext = false)
                    val playerAnimationState: PlayerAnimationState by vm.animData.playerAnimationState.collectAsState()

                    CenterComposableVertically {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val enable = false

                            Box(Modifier.weight(1F))
                            if (vm.tutoVM.isTutoClickOnPlayButton()) {
                                Box {
                                    PlayPauseButton(vm = vm, false)
                                    enlightItem(
                                        Modifier
                                            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
                                            .clickable {
                                                if (vm.tutoVM.isTutoClickOnPlayButton()) {
                                                    vm.clickPlayPauseButtonHandler()
                                                    vm.tutoVM.nextTuto()
                                                }
                                            }
                                    )
                                }
                            }
                            else
                                Box(
                                    Modifier
                                        .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                        .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp) )

                            Box(Modifier.weight(1F))
                            if (vm.tutoVM.isTutoClickOnResetButton()) {
                                Box {
                                    ResetButton(vm, false)
                                    enlightItem(
                                        Modifier
                                            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
                                            .clickable {
                                                if (vm.tutoVM.isTutoClickOnResetButton()) {
                                                    vm.clickResetButtonHandler()
                                                    vm.tutoVM.nextTuto()
                                                }
                                            }
                                    )
                                }
                            }
                            else
                                Box(
                                    Modifier
                                        .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                        .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
                                )

                            Box(Modifier.weight(1F))
//                            if (vm.tutoVM.isTutoClickOnForwardButton())
//                                BackButton(vm, enable = playerAnimationState runningInBackgroundIs true && false)
//                            else
                                Box(
                                    Modifier
                                        .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                        .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp) )

                            Box(Modifier.weight(1F))
                            if (enable) {
                                Box {
                                    NextButton(vm, enable = playerAnimationState runningInBackgroundIs true && false)
                                    enlightItem(
                                        Modifier
                                            .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                            .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp)
                                            .clickable {
                                                if (vm.tutoVM.isTutoClickOnResetButton()) {
                                                    vm.clickResetButtonHandler()
                                                    vm.tutoVM.nextTuto()
                                                }
                                            }
                                    )
                                }
                            }
                            else
                                Box(
                                    Modifier
                                        .height(vm.data.layout.thirdPart.sizes.buttonHeight.dp)
                                        .width(vm.data.layout.thirdPart.sizes.buttonWidth.dp) )
                            Box(Modifier.weight(1F))
                        }
                    }
                }
            }
        }

        DragAndDropOverlay(vm)

        AnimatedVisibility(
            visible = visisbleMenu,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            /** Instruction Menu **/
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
                                            (vm.tutoVM.isTutoClickOnFirstInstructionFromMenu() && c == 'u' && instructions.colors.first() == 'B')
                                            || (vm.tutoVM.isTutoClickOnSecondInstructionFromMenu() && c == 'u' && instructions.colors.first() == 'g')
                                            || (vm.tutoVM.isTutoClickOnRepeatingFirstLineGray() && c == '0' && instructions.colors.first() == 'g')
                                        ) {
                                            Box( Modifier.clickable {
                                                    vm.ChangeInstructionMenuState()
                                                    vm.tutoVM.nextTuto()
                                                }
                                            ) {
                                                InstructionCase(
                                                    vm,
                                                    FunctionInstruction(
                                                        c,
                                                        instructions.colors.first()
                                                    ),
                                                    Modifier
                                                )
                                                enlightItem(
                                                    modifier = Modifier
                                                        .clickable {
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
                                                            vm.tutoVM.nextTuto()
                                                        }
                                                        .size(vm.data.layout.menu.sizes.case.dp)
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

private fun Modifier.dragAndDropCaseTuto(
    vm: GameDataViewModel,
    levelFunctions: List<FunctionInstructions>,
): Modifier = Modifier.pointerInput(Unit) {
    detectDragGestures(
        onDrag = { change, _ ->
            vm.dragAndDropCase.onDrag(
                pointerInputChange = change,
                list = vm.instructionsRows
            )
        },
        onDragStart = { _offset ->
            infoLog("onDragStart", "started")
            if (vm.isDragAndDropAvailable()) {
                vm.clickResetButtonHandler()
                vm.dragAndDropCase.onDragStart(_offset, levelFunctions)
            }
        },
        onDragEnd = {
            vm.dragAndDropCase.onDragEnd(vm, vm.tutoVM)
            errorLog("onDragEnd", "end")
        },
        onDragCancel = {
        }
    )
}
