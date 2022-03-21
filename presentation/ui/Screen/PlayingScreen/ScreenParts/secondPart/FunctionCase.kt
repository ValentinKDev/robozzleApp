package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.mapCaseColorList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionsIconsFunction
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import gradientBackground

@Composable
fun FunctionCase(color: Char, vm: GameDataViewModel, instructionChar: Char, bigger: Boolean = false, filter: Boolean = false) {
    Card(
        modifier = Modifier.size(
//            if (bigger) vm.data.layout.secondPart.size.bigFunctionCase.dp
//            else vm.data.layout.secondPart.size.functionCase.dp
            if (bigger) vm.data.layout.secondPart.size.bigFunctionCaseDp
            else vm.data.layout.secondPart.size.functionCaseDp
        )
        ,
        shape = RectangleShape
        ,
        elevation = 50.dp
    ) {
        Box( Modifier.gradientBackground(mapCaseColorList(color, filter), 175f) ) {
            if (instructionChar != '.'){
                CenterComposable {
                    InstructionsIconsFunction(instructionChar, vm, filter)
                }
            }
        }
    }
}
