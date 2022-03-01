package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.utils.spacer.HorizontalSpace
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace


@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel()) {
    infoLog("launch", "CreatorScreen()")

    Row {
        val mapCleaner = MapCleaner()
        val mapClean1 = mapCleaner clean mapTest1
        val mapClean2 = mapCleaner clean mapTest2
        val mapClean3 = mapCleaner clean mapTest3
        val mapClean4 = mapCleaner clean mapTest4
        val mapClean5 = mapCleaner clean mapTest5
        Column(
            Modifier
                .fillMaxHeight()
                .width(100.dp)
        ) {
            MapView(widthInt = 100, mapParam = mapClean1)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapClean2)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapClean3)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapClean4)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapClean5)
        }
        HorizontalSpace(widthDp = 50)
        Column(
            Modifier
                .height(600.dp)
                .width(100.dp)
        ) {
            MapView(widthInt = 100, mapParam = mapTest1)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapTest2)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapTest3)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapTest4)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, mapParam = mapTest5)
        }
    }
    infoLog("horizontal empty ", "")
}

class MapCleaner() {
    infix fun clean(map: List<String>): List<String> {
        var ret: MutableList<String> = map.toMutableList()

        val listEmptyLines: List<Int> = getEmptyLines(map)
        val listEmptyColumns: List<Int> = getEmptyColumns(map)
        infoLog("emptyLines", "$listEmptyLines")
        infoLog("emptyColumns", "$listEmptyColumns")

        if (listEmptyLines.isNotEmpty()) {
            val temp = ret.filterIndexed { indexLine, _ -> !listEmptyLines.contains(indexLine) }.toMutableList()
            ret = temp
        }

        if (listEmptyColumns.isNotEmpty()) {
            ret.forEachIndexed { indexLine, line ->
                ret[indexLine] = line.filterIndexed { indexColumn, _ -> !listEmptyColumns.contains(indexColumn) }
            }
        }

        return ret
    }

    private fun getEmptyLines(map: List<String>): MutableList<Int> {
        val listEmptyLines = mutableListOf<Int>()
        map.forEachIndexed { indexLine, line ->
            if (isLineEmpty(line))
                listEmptyLines.add(indexLine)
        }
        return listEmptyLines
    }

    private fun isLineEmpty(line: String): Boolean {
        var ret = true
        line.forEach {
            if (it != '.') ret = false
        }
        return ret
    }

    private fun getEmptyColumns(map: List<String>): MutableList<Int> {
        val listEmptyColumns = mutableListOf<Int>()
        map.first().forEachIndexed { _indexColumn, _c ->
            if ( isColumnEmpty(map, _indexColumn) )
                listEmptyColumns.add(_indexColumn)
        }
        return listEmptyColumns
    }

    private fun isColumnEmpty(map: List<String>, indexColumn: Int): Boolean {
        var ret = true

        map.forEachIndexed { indexLine, line ->
            if (line[indexColumn] != '.') ret = false
        }

        return ret
    }
}

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

class MyDrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): View(context, attrs, defStyle) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Draw here
//        drawCircle()
    }
}

class TestShared(): ViewModel() {

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