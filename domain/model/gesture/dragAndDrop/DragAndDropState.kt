package com.mobilegame.robozzle.domain.model.gesture.dragAndDrop

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//class DragAndDropState {
class DragAndDropState(): ViewModel() {
    val elements = DragAndDropElements()

    private val _dragStart = MutableStateFlow<Boolean>(false)
    val dragStart: StateFlow<Boolean> = _dragStart.asStateFlow()
    fun setDragStart(value: Boolean) { _dragStart.value = value }

    private var touchOffSetStart: Offset? = null
    fun setTouchOffSetStart(localOffset: Offset) {
        touchOffSetStart ?: run {
            if (elements.parentOffset == Offset.Zero) errorLog("ERROR", "calcul touch offset start")
            touchOffSetStart = Offset(
                x = localOffset.x + elements.parentOffset.x,
                y = localOffset.y + elements.parentOffset.y
            )
        }
    }

    private val _touchOffSet = MutableStateFlow<Offset>(Offset(0F,0F))
    val touchOffSet: StateFlow<Offset> = _touchOffSet.asStateFlow()
//    fun setTouchOffset(localOffset: Offset): Job = viewModelScope.launch {
    fun setTouchOffset(localOffset: Offset) {
        _touchOffSet.value = Offset(
            x = localOffset.x + elements.parentOffset.x,
            y = localOffset.y + elements.parentOffset.y
        )
    }

    fun onDrag(change: PointerInputChange, list: List<FunctionInstructions>) {
        setTouchOffset(change.position)
//        elements.itemSelected ?: elements.findSelectedItem(touchOffSet.value, list)
    }

    fun onDragStart(offset: Offset, list: List<FunctionInstructions>) = runBlocking(Dispatchers.Default) {
        setTouchOffSetStart(offset)
        setTouchOffset(offset)
        elements.findSelectedItem(touchOffSetStart ?: Offset.Zero, list)
        setDragStart(true)
    }

    fun onDragCancel() = runBlocking {
        setDragStart(false)

        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
//        viewModelScope.launch(Dispatchers.IO) {
//            elements.itemSelectedPosition = Position(-42, -21)
//        }
    }

    fun onDragEnd() {
        setDragStart(false)

        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
//        viewModelScope.launch(Dispatchers.IO) {
//            elements.itemSelectedPosition = Position(-42, -21)
//        }
    }


}


