package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionsIconsFunction
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground

@Composable
fun DisplayFunctionsPart(lvl: RobuzzleLevel, vm: GameDataViewModel) {
    val density = LocalDensity.current.density
    Column( Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            vm.data.secondPartHeight = it.size.height
            vm.data.secondPartWidth = it.size.width
            vm.data.density = density

//            vm.dragAndDrop.setDragListnerRect(it)
            vm.dragAndDrop.elements.setDraggableParentOffset(it)
        }
//        .backColor(greendark10)
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDrag = { change, _offset ->
                    infoLog("onDrag", "position ${change.position}")
                    vm.dragAndDrop.setTouchOffset(change.position)
                },
                onDragStart = { _offset ->
                    infoLog("onDragStart", "started")
                    infoLog("onDrag", "_offsetStart $_offset")
                    vm.dragAndDrop.onDragStart(_offset)
                },
                onDragEnd = {
                    vm.dragAndDrop.onDragEnd()
                    errorLog("onDragEnd", "end")
                },
                onDragCancel = {
                    vm.dragAndDrop.onDragCancel()
                    errorLog( "onDragCanceled", "cancel" )
                }
            )
        }
    ) {
        CenterComposable {
            lvl.funInstructionsList.forEachIndexed { functionNumber, function ->
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
            vm.data.functionsNumber = lvl.funInstructionsList.size
            vm.data.maxCasesNumber = function.instructions.length

//            vm.dragAndDrop.addDroppableRow(functionNumber, it)
            vm.dragAndDrop.elements.addDroppableRow(functionNumber, it)
        }
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = vm.data.text.functionText(functionNumber),
            color = vm.data.colors.functionTextColor
        )
        Row() {
            function.instructions.forEachIndexed { _index, c ->
                val caseColor = function.colors[_index].toString()
                Box(
                    Modifier
                        .background(vm.data.colors.functionBorderColor)
                        .size(vm.data.getFunctionCaseSize().dp)
//                        .size(40.dp)
                        .padding(vm.data.getFunctionCasePadding().dp)
                        .onGloballyPositioned {
                            errorLog("$functionNumber ${_index}", "${it.boundsInRoot().topLeft}")
                            vm.dragAndDrop.elements.addDroppableCase(
                                rowIndex = functionNumber,
                                columnIndex = _index,
                                it
                            )
                        }
                        .clickable {
                            if (!animationRunningInBackground) {
                                vm.ChangeInstructionMenuState()
                                lvl.SetSelectedFunctionCase(functionNumber, _index)
                            }
                        }
                ) {
                    val instructionChar = function.instructions[_index]
                    if ( lvl.breadcrumb.currentInstructionList.isNotEmpty()
                        && ( (currentAction == 0 && functionNumber == 0 && _index == 0)
                                || lvl.breadcrumb.currentInstructionList[currentAction].Match( Position(functionNumber, _index) ) )
                        && animationRunningInBackground )
                    {
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .size(40.dp)
                                .padding(40.dp)
                        ) { }
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .gradientBackground(
                                    ColorsList(
                                        caseColor,
                                        vm.displayInstructionsMenu.value == true
                                    ), 175f
                                )
                                .size((40 - 12).dp)
                                .padding((4).dp)
                        ){
                            if (instructionChar != '.') {
                                InstructionsIconsFunction(instructionChar, vm)
                            }
                        }
                    }
                    else {
                        FunctionCase(
                            caseColor,
                            vm,
                            instructionChar
                        )
                    }
                }
            }

        }
    }
}
