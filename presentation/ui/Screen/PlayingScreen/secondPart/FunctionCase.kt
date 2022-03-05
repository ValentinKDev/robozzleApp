package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.secondPart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionsIconsFunction
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground

@Composable
fun FunctionCase(color: String, vm: GameDataViewModel, instructionChar: Char, bigger: Boolean = false) {
    Box( Modifier
        .gradientBackground( ColorsList( color, vm.displayInstructionsMenu.value == true ), 175f )
        .size( vm.data.getFunctionCaseSize(bigger = bigger).dp)
    ){
        if (instructionChar != '.'){
            InstructionsIconsFunction(instructionChar, vm)
        }
    }
}
