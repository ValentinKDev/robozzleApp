package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.utils.extensions.backColor
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, dragAndDropVM: DragAndDropViewModel = viewModel(), itemList: List<String> = itemListval) {
//    infoLog("launch", "CreatorScreen()")

    val offsetTouch by dragAndDropVM.touchOffSet.collectAsState()
//    infoLog("offsetTouch", "$offsetTouch")
    val offsetTouchStart by dragAndDropVM.touchOffsetStart.collectAsState()
    infoLog("offsetTouchStart", "$offsetTouchStart")
    val dragStart by dragAndDropVM.dragStart.collectAsState()
    infoLog("dragStart", "$dragStart")
    val selectedItem by dragAndDropVM.selectedItem.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .height(600.dp)
                .width(350.dp)
                .background(gray9)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change, _offset ->
                            infoLog("onDrag", "position ${change.position}")
                            dragAndDropVM.setTouchOffset(change.position)
                        },
                        onDragStart = { _offset ->
                            infoLog("onDragStart", "started")
                            dragAndDropVM.setTouchOffsetStart(_offset)
                            dragAndDropVM.setTouchOffset(_offset)
                            dragAndDropVM.findItemSelected(_offset)
                            dragAndDropVM.setDragStart(true)

                            infoLog("onDrag", "_offsetStart $_offset")
                            infoLog(
                                "onDrag",
                                "offset ${dragAndDropVM.touchOffsetStart.value} ${dragAndDropVM.dragStart.value}"
                            )
                        },
                        onDragEnd = {
                            dragAndDropVM.setTouchOffsetStart(Offset(0F, 0F))
                            dragAndDropVM.setDragStart(false)
                            errorLog("onDragEnd", "offset ${dragAndDropVM.touchOffsetStart.value} ")
                        },
                        onDragCancel = {
                            dragAndDropVM.setTouchOffsetStart(Offset(0F, 0F))
                            dragAndDropVM.setDragStart(false)
                            errorLog(
                                "onDragCanceled", "offset ${dragAndDropVM.touchOffsetStart.value}"
                            )
                        }
                    )
                }
        ) {
            VerticalSpace(height = 50)
            val rowIndex = 0
            Column( Modifier
                    .wrapContentSize()
                    .background(grayDark5)
                    .onGloballyPositioned {
                        dragAndDropVM.addDroppableRow(rowIndex, it)
                    }
            ) {
                itemList.forEachIndexed { _columnIndex, _str ->
                    var empty = ""
                    for (i in _str.indices) empty += " "
                    val item = if (dragStart && selectedItem.equals(Position(rowIndex, _columnIndex))) empty  else _str
                    Handle(0, _columnIndex, item, dragAndDropVM)
                }
            }
            VerticalSpace(height = 50)
        }
        if (dragStart) {
            Box(Modifier.offset {
                    IntOffset(offsetTouch.x.toInt(), offsetTouch.y.toInt())
                }
            ) {

                Text(text = itemList[selectedItem.column], color = whiteDark4)
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Handle(rowIndex: Int, columnIndex: Int, s: String, dragAndDropVM: DragAndDropViewModel) {
    var selectColor: Color = remember { gray7 }

    Box(
        Modifier
            .wrapContentSize()
            .background(selectColor)
            .onGloballyPositioned {
                dragAndDropVM.addDroppableCase(
                    rowIndex = rowIndex,
                    columnIndex = columnIndex,
                    it
                )
            }
    ) {
//        Box( Modifier .height(5.dp) .width(5.dp))
        Text(text = s, color = whiteDark4)
    }
}

class DragAndDropViewModel(): ViewModel() {

    private val _selectedItem = MutableStateFlow<Position>(Position(-21, -42))
    val selectedItem: StateFlow<Position> = _selectedItem.asStateFlow()


    var listDroppaableRowsAndCases: MutableList<Pair<Rect, MutableList<Rect>>> = mutableListOf()
//    var listDroppaableRowsAndCases: MutableList<Pair<LayoutCoordinates, MutableList<LayoutCoordinates>>> = mutableListOf()
    fun addDroppableRow(row: Int, layoutCoordinates: LayoutCoordinates) {
        val rect = layoutCoordinates.boundsInRoot()
//        layoutCoordinates.boundsInRoot().

        if (listDroppaableRowsAndCases.lastIndex < row || (row == 0 && listDroppaableRowsAndCases.size == 0) ) {
            listDroppaableRowsAndCases.add(Pair(rect, mutableListOf()))
//            listDroppaableRowsAndCases.add(Pair(layoutCoordinates, mutableListOf()))
            verbalLog("add", "droppable row")
        }
    }
    fun addDroppableCase(rowIndex: Int, columnIndex: Int, layoutCoordinates: LayoutCoordinates) {
        val rect = layoutCoordinates.boundsInRoot()

        if (listDroppaableRowsAndCases[rowIndex].second.size < columnIndex ) {
            listDroppaableRowsAndCases[rowIndex].second += rect
//            listDroppaableRowsAndCases[rowIndex].second += layoutCoordinates
            verbalLog("add", "\tdroppable case $columnIndex")
        }
    }

    fun findItemSelected(offset: Offset) {
        listDroppaableRowsAndCases.forEachIndexed { rowIndex, row ->
//            if (row.first.myContain(offset))
//                errorLog("select", "$rowIndex")
            if (row.first.contains(offset)) {
                row.second.forEachIndexed { columIndex, case ->
                    errorLog("select", "$rowIndex")
                    if (case.contains(offset)) {
                        errorLog("select", "$rowIndex $columIndex")
                        _selectedItem.value = Position(rowIndex, columIndex)
                    }
                }
            }
        }
    }
//    fun Rect.myContain(offset: Offset): Boolean {
//        return ( this.topLeft.x < offset.x
//                && this.bottomLeft.x < offset.x
//                && this.topRight.x > offset.x
//                && this.bottomRight.x > offset.x
//
//                && this.topLeft.y < offset.y
//                && this.topRight.y < offset.y
//                && this.top
//                )
//    }

//    fun isOffsetInsideLayout(offset: Offset, layoutCoordinates: LayoutCoordinates) {
//    }

    private val _touchOffsetStart = MutableStateFlow(Offset(0F, 0F))
    val touchOffsetStart: StateFlow<Offset> = _touchOffsetStart.asStateFlow()
    fun setTouchOffsetStart(offset: Offset) {
        _touchOffsetStart.value = offset
    }


    private val _touchOffSet = MutableStateFlow<Offset>(Offset(0F,0F))
    val touchOffSet: StateFlow<Offset> = _touchOffSet.asStateFlow()
    fun setTouchOffset(offset: Offset) {
        _touchOffSet.value = offset
    }

    private val _dragStart = MutableStateFlow<Boolean>(false)
    val dragStart: StateFlow<Boolean> = _dragStart.asStateFlow()
    fun setDragStart(state: Boolean) {
        if (state == false ) errorLog("dragStart", "set to false")
        _dragStart.value = state
    }

}

//fun <K,V> MutableMap<K, V>.adding(key: K, element: V) {
//    val ret: MutableMap<>
//    this.forEach()
//}

fun Modifier.myDraggable(dragAndDropVM: DragAndDropViewModel) {
    this.then(
        Modifier.pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, _offset ->
                        infoLog("onDrag", "position ${change.position}")
                    },
                    onDragStart = { _offset ->
                        infoLog("onDragStart", "started")
                        dragAndDropVM.setDragStart(true)
                        dragAndDropVM.setTouchOffsetStart(_offset)

                        infoLog("onDrag", "_offsetStart $_offset")
                        infoLog(
                            "onDrag",
                            "offset ${dragAndDropVM.touchOffsetStart.value} ${dragAndDropVM.dragStart.value}"
                        )
                    },
                    onDragEnd = {
                        dragAndDropVM.setTouchOffsetStart(Offset(0F, 0F))
                        dragAndDropVM.setDragStart(false)
                        errorLog("onDragEnd", "offset ${dragAndDropVM.touchOffsetStart.value} ")
                    },
                    onDragCancel = {
                        dragAndDropVM.setTouchOffsetStart(Offset(0F, 0F))
                        dragAndDropVM.setDragStart(false)
                        errorLog( "onDragCanceled", "offset ${dragAndDropVM.touchOffsetStart.value}"
                        )
                    }
                )
            }
    )
}

val itemListval = listOf(
    "Item 1",
    "Item 2",
    "Item 3",
    "Item 4",
    "Item 5",
    "Item 6",
    "Item 7",
    "Item 8",
    "Item 9",
    "Item 10",
    "Item 11",
    "Item 12",
    "Item 13",
    "Item 14",
    "Item 15",
    "Item 16",
    "Item 17",
    "Item 18",
    "Item 19",
    "Item 20"
).toMutableStateList()

@Composable
fun Neon() {
    Canvas(
        modifier = Modifier.size(100.dp),
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(green0, green9)
            ),
            radius = (canvasWidth / 2) - 5,
            center = center,
            style = Stroke(width = canvasWidth * 0.075f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(green0, green9)
            ),
            radius = canvasWidth / 2,
            center = center,
            style = Stroke(width = canvasWidth * 0.075f)
        )
    }
}

val mapTest4 = listOf(
/*                               1 1 1 1 1 1      */
/*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
/*0*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
/*1*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
     ". . . . . . . . . . . . . . . .".replace(" ", "") ,
/*3*/". . . R G G G R . B B B . . . .".replace(" ", "") , /*2*/
     ". . R G G R . G . B B B B . . .".replace(" ", "") ,
/*5*/". . G G . G . G . B . B B . . .".replace(" ", "") , /*4*/
     ". . G R G R . G . B B B B . . .".replace(" ", "") ,
/*7*/". . G . . . . G . . . . B . . .".replace(" ", "") , /*6*/
     ". . R . . . . B B B B B B . . .".replace(" ", "") ,
/*9*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*8*/
     ". . . . . . . . . . . . . . . .".replace(" ", "") ,
/*11*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*10*/
)


val mapTest3 = listOf(
/*                               1 1 1 1 1 1      */
/*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
/*0*/". R . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
    ". G B B B G . . . . . . . . . .".replace(" ", "") ,
/*2*/". G B B B B B G . . . . . . . .".replace(" ", "") , /*2*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*4*/". G B G . . . . . . . . . . . .".replace(" ", "") , /*4*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*6*/". G B B B B B B B B G . . . . .".replace(" ", "") , /*6*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*8*/". G B B B B B B B B B B B B B .".replace(" ", "") , /*8*/
     ". G B B B B B B B B B B G . . .".replace(" ", "") ,
/*10*/". G B B B B B B B G . . . . . .".replace(" ", "") , /*10*/
     ". G B B B B B B B B B G . . . .".replace(" ", "") ,
)
val mapTest2 = listOf(
    /*                               1 1 1 1 1 1      */
    /*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
   /*0*/". . . . . . . . . . . R R R . G".replace(" ", "") , /*0*/
        ". R G G G R . . . . . R R R B G".replace(" ", "") ,
   /*2*/". R G B G R G R G R . R R R B G".replace(" ", "") , /*2*/
        ". R G B R B B B B . . . B B B G".replace(" ", "") ,
   /*4*/". R G B B B B B B . . . B B B G".replace(" ", "") , /*4*/
        ". R G B G B B B B R B B B B B G".replace(" ", "") ,
   /*6*/". R R B R B B B B G B . . . B G".replace(" ", "") , /*6*/
        ". B B B G R G R G R B . . . B G".replace(" ", "") ,
   /*8*/". B B B B B B B B B B B B B B G".replace(" ", "") , /*8*/
        "B B B B B B B B B G B G B G B G".replace(" ", "") ,
   /*10*/"B R R R R R R R R R B R R R B R".replace(" ", "") , /*10*/
        ". . . . . . . . . . . . . . . .".replace(" ", "") ,
)
val mapTest1 = listOf(
/*                               1 1 1 1 1 1      */
/*            0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
        /*0*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
        /*2*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*2*/
             ". . . . . B . . B B B . . . . .".replace(" ", "") ,
        /*4*/". . . . B R B . B B B B . . . .".replace(" ", "") , /*4*/
             ". . . . B B R . B B B B . . . .".replace(" ", "") ,
        /*6*/". . . . B B R B B B R B . . . .".replace(" ", "") , /*6*/
             ". . . . B R B . B B B B . . . .".replace(" ", "") ,
        /*8*/". . . . . B R . B B B . . . . .".replace(" ", "") , /*8*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
       /*10*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*10*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
)
val mapTest5 = listOf(
/*               0123456789012345     */
"................", /*0*/
".......RR.......",
".......BB.......", /*2*/
".......GG.......",
".......RR.......", /*4*/
".......BR.......",
".......RR.......", /*6*/
".......GG.......",
".......BB.......", /*8*/
".......RR.......",
"................", /*10*/
)