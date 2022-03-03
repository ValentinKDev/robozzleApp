package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.greendark10
import com.mobilegame.robozzle.presentation.res.red0
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.res.yellow0
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.secondPart.FunctionCase
import com.mobilegame.robozzle.presentation.ui.utils.extensions.backColor

@Composable
fun DragAndDropOverlay(level: RobuzzleLevel, vm: GameDataViewModel) {
    val dragStart: Boolean by vm.dragAndDrop.dragStart.collectAsState()
    val offsetTouch by vm.dragAndDrop.touchOffSet.collectAsState()

    if (dragStart) {
        vm.dragAndDrop.elements.getColor(level.funInstructionsList) ?.let { _color ->
            vm.dragAndDrop.elements.getInstruction(level.funInstructionsList) ?.let { _instruction ->
                    Box( Modifier.fillMaxSize() ) {
                        Box(Modifier
                            .offset {
                                IntOffset(
                                    x = offsetTouch.x.toInt() - vm.data.getFunctionCaseHalfSize(),
                                    y = offsetTouch.y.toInt() - vm.data.getFunctionCaseHalfSize()
                                )
                            }
                        ) {
                            FunctionCase(color = _color, vm = vm, instructionChar = _instruction)
                        }
                    }
            }
        }
    }
}