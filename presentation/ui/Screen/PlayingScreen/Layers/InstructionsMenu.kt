package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.Layers

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsMenu
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

//todo : highlight in background only the instruction you selected ?
//todo : il manque la touche supprimer l'instruction selectionnée
@Composable
fun DisplayInstuctionMenu(level: RobuzzleLevel, vm: GameDataViewModel) {
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
                level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
                    Row(
                        modifier = Modifier
                            .width(800.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        instructions.instructions.toList().forEachIndexed { index, c ->
                            //todo: InstructionMenuCase()
                            InstructionCase(level, vm, FunctionInstructions(c.toString(), instructions.colors))
                        }
                    }
                }
            }
        }
}

@Composable
fun InstructionCase(level: RobuzzleLevel, vm: GameDataViewModel, case: FunctionInstructions) {
    Box(
        modifier = Modifier
            .gradientBackground(
                ColorsList(case.colors, false),
                175f
            )
            .clickable {
                level.replaceInstruction( level.selected, case )
                Log.i("ON CLICK actionList", level.breadcrumb.actionList)
                vm.ChangeInstructionMenuState()
            }
//            .size(screenConfig.instructionMenuCaseWidth.dp)
//            .border(
//                width = screenConfig.instructionMenuCaseBorder.dp,
//                color = Color.Black
//            )
            .size(vm.data.layout.menu.size.case.dp)
            .border(
                width = vm.data.layout.menu.size.casePadding.dp,
                color = vm.data.colors.menuCaseBorder
            )
    ) {
        InstructionIconsMenu(case.instructions.first(), vm)
    }
}
