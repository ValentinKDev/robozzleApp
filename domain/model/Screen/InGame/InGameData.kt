package com.mobilegame.robozzle.domain.model.Screen.InGame

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.configuration.inGame.*
import com.mobilegame.robozzle.domain.model.data.store.ScreenDimensionsDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.utils.Dimensions

class InGameData {
    val text = InGameText
    val ratios = InGameRatios
    val colors = InGameColors

    var density: Float? = null
        set(value) { if (field == null) field = value }
    var fistPartWidth: Int? = null
        set(value) { if (field == null) field = value }
    var fistPartHeight: Int? = null
        set(value) { if (field == null) field = value }
    var thirdPartWidth: Int? = null
        set(value) { if (field == null) field = value }
    var thirdPartHeight: Int? = null
        set(value) { if (field == null) field = value }

    var secondPartWidth: Int? = null
        set(value) { if (field == null) field = value }
    var secondPartHeight: Int? = null
        set(value) { if (field == null) field = value }
    var functionsNumber: Int? = null
        set(value) { if (field == null) field = value }
    private val minimumCaseNumberPerRow = 6
    var maxCasesNumber: Int = minimumCaseNumberPerRow
        set(value) { if (value > field) field = value }
    private var functionCaseSize: Int? = null
    fun getFunctionCaseSize(bigger: Boolean = false): Int = functionCaseSize ?: run {
        secondPartWidth?.let { _width ->
            functionCaseSize = ((_width * ratios.maxFunctionWidth) / maxCasesNumber).toInt()
            functionCaseSize?.let {
                density?. let { _densisty ->
                    val size = (((_width * ratios.maxFunctionWidth) / maxCasesNumber) / _densisty).toInt()
                    functionCaseSize = size
                }
            }
            errorLog("mapCaseNumber", "${maxCasesNumber}")
            errorLog("secondPartWidht", "${_width}")
            errorLog("funnctionCaseSize", "${functionCaseSize}")

            functionCaseSize?.let { if (bigger) (it * ratios.functionCaseBiggerRatio).toInt() else it}
//            functionCaseSize
        }
//        functionCaseSize ?: 0
        functionCaseSize?.let { if (bigger) (it * ratios.functionCaseBiggerRatio).toInt() else it} ?: 0
//        functionCaseSize ?: 0
    }
    var functionCasePadding: Int? = null
    fun getFunctionCasePadding(): Int = functionCasePadding ?: run {
        functionCaseSize?.let {
            functionCasePadding = (it * ratios.functionCasePadding).toInt()
            functionCasePadding
        } ?: 0
    }
}