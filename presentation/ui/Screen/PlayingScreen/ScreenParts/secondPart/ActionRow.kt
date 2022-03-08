package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsActionRow
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenConfig
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground

//Todo: replace all this logic based on the breadCrumb in an viewModel or an other entity to unload the screen ??? should it be really on the main thread ?
//Todo: replace fonction calls by little spaces
//Todo: delay the disparition of the last action in actionList
@Composable
fun DisplayActionsRow(lvl: RobuzzleLevel, vm: GameDataViewModel, screenConfig: ScreenConfig) {
    val actionsList: List<FunctionInstruction> by lvl.breadcrumb.actionsList.collectAsState()

    verbalLog("Display Actions i", "${lvl.breadcrumb.actions.instructions}")
    verbalLog("Display Actions c", "${lvl.breadcrumb.actions.colors}")
    verbalLog("Display ActionsList ", "${lvl.breadcrumb.actionsList.value}")
    infoLog("action row case size", "${vm.data.layout.secondPart.size.actionRowCase}")
    infoLog("action row case border size", "${vm.data.layout.secondPart.size.actionRowCaseBorder}")

    Row() {
        actionsList.forEachIndexed { index, functionInstruction ->
            Box(
                Modifier
                    .size(vm.data.layout.secondPart.size.actionRowCase.dp)
                    .border(
                        width = vm.data.layout.secondPart.size.actionRowCaseBorder.dp,
                        color = vm.data.colors.actionRowCaseBorder
                    )
                    .gradientBackground(
                        colors = ColorsList(
                            functionInstruction.color.toString(),
                            vm.displayInstructionsMenu.value == true
                        ),
                        angle = 45f
                    )
            ) {
                Box(Modifier.align(Alignment.Center)) {
                    InstructionIconsActionRow(functionInstruction.instruction, vm, screenConfig )
                }
            }
        }
    }
//    Row(modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
//        if (currentAction != lvl.breadcrumb.actions.instructions.length - 1) {
//            DisplayActionRowCase(currentAction, lvl, vm, screenConfig)
//        }
//        var nextAction = 1
//        while (nextAction < screenConfig.maxActionDisplayedActionRow &&
//            nextAction + currentAction < lvl.breadcrumb.actions.instructions.length) {
//            actionDisplayed = CalculateActionToDiplay(lvl, currentAction, nextAction)
//
//            DisplayActionRowCase(actionDisplayed, lvl, vm, screenConfig)
//            nextAction++
//        }
//    }
}

@Composable
fun ActionRowSurronder(screenConfig: ScreenConfig, vm: GameDataViewModel) {
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

//todo : est ce que la barre d'action s'affiche uniquement lors du lancement ? est ce que je peux animer les action qui se stack au fur et a mesure que le joueur selectionne des instructions
//todo : bug affichage quand la fonction 0 ne fait que rappeller la fonction 0
@Composable
fun DisplayActionRowCase(action: Int, lvl: RobuzzleLevel, vm: GameDataViewModel, screenConfig: ScreenConfig) {
    Box(
        modifier = Modifier
//            .size(screenConfig.instructionActionRowCase.dp)
//            .size(vm.data.getActionRowCaseSize().dp)
//            .size(vm.data.layout.secondPart.size.actionRowCase.dp)
            .size(50.dp)
            .gradientBackground(
                ColorsList(
                    lvl.funInstructionsList[lvl.breadcrumb.currentInstructionList[action].line].colors[lvl.breadcrumb.currentInstructionList[action].column].toString(),
                    vm.displayInstructionsMenu.value == true
                ), 45f
            )
            .border(width = 2.dp, color = Color.Black)
    ) {
        Box(Modifier.align(Alignment.Center)) {
            InstructionIconsActionRow(lvl.breadcrumb.actions.instructions[action], vm, screenConfig )
        }
    }
}

fun CalculateActionToDiplay(lvl: RobuzzleLevel, currentAction: Int, nextActions: Int): Int {
    val actionToDisplay: Int = currentAction + nextActions
//    val actionListSize: Int = lvl.guideline.actions.instructions.length
    val actionListSize: Int = lvl.breadcrumb.actions.instructions.length

    return (if (actionToDisplay >= actionListSize) actionListSize - 1 else actionToDisplay)
}
