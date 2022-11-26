package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.TutoViewModel

fun Modifier.dragAndDropCase(
    vm: GameDataViewModel,
    levelFunctions: List<FunctionInstructions>,
//    tutoVM: TutoViewModel? = null
): Modifier = Modifier.pointerInput(Unit) {
    detectDragGestures(
        onDrag = { change, _ ->
            vm.dragAndDropCase.onDrag(
                pointerInputChange = change,
                list = vm.instructionsRows
            )
        },
        onDragStart = { _offset ->
            infoLog("onDragStart", "started")
            if (vm.isDragAndDropAvailable()) {
                vm.clickResetButtonHandler()
                vm.dragAndDropCase.onDragStart(_offset, levelFunctions)
            }
        },
        onDragEnd = {
            vm.dragAndDropCase.onDragEnd(vm)
            errorLog("onDragEnd", "end")
        },
        onDragCancel = {
            vm.dragAndDropCase.onDragCancel()
            errorLog("onDragCanceled", "cancel")
        }
    )
}
