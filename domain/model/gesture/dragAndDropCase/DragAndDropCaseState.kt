package com.mobilegame.robozzle.domain.model.gesture.dragAndDropCase

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.layout.inGame.elements.Trash
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.TutoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KFunction0

class DragAndDropCaseState(val trash: Trash) {
    val elements = DragAndDropCaseElements()

    private val _dragStart = MutableStateFlow<Boolean>(false)
    val dragStart: StateFlow<Boolean> = _dragStart.asStateFlow()
    fun setDragStart(value: Boolean) {
        _dragStart.value = value
    }

    var touchOffSetStart: Offset? = null
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
        setPointOffset()
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

    private val _pointerOffset = MutableStateFlow(Offset.Zero)
    val pointerOffset: StateFlow<Offset> = _pointerOffset.asStateFlow()

//    var pointerOffset = Offset.Zero
    fun setPointOffset() {
        val elementHalfHeight: Float = elements.itemSelectedHalfHeight ?: 0F
        val elementTwoThirdHeight: Float = elements.itemSelectedTwoThirdHeight ?: 0F
        val elementOneThirdHeight: Float = elements.itemSelectedOneThirdHeight ?: 0F

//    pointerOffset = Offset(
        _pointerOffset.value = Offset(
            x = touchOffSet.value.x - elementOneThirdHeight,
            y = touchOffSet.value.y - elementHalfHeight - elementTwoThirdHeight,
        )
    }

    private fun upDateTrashState() {
//        elements.setLeftTrashHighlightTo(trash.leftContains(pointerOffset) )
//        elements.setRightTrashHighlightTo(trash.rightContains(pointerOffset))
        elements.setLeftTrashHighlightTo(trash.leftContains(pointerOffset.value) )
        elements.setRightTrashHighlightTo(trash.rightContains(pointerOffset.value))
    }

    fun isSwitchingCase(row: Int, column: Int): Boolean {
        return (dragStart.value.not() && ((elements.itemUnderPosition?.Match(Position(row, column)) ?: false) || (elements.itemSelectedPosition?.Match(Position(row, column) ) ?: false )) )
    }

    fun onDrag(pointerInputChange: PointerInputChange, list: List<FunctionInstructions>) = runBlocking(Dispatchers.IO) {
//        infoLog("DragAndDropCaseState::onDrag", "position ${pointerInputChange.position}")
        setOffsets(pointerInputChange.position)
        if (trash.displayTrash) upDateTrashState()
//        elements.findItemUnderItem(pointerOffset, list)
        elements.findItemUnderItem(pointerOffset.value, list)
    }

    fun onDragStart(offset: Offset, list: List<FunctionInstructions>) = runBlocking(Dispatchers.IO) {
//        infoLog("vm.dragstrt", "${vm.dragAndDropCase.dragStart.value}")
        verbalLog("DragAndDropVM::onDragStart", "start")
        setTouchOffSetStart(offset)
        touchOffSetStart ?: setTouchOffSetStart(offset)
        setOffsets(offset)
        if (elements.findSelectedItem(touchOffSetStart ?: Offset.Zero, list)) {
            setDragStart(true)
        }
    }

    fun onDragCancel() = runBlocking {
        setDragStart(false)
        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
    }


    fun onDragEnd(vm: GameDataViewModel, tutoVM: TutoViewModel? = null) = runBlocking {
        switch(vm, tutoVM)

        setDragStart(false)
        elements.itemSelectedPosition = null
        elements.itemSelected = null
        touchOffSetStart = null
    }

    fun switch(vm: GameDataViewModel, tutoVM: TutoViewModel?) {
        if (trash.displayTrash && trash.contains(pointerOffset.value)) {
            elements.itemSelectedPosition?.let { _selectedP ->
                vm.replaceInstruction(_selectedP, FunctionInstruction.empty)
                tutoVM?.let { tutoVM.setTutoTo(Tuto.ClickOnThirdInstructionCase) }
            }
        }
        else {
            elements.itemSelectedPosition?.let { _selectedP ->
                elements.itemUnder?.let { _underD ->
                    elements.itemUnderPosition?.let { _underP ->
                        elements.itemSelected?.let { _selectedD ->
                            tutoVM?.let {
                                if (_underP.Match(Position(0,6)) || _underP.Match(Position(0,2))) {
                                    vm.switchInstruction(_underP, _underD, _selectedP, _selectedD)
                                    tutoVM.nextTuto()
                                }
                            } ?: vm.switchInstruction(_underP, _underD, _selectedP, _selectedD)
                        } ?: errorLog("ERROR", "dragAndDropState.elements.itemSelected == null")
                    } ?: errorLog("ERROR", "dragAndDropState.elements.itemUnderPosition == null")
                } ?: errorLog("ERROR", "dragAndDropState.elements.itemUnder == null")
            } ?: errorLog("ERROR", "dragAndDropState.elements.itemSelected == null")
        }
    }
}