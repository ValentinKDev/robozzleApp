package com.mobilegame.robozzle.presentation.ui.InGameCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.Extensions.match
import com.mobilegame.robozzle.domain.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.ui.ColorsList

@Composable
fun InstructionsIconsFunction(instruction: Char, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    InstructionIcons(instruction, gameDataViewModel, screenConfig.instructionFunctionCase, screenConfig.instructionFunctionIcon)
}

@Composable
fun InstructionIconsActionRow(instruction: Char, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    InstructionIcons(instruction, gameDataViewModel, screenConfig.instructionActionRowCase, screenConfig.instructionActionRowIcon)
}

@Composable
fun InstructionIconsMenu(instruction: Char, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    InstructionIcons(instruction, gameDataViewModel, screenConfig.instructionMenuCase, screenConfig.instructionMenuIcon)
}

@Composable
fun InstructionIcons(instruction: Char, gameDataViewModel: GameDataViewModel, sizeCase: Int, sizeIcon: Int) {
    if (instruction.toString().matches("[RGBg]".toRegex())) {
        //todo: resize shape of the colored square inside the black square in the actionsRow
        MyIconCaseColoring(instruction, sizeIcon, sizeCase, gameDataViewModel)
    }
    else if (instruction == 'n') { return }
    else { SelectGoogleIcons(instruction, sizeIcon, sizeCase) }
}

@Composable
fun SelectGoogleIcons(instruction: Char, sizeIcon: Int, sizeCase: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = when (instruction) {
                'r' -> Icons.Outlined.Redo
                'l' -> Icons.Outlined.Undo
                'U' -> Icons.Outlined.ArrowUpward
//                    'R' ->
                //todo: function to get the function number in icon
                '0' -> Icons.Outlined.ExposureZero
                '1' -> Icons.Outlined.LooksOne
                '2' -> Icons.Outlined.LooksTwo
                '3' -> Icons.Outlined.Looks3
                '4' -> Icons.Outlined.Looks4
                '5' -> Icons.Outlined.Looks5
                '6' -> Icons.Outlined.Looks6
                else -> Icons.Default.Home
            },
            contentDescription = "instruction",
            modifier = Modifier
                .size(sizeIcon.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun MyIconCaseColoring(
    instruction: Char,
    sizeIcon: Int,
    sizeCase: Int,
    gameDataViewModel: GameDataViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .size((sizeCase - 10).dp)
                .border(
                    width = 2.dp,
                    color = Color.Black
                )
                .align(Alignment.Center)
        ) {
            Box(
                Modifier
                    .gradientBackground(
                        ColorsList(
                            instruction.toString(),
                            gameDataViewModel.displayInstructionsMenu.value == true
                        ), 145f
                    )
                    .size((sizeCase - 20).dp)
                    .align(Alignment.Center)
            ) {


            }
        }
    }
}
