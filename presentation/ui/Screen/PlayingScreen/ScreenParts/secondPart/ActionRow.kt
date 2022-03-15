package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logAnimLayoutSecondPart
import com.mobilegame.robozzle.analyse.logLayoutSecondPart
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsActionRow
import com.mobilegame.robozzle.utils.Extensions.subListIfPossible
import gradientBackground

//Todo: replace all this logic based on the breadCrumb in an viewModel or an other entity to unload the screen ??? should it be really on the main thread ?
//Todo: replace fonction calls by little spaces
//Todo: delay the disparition of the last action in actionList
@Composable
fun DisplayActionsRow(vm: GameDataViewModel) {

    val actionList: List<FunctionInstruction> by vm.animData.actionRowList.collectAsState()
//    val recomposition: Int by vm.triggerRecompositionInstructionsRows.observeAsState(initial = 0)
//    val list = if (recomposition < 0) emptyList() else actionList
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    logLayoutSecondPart?.let {
        infoLog("action row case size", "${vm.data.layout.secondPart.size.actionRowCase}")
        infoLog("action row case border size", "${vm.data.layout.secondPart.size.actionRowCaseBorder}")
    }
    logAnimLayoutSecondPart?.let {
//        verbalLog("action to read", currentAction.toString())
        verbalLog("action to read", vm.animData.actionToRead.value.toString())
//        verbalLog("Actions list i", vm.breadcrumb.actions.instructions)
//        verbalLog("Actions list c", vm.breadcrumb.actions.colors)
        verbalLog("Display vm.ActionsList ", "${vm.animData.actionRowList.value}")
//        verbalLog("Display ActionsList ", "$list")
//        verbalLog("Display ActionsList size", "${list.size}")
        verbalLog("number of action to display", "${vm.data.layout.secondPart.actionToDisplayNumber}")
    }

    Row() {
        Box( Modifier
            .weight(vm.data.layout.secondPart.ratios.actionRowFirstPart)
            ,
        ) {
            if (actionList.isNotEmpty())
                ActionRowCase(vm = vm, case = actionList.first(), filter = displayInstructionMenu)
        }
        Box( Modifier
            .weight(vm.data.layout.secondPart.ratios.actionRowSecondPart)
        ) {
            Row {
                actionList.subListIfPossible(fromIndex = 1)
                    .forEachIndexed {index, functionInstruction ->
                        logAnimLayoutSecondPart?.let { verbalLog("Display Action", "index $index : $functionInstruction") }
                        ActionRowCase(vm = vm, case = functionInstruction, filter = displayInstructionMenu)
                    }
            }
        }
    }
}

@Composable
fun ActionRowCase(vm: GameDataViewModel, case: FunctionInstruction, filter: Boolean) {
    Card(
        Modifier
            .size(vm.data.layout.secondPart.size.actionRowCase.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
            ,
            shape = RoundedCornerShape(corner= CornerSize(5.dp)),
            elevation = vm.data.colors.actionRowCaseElevation
        ) {
            Box( Modifier.gradientBackground(
                colors = ColorsList(
                    case.color,
                    filter
//                    vm.displayInstructionsMenu.value,
                ),
                angle = 45f
            )
            ) {
                InstructionIconsActionRow(case.instruction, vm)
            }
        }
}
@Composable
fun ActionRowSurronder(vm: GameDataViewModel) {
    Column() {
        Surface(
            elevation = 5.dp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(vm.data.layout.secondPart.ratios.actionRowSurronderBlackLineHeight)
        ) { }
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(vm.data.layout.secondPart.ratios.actionRowSurronderEmptyLineHeight)
            .background(Color.Transparent)
        ){}
        Surface(
            elevation = 5.dp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(vm.data.layout.secondPart.ratios.actionRowSurronderBlackLineHeight)
        ) { }
    }
}
