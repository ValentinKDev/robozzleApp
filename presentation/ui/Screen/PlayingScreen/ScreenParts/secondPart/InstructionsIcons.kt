package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.data.layout.inGame.InGameColors
import com.mobilegame.robozzle.data.layout.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.utils.Extensions.toCaseColor

@Composable
fun InstructionsIconsFunction(instruction: Char, vm: GameDataViewModel, darkFilter: Boolean) {
    InstructionIcons(
        instruction,
        vm.data.layout.secondPart.caseColoringIcon,
        vm.data.colors,
        vm.data.layout.secondPart.size.functionCaseIconDp,
        darkFilter
    )
}

@Composable
fun InstructionIconsActionRow(
    instruction: Char,
    vm: GameDataViewModel,
    darkFilter: Boolean,
    bigger: Boolean
) {
    InstructionIcons(
        instruction,
        vm.data.layout.secondPart.actionRow.caseColoringIcon,
        vm.data.colors,
        if (bigger) vm.data.layout.secondPart.size.actionRowBiggerIconDp else vm.data.layout.secondPart.size.actionRowIconDp,
        darkFilter
    )
}

@Composable
fun InstructionIconsMenu(instruction: Char, vm: GameDataViewModel) {
    InstructionIcons(
        instruction,
        vm.data.layout.menu.caseColoringIcon,
        vm.data.colors,
        vm.data.layout.menu.size.iconDp
    )
}

@Composable
fun InstructionIcons(
    instruction: Char,
    caseColoringData: CaseColoringIcon,
    colors: InGameColors,
    sizeInstruction: Dp,
    darkFilter: Boolean = false
) {
    when {
        instruction.toString().matches("[RGBg]".toRegex()) -> {
            DrawCaseColoringIcon(
                colorInto = instruction.toCaseColor(),
                data = caseColoringData,
                dataColors = colors,
                darkFilter =  darkFilter
            )
        }
        instruction.toString().matches("[urlx0-6]".toRegex()) -> {
            SelectGoogleIcons(instruction, sizeInstruction, darkFilter)
        }
//        instruction == 'x' -
        else -> { }
    }
}

@Composable
fun SelectGoogleIcons(instruction: Char, sizeIconDp: Dp, darkFilter: Boolean) {
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = when (instruction) {
                'r' -> Icons.Outlined.Redo
                'l' -> Icons.Outlined.Undo
                'u' -> Icons.Outlined.North
                'x' -> Icons.Outlined.Close
                else -> iconByInt(instruction.toString().toInt())
            },
            tint =
//            if (instruction == 'x') redDark4
//            else if (darkFilter) grayDark8
//            else
                grayDark6,
            contentDescription = "instructionBack",
            modifier = Modifier
                .size((sizeIconDp.value * 1.1F).dp)
                .align(Alignment.Center)
        )
        Icon(
            imageVector = when (instruction) {
                'r' -> Icons.Outlined.Redo
                'l' -> Icons.Outlined.Undo
                'u' -> Icons.Outlined.North
                'x' -> Icons.Outlined.Close
                else -> iconByInt(instruction.toString().toInt())
            },
            tint =
//            if (instruction == 'x') redDark1
//            else if (darkFilter) whiteDark8
//            else
                whiteDark6,
            contentDescription = "instruction",
            modifier = Modifier
                .size(sizeIconDp)
                .align(Alignment.Center)
        )
    }
}

fun iconByInt(number: Int): ImageVector = when (number) {
    0 -> Icons.Outlined.LooksOne
    1 -> Icons.Outlined.LooksTwo
    2 -> Icons.Outlined.Looks3
    3 -> Icons.Outlined.Looks4
    4 -> Icons.Outlined.Looks5
    5 -> Icons.Outlined.Looks6
    else -> Icons.Outlined.Help
}

