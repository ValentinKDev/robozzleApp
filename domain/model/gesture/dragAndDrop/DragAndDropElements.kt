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
import kotlinx.coroutines.Job

typealias DragAndDropRow = Pair<Rect, DragAndDropCaseList>
typealias DragAndDropCaseList = MutableList<Rect>

class DragAndDropElements {
    var parentOffset: Offset = Offset.Zero
    fun  setDraggableParentOffset(layoutCoordinates: LayoutCoordinates) {
        if (parentOffset == Offset.Zero) {
            parentOffset = layoutCoordinates.boundsInRoot().topLeft
        }
    }

    var itemSelectedPosition: Position? = null
    var itemSelected: FunctionInstructions? = null

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

//        infoLog("infos", "${case.isValid()} && ${allreadyIn.containsNot(item)}")
        if (allreadyIn.containsNot(item) && case.isValid()) {
//            verbalLog("add", "\t${case}")
            casesList += case
            allreadyIn.add(item)
            verbalLog("add", "\tdroppable case $rowIndex, $columnIndex")
        }
    }

    fun findSelectedItem(offset: Offset, list: List<FunctionInstructions>) {
        var found = false
        rowsList.forEachIndexed { rowIndex, row ->
            if (row.first.contains(offset)) {
                row.second.forEachIndexed { columIndex, case ->
                    if (case.contains(offset)) {
                        itemSelectedPosition = Position(rowIndex, columIndex)
                        itemSelectedPosition?.let {
                            itemSelected = FunctionInstructions(
                                instructions = list[it.line].instructions[it.column].toString(),
                                colors = list[it.line].colors[it.column].toString()
                            )
                        }
                        verbalLog("itemSelected Postion", "$itemSelectedPosition")
                        verbalLog("itemSelected", "$itemSelected")
                        found = true
                    }
                }
            }
        }
        if (found) verbalLog("item", "founded offSet $offset")
        else {
            errorLog("error", "not found, offSet $offset")
            errorLog("conditions", "${rowsList[0].first.contains(offset)} ${rowsList[0].second[0].contains(offset)}${rowsList[0].second[1].contains(offset)}${rowsList[0].second[2].contains(offset)}${rowsList[0].second[3].contains(offset)}")
        }

    }

    fun onHoldItem(list: MutableList<FunctionInstructions>): MutableList<FunctionInstructions> {
        val ret: MutableList<FunctionInstructions> = list.map { it.copy() }.toMutableList()
        itemSelectedPosition?.let {
            val line = it.line
            val column = it.column
            ret[line].instructions = ret[line].instructions.replaceRange(column..column, ".")
//            ret[line].colors = ret[line].colors.replaceRange(column..column+1, "g")
            ret[line].colors = ret[line].colors.replaceRange(column..column, "g")
            errorLog("${ret}", ".")
            errorLog("${list}", ".")
        }
        return ret
    }
    private fun Rect.isValid(): Boolean = !this.isEmpty && !this.center.isUnspecified
    fun getColor(list: List<FunctionInstructions>): String? = itemSelected?.colors
    fun getInstruction(list: List<FunctionInstructions>): Char? = itemSelected?.instructions?.get(0)
//        list[itemSelectedPosition.line].colors[itemSelectedPosition.column].toString()
//    } catch (e: ArrayIndexOutOfBoundsException) {
//        errorLog("ERROR", "${e.message}")
//        null
//    }
//    fun getInstruction(list: List<FunctionInstructions>): Char? = try {
//        list[itemSelectedPosition.line].instructions[itemSelectedPosition.column]
//    } catch (e: ArrayIndexOutOfBoundsException) {
//        errorLog("ERROR", "${e.message}")
//        null
//    }

}