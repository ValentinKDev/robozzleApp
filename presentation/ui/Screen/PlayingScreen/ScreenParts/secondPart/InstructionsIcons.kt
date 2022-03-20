package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

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
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.mapCaseColorList
import gradientBackground

@Composable
fun InstructionsIconsFunction(instruction: Char, vm: GameDataViewModel) {
    InstructionIcons(instruction, vm, vm.data.layout.secondPart.size.functionCase, vm.data.layout.secondPart.size.functionCaseIcon)
}

@Composable
fun InstructionIconsActionRow(instruction: Char, vm: GameDataViewModel) {
    InstructionIcons(instruction, vm, vm.data.layout.secondPart.size.actionRowCase, vm.data.layout.secondPart.size.actionRowIcon)
}

@Composable
fun InstructionIconsMenu(instruction: Char, vm: GameDataViewModel) {
    InstructionIcons(
        instruction,
        vm,
        vm.data.layout.menu.size.case.toInt(),
        vm.data.layout.menu.size.icon.toInt()
    )
}

@Composable
fun InstructionIcons(instruction: Char, gameDataViewModel: GameDataViewModel, sizeCase: Int, sizeIcon: Int) {
    when {
        instruction.toString().matches("[RGBg]".toRegex()) -> {
            //todo: resize shape of the colored square inside the black square in the actionsRow
            MyIconCaseColoring(instruction, sizeCase, gameDataViewModel)
        }
        instruction.toString().matches("[url0-6]".toRegex()) -> {
            SelectGoogleIcons(instruction, sizeIcon)
        }
        else -> {
        }
    }
}

@Composable
fun SelectGoogleIcons(instruction: Char, sizeIcon: Int) {
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = when (instruction) {
                'r' -> Icons.Outlined.Redo
                'l' -> Icons.Outlined.Undo
                'u' -> Icons.Outlined.ArrowUpward
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
    sizeCase: Int,
    vm: GameDataViewModel
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
                        mapCaseColorList(
                            instruction,
                            vm.displayInstructionsMenu.value == true
                        ), 145f
                    )
                    .size((sizeCase - 20).dp)
                    .align(Alignment.Center)
            ) {


            }
        }
    }
}
