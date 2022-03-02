package com.mobilegame.robozzle.presentation.ui.utils

import com.mobilegame.robozzle.analyse.infoLog

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
