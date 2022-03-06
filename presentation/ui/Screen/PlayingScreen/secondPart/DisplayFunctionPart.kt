package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.res.greendark10
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.EmptySquare
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionsIconsFunction
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.extensions.backColor
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground

@Composable
fun DisplayFunctionsPart(lvl: RobuzzleLevel, vm: GameDataViewModel) {
    val density = LocalDensity.current.density

    val animationIsPlaying: Boolean by vm.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by vm.animationIsOnPause.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause

    val draggedStart : Boolean by vm.dragAndDrop.dragStart.collectAsState()
//    val levelFunctions: List<FunctionInstructions> by lvl.instructionRows.collectAsState()
    val levelFunctions = lvl.instructionRows.value
    infoLog("lvl", "${lvl.instructionRows.value}")
    infoLog("levelFucntions", "$levelFunctions")
    infoLog("draggedStart", "$draggedStart")

//    levelFunctions =
    val functions =
        if ( draggedStart )
            vm.dragAndDrop.elements.onHoldItem(levelFunctions.toMutableList())
        else
            levelFunctions

    verbalLog("functions", "display $functions")

    Column(Modifier
        .fillMaxSize()
        .onGloballyPositioned {
//            vm.data.secondPartHeight = it.size.height
//            vm.data.secondPartWidth = it.size.width
//            vm.data.density = density

            vm.dragAndDrop.elements.setDraggableParentOffset(it)
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, _ ->
                    infoLog("onDrag", "position ${change.position}")
                    vm.dragAndDrop.onDrag(
                        change,
//                        vm.data.getFunctionCaseHalfSize(),
                        vm.data.layout.secondPart.size.halfFunctionCase.toFloat(),
                        functions
                    )
                },
                onDragStart = { _offset ->
                    infoLog("onDragStart", "started")
                    vm.dragAndDrop.onDragStart(_offset, functions)
                },
                onDragEnd = {
                    vm.dragAndDrop.onDragEnd(lvl)
                    errorLog("onDragEnd", "end")
                },
                onDragCancel = {
                    vm.dragAndDrop.onDragCancel(lvl.funInstructionsList)
                    errorLog("onDragCanceled", "cancel")
                }
            )
        }
    ) {
        CenterComposable {
//            lvl.funInstructionsList.forEachIndexed { functionNumber, function ->
            functions.forEachIndexed { functionNumber, function ->
                DisplayFunctionRow(lvl, functionNumber, function, vm)
            }
        }
    }
}

@Composable
fun DisplayFunctionRow(lvl: RobuzzleLevel, functionNumber: Int, function: FunctionInstructions, vm: GameDataViewModel) {
    val currentAction: Int by vm.actionToRead.observeAsState(0)

    val animationIsPlaying: Boolean by vm.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by vm.animationIsOnPause.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause


    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
//            vm.data.functionsNumber = lvl.funInstructionsList.size
//            vm.data.maxCasesNumber = function.instructions.length
            vm.dragAndDrop.elements.addDroppableRow(functionNumber, it)
        } ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = vm.data.text.functionText(functionNumber),
            color = vm.data.colors.functionTextColor
        )
        Row( Modifier
//            .height((vm.data.getFunctionCaseSize() + (2 * vm.data.getFunctionCasePadding())).dp)
//            .width(((function.instructions.length * (vm.data.getFunctionCaseSize() + vm.data.getFunctionCasePadding())) + vm.data.getFunctionCasePadding()).dp)
            .height((vm.data.layout.secondPart.size.functionCase + (2 * vm.data.layout.secondPart.size.functionCasePadding)).dp)
            .width(((function.instructions.length * (vm.data.layout.secondPart.size.functionCase + vm.data.layout.secondPart.size.functionCasePadding)) + vm.data.layout.secondPart.size.functionCasePadding).dp)
            .background(vm.data.colors.functionBorderColor)
            ,
        ) {
            CenterComposableVertically {
                Row( Modifier.fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    function.instructions.forEachIndexed { _index, c ->
//                        if ( vm.dragAndDrop.isSwitchingCase(functionNumber, _index)) {
//                        }
                        val caseColor = function.colors[_index].toString()
                        Box(Modifier
//                            .size(vm.data.getFunctionCaseSize().dp)
                            .size(vm.data.layout.secondPart.size.functionCase.dp)
//                            .size((vm.data.getFunctionCaseSize() + vm.data.getFunctionCasePadding()).dp)
                            .onGloballyPositioned {
                                vm.dragAndDrop.elements.addDroppableCase(
                                    rowIndex = functionNumber,
                                    columnIndex = _index,
                                    it
                                )
                            }
                            .clickable {
                                if (!animationRunningInBackground && !vm.dragAndDrop.dragStart.value) {
                                    vm.ChangeInstructionMenuState()
                                    lvl.SetSelectedFunctionCase(functionNumber, _index)
                                }
                            }
                        ) {
                            val instructionChar = function.instructions[_index]
                            Box( Modifier .fillMaxSize()
                            ) {
                                FunctionCase(color = caseColor, instructionChar = instructionChar, vm = vm)
                                if ( lvl.breadcrumb.currentInstructionList.isNotEmpty()
                                    && ( (currentAction == 0 && functionNumber == 0 && _index == 0) || lvl.breadcrumb.currentInstructionList[currentAction].Match( Position(functionNumber, _index) ) )
                                    && animationRunningInBackground
                                ) {
//                                    EmptySquare()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
