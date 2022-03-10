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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsActionRow
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenConfig
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground

//Todo: replace all this logic based on the breadCrumb in an viewModel or an other entity to unload the screen ??? should it be really on the main thread ?
//Todo: replace fonction calls by little spaces
//Todo: delay the disparition of the last action in actionList
@Composable
fun DisplayActionsRow(vm: GameDataViewModel) {
    val actionsList: List<FunctionInstruction> by vm.animationLogicVM.data.actionList.collectAsState()

//    val dragStart2: Boolean by vm.dragStart.collectAsState()
//    val dragStart1: Boolean by vm.dragAndDrop.dragStart.collectAsState()

    verbalLog("Display Actions i", vm.breadcrumb.actions.instructions)
    verbalLog("Display Actions c", vm.breadcrumb.actions.colors)
    verbalLog("Display vm.ActionsList ", "${vm.animationLogicVM.data.actionList.value}")
    verbalLog("Display ActionsList ", "$actionsList")
    infoLog("action row case size", "${vm.data.layout.secondPart.size.actionRowCase}")
    infoLog("action row case border size", "${vm.data.layout.secondPart.size.actionRowCaseBorder}")

    Row() {
        actionsList.forEachIndexed { index, functionInstruction ->
            ActionRowCase(vm = vm, case = functionInstruction)
        }
    }
}

@Composable
fun ActionRowCase(vm: GameDataViewModel, case: FunctionInstruction) {
    Card(Modifier
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
                        vm.displayInstructionsMenu.value == true
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
