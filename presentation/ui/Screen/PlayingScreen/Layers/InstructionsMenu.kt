package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.isDelete
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.mapCaseColorList
import com.mobilegame.robozzle.presentation.res.redDark1
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.InstructionIconsMenu
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableHorizontally
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import gradientBackground

//todo : highlight in background only the instruction you selected ?
//todo : il manque la touche supprimer l'instruction selectionnée
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DisplayInstuctionMenu(vm: GameDataViewModel) {
    val interactionExtMenu = remember { MutableInteractionSource() }
    Box(
        Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionExtMenu,
                indication = null
            ) { vm.ChangeInstructionMenuState() }
    )
    PaddingComposable(
        topPaddingRatio = vm.data.layout.menu.ratios.topPadding,
        bottomPaddingRatio = vm.data.layout.menu.ratios.bottomPadding,
//        startPaddingRatio = vm.data.layout.menu.ratios.startPadding,
//        endPaddingRatio = vm.data.layout.menu.ratios.endPadding,
    ) {
    CenterComposableHorizontally {
            Box(
                Modifier
                    .clickable { vm.ChangeInstructionMenuState() }
                ,
            ){
                Column(
                    Modifier
                        .width(vm.data.layout.menu.size.windowWidth.dp)
                        .background(Color.Transparent)
                ) {
                    vm.level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
                        Row( Modifier .background(Color.Transparent)
                            ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            instructions.instructions.toList().forEachIndexed { index, c ->
                                InstructionCase(vm, FunctionInstruction(c, instructions.colors.first()))
                            }
                        }
                    }
                    CenterComposableHorizontally {
                        InstructionCase(vm = vm, case = FunctionInstruction(instruction = 'x', color = 'g'))
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
                mapCaseColorList(case.color, false),
                175f
            )
            .clickable {
                vm.replaceInstruction(
                    vm.selectedCase,
                    if (case.isDelete()) FunctionInstruction('.', 'g') else case
                )
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
