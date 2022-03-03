package com.mobilegame.robozzle.domain.model.gesture.dragAndDrop

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import com.mobilegame.robozzle.Extensions.containsNot
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

typealias DragAndDropCaseList = MutableList<Rect>
typealias DragAndDropRow = Pair<Rect, DragAndDropCaseList>

class DragAndDropElements {
    var parentOffset: Offset = Offset.Zero
    fun  setDraggableParentOffset(layoutCoordinates: LayoutCoordinates) {
        if (parentOffset == Offset.Zero) {
            parentOffset = layoutCoordinates.boundsInRoot().topLeft
        }
    }

    var selectedItem: Position = Position(-21, -42)

    var rowsList: MutableList<DragAndDropRow> = mutableListOf()
    private var allreadyIn: MutableList<Position> = mutableListOf()

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
        val row = rowsList[rowIndex].first
        val casesList = rowsList[rowIndex].second

        infoLog("infos", "${case.isValid()} && ${allreadyIn.containsNot(item)}")
        if (allreadyIn.containsNot(item) && case.isValid()) {
            verbalLog("add", "\t${case}")
            casesList += case
            allreadyIn.add(item)
            verbalLog("add", "\tdroppable case $rowIndex, $columnIndex")
        }
    }

    fun findSelectedItem(offset: Offset) {
        infoLog("find item selected in", "${rowsList}")
        infoLog("find item selected with", "${offset}")
        rowsList.forEachIndexed { rowIndex, row ->
            infoLog("$rowIndex", "${row.first}")
//            errorLog("${row.first} contains ${touchOffSet.value}", "${row.first.contains(touchOffSet.value)}")
//            if (row.first.contains(offset)) {
            if (row.first.contains(offset)) {
                errorLog("select", "$rowIndex")
                row.second.forEachIndexed { columIndex, case ->
                    errorLog("select", "$rowIndex $columIndex")
                    errorLog("select", "${case.topLeft} ${case.topRight} ${case.bottomLeft} ${case.bottomRight}")
                    errorLog("$case contains $offset", "${case.contains(offset)}")
//                    if (case.contains(offset)) {
                    if (case.contains(offset)) {
                        selectedItem = Position(rowIndex, columIndex)

//                        _selectedItem.value = Position(rowIndex, columIndex)
                    }
                }
            }
        }

    }
    private fun Rect.isValid(): Boolean = !this.isEmpty && !this.center.isUnspecified
    fun getColor(list: List<FunctionInstructions>): String? = try {
        list[selectedItem.line].colors[selectedItem.column].toString()
    } catch (e: ArrayIndexOutOfBoundsException) {
        errorLog("ERROR", "${e.message}")
        null
    }
    fun getInstruction(list: List<FunctionInstructions>): Char? = try {
        list[selectedItem.line].instructions[selectedItem.column]
    } catch (e: ArrayIndexOutOfBoundsException) {
        errorLog("ERROR", "${e.message}")
        null
    }

}