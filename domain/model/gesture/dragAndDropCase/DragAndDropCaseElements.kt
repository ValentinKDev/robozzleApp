package com.mobilegame.robozzle.domain.model.gesture.dragAndDropCase

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import com.mobilegame.robozzle.utils.Extensions.containsNot
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.utils.Extensions.getSafe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias DragAndDropRow = Pair<Rect, DragAndDropCaseList>
typealias DragAndDropCaseList = MutableList<Rect>

infix fun Boolean.not(check: Boolean) = this != check

class DragAndDropCaseElements {
    private val _leftTrashHighlight = MutableStateFlow<Boolean>(false)
    val leftTrashHighLight: StateFlow<Boolean> = _leftTrashHighlight.asStateFlow()
    fun setLeftTrashHighlightTo(state : Boolean) {
        if (leftTrashHighLight.value != state) _leftTrashHighlight.value = state
    }
    private val _rightTrashHighlight = MutableStateFlow<Boolean>(false)
    val rightTrashHighlight: StateFlow<Boolean> = _rightTrashHighlight.asStateFlow()
    fun setRightTrashHighlightTo(state: Boolean) {
        if (rightTrashHighlight.value != state) _rightTrashHighlight.value = state
    }

    var parentOffset: Offset = Offset.Zero
    fun  setDraggableParentOffset(layoutCoordinates: LayoutCoordinates) {
        if (parentOffset == Offset.Zero) {
            parentOffset = layoutCoordinates.boundsInRoot().topLeft
        }
    }

    var itemSelectedPosition: Position? = null
    var itemSelected: FunctionInstruction? = null
    var itemSelectedSize: Size? = null
    var itemSelectedHalfHeight: Float? = null
    var itemSelectedOneThirdHeight: Float? = null
    var itemSelectedTwoThirdHeight: Float? = null

    var itemUnderPosition: Position? = null
    var itemUnder: FunctionInstruction? = null
    private val _itemUnderCorner = MutableStateFlow<Offset?>(null)
    val itemUnderCorner: StateFlow<Offset?> = _itemUnderCorner.asStateFlow()
    var itemUnderTopLeftOffset: Offset? = null
    fun clearItemUnder() {
        errorLog("clear", "item Under")
        itemUnderPosition = null
        itemUnder = null
        itemUnderTopLeftOffset = null
        _itemUnderCorner.value = null
    }

    var rowsList: MutableList<DragAndDropRow> = mutableListOf()
    private var alreadyIn: MutableList<Position> = mutableListOf()

    fun addDroppableRow(row: Int, layoutCoordinates: LayoutCoordinates) {
        val rect = layoutCoordinates.boundsInRoot()
        if (rect.isValid() && (rowsList.lastIndex < row || (row == 0 && rowsList.size == 0))) {
            rowsList.add(Pair(rect, mutableListOf()))
            verbalLog("add", "droppable row")
        }
    }
    //todo : oh god this is ugly
    fun addDroppableCase(rowIndex: Int, columnIndex: Int, layoutCoordinates: LayoutCoordinates) {
        val item = Position(rowIndex, columnIndex)
        val case = layoutCoordinates.boundsInRoot()
        val casesList = rowsList[rowIndex].second

        if (alreadyIn.containsNot(item) && case.isValid()) {
            casesList += case
            alreadyIn.add(item)
            verbalLog("add", "\tdroppable case $rowIndex, $columnIndex")
        }
    }

    fun findSelectedItem(offset: Offset, list: List<FunctionInstructions>): Boolean {
        var found = false
        rowsList.forEachIndexed { rowIndex, row ->
            if (row.first.contains(offset)) {
                row.second.forEachIndexed { columnIndex, case ->
                    if (case.contains(offset)) {
                        itemSelectedPosition = Position(rowIndex, columnIndex)
                        itemSelectedPosition?.let {
                            itemSelected = FunctionInstruction(
                                instruction = list[it.line].instructions[it.column],
                                color = list[it.line].colors[it.column]
                            )
                            itemSelectedSize = case.size
                            itemSelectedHalfHeight = case.height / 2F
                            itemSelectedOneThirdHeight = case.height / 3F
                            itemSelectedTwoThirdHeight = (2F * case.height) / 3F
                        }
                        verbalLog("itemSelected Postion", "$itemSelectedPosition")
                        verbalLog("itemSelected", "$itemSelected")
                        verbalLog("itemSelectedSize", "$itemSelectedSize")
                        found = true
                    }
                }
            }
        }
        if (found) verbalLog("DragAndDropCaseElement::findSelectedItem", "founded offSet $offset")
        else {
            errorLog("DragAndDropCaseElement::findSelectedItem", "not found, offSet $offset")
        }
        return found
    }

    fun findItemUnderItem(offset: Offset, list: List<FunctionInstructions>) {
        var found = false
        var visible = false
        rowsList.forEachIndexed { rowIndex, row ->
//            if (row.first.contains(offset)) {
                row.second.forEachIndexed { columnIndex, case ->
                    if (case.contains(offset)) {
                        visible = true
                        val position = Position(rowIndex, columnIndex)
                        if (itemUnderPosition != position) {
                            infoLog("condition same ", "$itemUnderPosition $position")
                            itemUnderPosition = position
                            itemUnderPosition?.let {
                                itemUnder = FunctionInstruction(
                                    instruction = list[it.line].instructions[it.column],
                                    color = list[it.line].colors[it.column]
                                )
                                if (it != itemSelectedPosition)  {
//                                    setUnderVisibleTo(true)
                                    itemUnderTopLeftOffset = case.topLeft
                                    _itemUnderCorner.value = case.topLeft
                                }
                                found = true
                                verbalLog("itemUnder Postion", "$itemUnderPosition")
                                verbalLog("itemUnder", "$itemUnder")
                            }
                        }
                    }
                }
            if (visible not true)
                clearItemUnder()
        }
    }
    infix fun not(uoi: Boolean): Boolean = uoi.not()

    fun onHoldItem(list: MutableList<FunctionInstructions>): MutableList<FunctionInstructions> {
        val ret: MutableList<FunctionInstructions> = list.map { it.copy() }.toMutableList()
        itemSelectedPosition?.let {
            val line = it.line
            val column = it.column
            ret[line].instructions = ret[line].instructions.replaceRange(column..column, ".")
            ret[line].colors = ret[line].colors.replaceRange(column..column, "g")
            errorLog("${ret}", ".")
            errorLog("${list}", ".")
        }
        return ret
    }

    private fun Rect.isValid(): Boolean = !this.isEmpty && !this.center.isUnspecified
    fun getColor(): Char? = itemSelected?.color
    fun getInstruction(): Char? = itemSelected?.instruction
}