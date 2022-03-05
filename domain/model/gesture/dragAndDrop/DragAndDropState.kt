package com.mobilegame.robozzle.domain.model.gesture.dragAndDrop

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun setOffsets(localOffset: Offset) {
        setTouchOffset(localOffset)
        setDraggedRepresentationOffset()
        setPointerUnderOffset()
    }
    private val _touchOffSet = MutableStateFlow<Offset>(Offset(0F,0F))
    val touchOffSet: StateFlow<Offset> = _touchOffSet.asStateFlow()
    fun setTouchOffset(localOffset: Offset) {
        _touchOffSet.value = Offset(
            x = localOffset.x + elements.parentOffset.x,
            y = localOffset.y + elements.parentOffset.y
        )
    }

    private val _dragRepresentationOffset = MutableStateFlow<Offset>(Offset.Zero)
    val dragRepresentationOffset: StateFlow<Offset> = _dragRepresentationOffset.asStateFlow()
    fun setDraggedRepresentationOffset() {
        val elementHalfHeight: Float = elements.itemSelectedHalfHeight ?: 0F
        val elementTwoThirdHeight: Float = elements.itemSelectedTwoThirdHeight ?: 0F
        val elementOneThirdHeight: Float = elements.itemSelectedOneThirdHeight ?: 0F

        _dragRepresentationOffset.value = Offset(
            x = touchOffSet.value.x - elementHalfHeight - elementOneThirdHeight,
            y = touchOffSet.value.y - elementHalfHeight - elementOneThirdHeight,
        )
    }

    var underPointerOffset = Offset.Zero
    fun setPointerUnderOffset() {
        val elementHalfHeight: Float = elements.itemSelectedHalfHeight ?: 0F
        val elementTwoThirdHeight: Float = elements.itemSelectedTwoThirdHeight ?: 0F
        val elementOneThirdHeight: Float = elements.itemSelectedOneThirdHeight ?: 0F

        underPointerOffset = Offset(
            x = touchOffSet.value.x - elementOneThirdHeight,
            y = touchOffSet.value.y - elementHalfHeight - elementTwoThirdHeight,
        )
    }

    fun isSwitchingCase(row: Int, column: Int): Boolean {
        return (dragStart.value.not() && ((elements.itemUnderPosition?.Match(Position(row, column)) ?: false) || (elements.itemSelectedPosition?.Match(Position(row, column) ) ?: false )) )
    }

    fun onDrag(change: PointerInputChange, boxOffset: Float, list: List<FunctionInstructions>) {
        setOffsets(change.position)
        elements.findItemUnderItem(underPointerOffset, list)
    }

    fun onDragStart(offset: Offset, list: List<FunctionInstructions>) = runBlocking(Dispatchers.Default) {
        setTouchOffSetStart(offset)
        touchOffSetStart ?: setTouchOffSetStart(offset)
        setOffsets(offset)
        elements.findSelectedItem(touchOffSetStart ?: Offset.Zero, list)
        setDragStart(true)
    }

    fun onDragCancel(list: List<FunctionInstructions>) = runBlocking {
//        switch(lvl)
//        switchCases(list)

        setDragStart(false)
        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
    }


    fun onDragEnd(lvl: RobuzzleLevel) = runBlocking {
        switch(lvl)

        setDragStart(false)
        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
    }

    private fun switch(lvl: RobuzzleLevel) {
        elements.itemSelectedPosition?.let { _selectedP ->
            elements.itemUnder?.let { _underD ->
                elements.itemUnderPosition?.let { _underP ->
                    elements.itemSelected?.let { _selectedD ->
                        lvl.replaceInstruction(_selectedP, _underD)
                        lvl.replaceInstruction(_underP, _selectedD)
                    } ?: errorLog("ERROR", "dragAndDropState.elements.itemSelected == null")
                } ?: errorLog("ERROR", "dragAndDropState.elements.itemUnderPosition == null")
            } ?: errorLog("ERROR", "dragAndDropState.elements.itemUnder == null")
        } ?: errorLog("ERROR", "dragAndDropState.elements.itemSelected == null")
    }

    private fun switchCases(list: List<FunctionInstructions>) {
        elements.itemSelectedPosition?.let { _selectedP ->
            elements.itemUnder?.let { _underD ->
                elements.itemUnderPosition?.let { _underP ->
                    errorLog("list", "$list")

                    elements.itemSelected?.let { _selectedD ->
                        list[_selectedP.line].instructions =
                            list[_selectedP.line].instructions.replaceRange(_selectedP.column.._selectedP.column, _underD.instructions)
                        list[_selectedP.line].colors =
                            list[_selectedP.line].colors.replaceRange(_selectedP.column.._selectedP.column, _underD.colors)

                        list[_underP.line].instructions =
                            list[_underP.line].instructions.replaceRange(_underP.column.._underP.column, _selectedD.instructions)
                        list[_underP.line].colors =
                            list[_underP.line].colors.replaceRange(_underP.column.._underP.column, _selectedD.colors)
                    }
                    verbalLog("list", "$list")
                }
            }
        }
    }
}