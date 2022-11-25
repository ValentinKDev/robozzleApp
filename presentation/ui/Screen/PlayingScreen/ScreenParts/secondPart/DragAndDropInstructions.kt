package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel

fun Modifier.dragAndDropCase(vm: GameDataViewModel, levelFunctions: List<FunctionInstructions>): Modifier = Modifier.pointerInput(Unit) {
    detectDragGestures(
        onDrag = { change, _ ->
            infoLog("onDrag", "position ${change.position}")
            vm.dragAndDropCase.onDrag(
                pointerInputChange = change,
                list = vm.instructionsRows
            )
        },
        onDragStart = { _offset ->
            errorLog("TEST", "${vm.animData.playerAnimationState.value.key}")
            infoLog("onDragStart", "started")
            if (vm.isDragAndDropAvailable()) {
                vm.clickResetButtonHandler()
                vm.dragAndDropCase.onDragStart(_offset, levelFunctions)
            }
            infoLog("vm.dragstrt", "${vm.dragAndDropCase.dragStart.value}")
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
