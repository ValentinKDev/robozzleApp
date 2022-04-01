package com.mobilegame.robozzle.domain.model.gesture.dragAndDropRow

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.InGame.AnimationStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class DragAndDropRowState() {
    private val max = 100F
    private val maxActionMoveInOneDrag = 30F
//    val step: Float = maxActionMoveInOneDrag / max
    val step: Float = max / maxActionMoveInOneDrag
    var actionMove = 0
    var precedentMove = 0

    var rowWidth: Float = 0F
    fun setDraggableLength(layoutCoordinates: LayoutCoordinates) {
        if (rowWidth == 0F) {
            rowWidth = layoutCoordinates.boundsInRoot().width
        }
    }

    private val _dragStart = MutableStateFlow<Boolean>(false)
    val dragStart: StateFlow<Boolean> = _dragStart.asStateFlow()
    fun setDragStart(value: Boolean) {
        _dragStart.value = value
    }

    private var touchXStart: Float? = null
    fun setTouchOffSetStart(localOffset: Offset) {
        touchXStart ?: run { touchXStart = localOffset.x }
    }
    private var touchX: Float? = null
    fun setTouchOffset(localOffset: Offset) {
        touchX = localOffset.x
    }

//    private val _dragXPercantage = MutableStateFlow<Float?>(0F)
    var dragXPercantage: Float = 0F
    fun calculDragXPercantage() {
        touchX?.let { _touchX ->
            touchXStart?.let { _touchXStart ->
                val difference = _touchXStart - _touchX
                dragXPercantage = (difference * 100) / rowWidth
                actionMove = (dragXPercantage / step).toInt()
//                infoLog("actionMove", "$actionMove")
            }
        }
    }
    fun move(): AnimationStream? {
        return when {
            actionMove > precedentMove -> {
                precedentMove = actionMove
                AnimationStream.Forward
            }
            actionMove < precedentMove -> {
                precedentMove = actionMove
                AnimationStream.Backward
            }
            else -> null
        }
    }

    fun onDrag(pointerInputChange: PointerInputChange) {
        setTouchOffset(pointerInputChange.position)
        calculDragXPercantage()
    }

    fun onDragStart(offset: Offset) = runBlocking(Dispatchers.IO) {
        setTouchOffSetStart(offset)
        touchXStart ?: setTouchOffSetStart(offset)
//        setOffsets(offset)
//        elements.findSelectedItem(touchOffSetStart ?: Offset.Zero, list)
        setDragStart(true)
    }

    fun onDragReset() {
        touchX = null
        touchXStart = null
        dragXPercantage = 0F
        actionMove = 0
        precedentMove = 0
    }

    private fun setPercantage() {
        infoLog("start", "${touchXStart?.div(rowWidth)}")
        infoLog("percantage", "${touchX?.div(rowWidth)}")
    }

}
