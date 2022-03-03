package com.mobilegame.robozzle.domain.model.gesture.dragAndDrop

import androidx.compose.ui.geometry.Offset
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class DragAndDropState {
    val elements = DragAndDropElements()

    private val _dragStart = MutableStateFlow<Boolean>(false)
    val dragStart: StateFlow<Boolean> = _dragStart.asStateFlow()
    fun setDragStart(value: Boolean) { _dragStart.value = value }

    private val _touchOffSet = MutableStateFlow<Offset>(Offset(0F,0F))
    val touchOffSet: StateFlow<Offset> = _touchOffSet.asStateFlow()
    fun setTouchOffset(localOffset: Offset, ) {
        _touchOffSet.value = Offset(
            x = localOffset.x + elements.parentOffset.x,
            y = localOffset.y + elements.parentOffset.y
        )
    }

    fun onDragStart(offset: Offset) = runBlocking {
        setTouchOffset(offset)
        elements.findSelectedItem(touchOffSet.value)
        setDragStart(true)
    }

    fun onDragCancel() = runBlocking {
        setDragStart(false)

        elements.selectedItem = Position(-42, -21)
    }

    fun onDragEnd() = runBlocking {
        setDragStart(false)

        elements.selectedItem = Position(-42, -21)
    }
}


