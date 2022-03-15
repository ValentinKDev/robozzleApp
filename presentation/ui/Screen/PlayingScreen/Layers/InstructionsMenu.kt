package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsMenu
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import gradientBackground

//todo : highlight in background only the instruction you selected ?
//todo : il manque la touche supprimer l'instruction selectionnée
@Composable
fun DisplayInstuctionMenu(vm: GameDataViewModel) {
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.observeAsState( false )

    if (displayInstructionMenu) {

//        ) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable { vm.ChangeInstructionMenuState() }
        ) {
            PaddingComposable(
                topPaddingRatio = vm.data.layout.menu.ratios.topPadding,
                bottomPaddingRatio = vm.data.layout.menu.ratios.bottomPadding,
                startPaddingRatio = vm.data.layout.menu.ratios.startPadding,
                endPaddingRatio = vm.data.layout.menu.ratios.endPadding,
            ) {
                Card(
                    Modifier
                        .fillMaxSize()
                        .clickable { vm.ChangeInstructionMenuState() }
                    ,
                    shape = RectangleShape,
                    elevation = 25.dp
                ){
                    Column() {
                        vm.level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
                            Row(
                                modifier = Modifier
                                    .width(800.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                instructions.instructions.toList().forEachIndexed { index, c ->
                                    //todo: InstructionMenuCase()
                                    InstructionCase(vm, FunctionInstruction(c, instructions.colors.first()))
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
fun InstructionCase(vm: GameDataViewModel, case: FunctionInstruction) {
    Box(
        modifier = Modifier
            .gradientBackground(
                ColorsList(case.color, false),
                175f
            )
            .clickable {
                vm.replaceInstruction(vm.selectedCase, case)
                vm.ChangeInstructionMenuState()
            }
            .size(vm.data.layout.menu.size.case.dp)
            .border(
                width = vm.data.layout.menu.size.casePadding.dp,
                color = vm.data.colors.menuCaseBorder
            )
    ) {
        InstructionIconsMenu(case.instruction, vm)
    }
}
