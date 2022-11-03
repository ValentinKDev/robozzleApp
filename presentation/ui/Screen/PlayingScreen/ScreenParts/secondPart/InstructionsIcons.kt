package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import backColor
import com.mobilegame.robozzle.data.configuration.inGame.InGameColors
import com.mobilegame.robozzle.data.configuration.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.red1
import com.mobilegame.robozzle.presentation.res.redDark1
import com.mobilegame.robozzle.presentation.res.redDark4
import com.mobilegame.robozzle.utils.Extensions.toCaseColor

@Composable
fun InstructionsIconsFunction(instruction: Char, vm: GameDataViewModel, darkFilter: Boolean) {
    InstructionIcons(
        instruction,
//        vm.data.layout.secondPart.caseColoringIcon,
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
            SelectGoogleIcons(instruction, sizeInstruction )
        }
//        instruction == 'x' -
        else -> { }
    }
}

@Composable
fun SelectGoogleIcons(instruction: Char, sizeIconDp: Dp) {
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = when (instruction) {
                'r' -> Icons.Outlined.Redo
                'l' -> Icons.Outlined.Undo
                'u' -> Icons.Outlined.ArrowUpward
                '0' -> Icons.Outlined.ExposureZero
                'x' -> Icons.Outlined.Close
                '1' -> Icons.Outlined.LooksOne
                '2' -> Icons.Outlined.LooksTwo
                '3' -> Icons.Outlined.Looks3
                '4' -> Icons.Outlined.Looks4
                '5' -> Icons.Outlined.Looks5
                '6' -> Icons.Outlined.Looks6
                else -> Icons.Default.Home
            },
            tint = if (instruction == 'x') redDark1 else LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            contentDescription = "instruction",
            modifier = Modifier
                .size(sizeIconDp)
                .align(Alignment.Center)
        )
    }
}