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

    infoLog("dragStart", "$dragStart")
//    infoLog("map", "${vm.dragAndDrop.listDroppaableRowsAndCases}")
//    infoLog("selcted", "${vm.dragAndDrop.selectedItem}")
//    Box( Modifier.fillMaxHeight().size(2.dp) .backColor(greendark10)
//            .offset { IntOffset(402, 453) }
//    )
//    Box( Modifier
//        .offset { IntOffset(402, 453) }
//        .fillMaxHeight()
//        .width(2.dp)
//        .backColor(yellow0)
//    )
//    Box( Modifier
//        .offset { IntOffset(402, 453) }
//        .fillMaxWidth()
//        .height(2.dp)
//        .backColor(yellow0)
//    )

//    Box( Modifier
//        .offset { IntOffset(
//            x=552,
//            y= 1508
//            x = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[0].topLeft.x.toInt() ,
//            y = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[1].topLeft.y.toInt() ,
//        )}
//        .size(2.dp)
//        .backColor(red0)
//    )
//    Box( Modifier
//        .offset { IntOffset(
//            x=437,
//            y= 1393
//            x = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[0].topLeft.x.toInt() ,
//            y = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[1].topLeft.y.toInt() ,
//        )}
//        .size(2.dp)
//        .backColor(red0)
//    )
//    Box( Modifier
//        .offset { IntOffset(
//            x = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[0].bottomRight.x.toInt() ,
//            y = vm.dragAndDrop.listDroppaableRowsAndCases[0].second[1].bottomRight.y.toInt() ,
//        )}
//        .size(2.dp)
//        .backColor(red0)
//    )
//    Box( Modifier
//        .offset { IntOffset(402, 453 + 1006) }
//        .fillMaxWidth()
//        .height(2.dp)
//        .backColor(red0)
//    )
    if (dragStart) {
        vm.dragAndDrop.elements.getColor(level.funInstructionsList) ?.let { _color ->
            vm.dragAndDrop.elements.getInstruction(level.funInstructionsList) ?.let { _instruction ->
                Box( Modifier.fillMaxSize() ) {
                    Box(Modifier.offset { IntOffset(offsetTouch.x.toInt(), offsetTouch.y.toInt()) }
                    ) {
                        FunctionCase(color = _color, vm = vm, instructionChar = _instruction)
                    }
                }
            }
        }
    }
}