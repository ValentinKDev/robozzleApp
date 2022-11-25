package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.InGame.AnimationStream
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel

internal fun Modifier.dragActionRow(vm: GameDataViewModel): Modifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, _ ->
                vm.dragAndDropRow.onDrag( pointerInputChange = change)
                vm.dragAndDropRow.move()?.let {
                    when (it) {
                        AnimationStream.Forward -> vm.clickNextButtonHandler()
                        AnimationStream.Backward -> vm.clickBackButtonHandler()
                    }
                }
            },
            onDragStart = { _offset ->
                infoLog("onDragStart", "started")
                vm.dragAndDropRow.onDragStart(_offset)
                infoLog( "vm.dragstart", "${vm.dragAndDropRow.dragStart.value}" )
            },
            onDragEnd = {
                vm.dragAndDropRow.onDragReset()
                errorLog("onDragEnd", "end")
            },
            onDragCancel = {
                vm.dragAndDropRow.onDragReset()
                errorLog("onDragCanceled", "cancel")
            }
        )
    }