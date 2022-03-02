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
    var maxCasesNumber: Int = null
        set(value) { if (field == null) field = value else if (value!! > field!!) field = value}
    private var functionCaseSize: Int? = null
    fun getFunctionCaseSize(): Int = functionCaseSize ?: run {
        secondPartWidth?.let { _width ->
            maxCasesNumber?.let { caseNumber ->
//                functionCaseSize = ((_width * ratios.maxFunctionWidth) / caseNumber).toInt()
                functionCaseSize = ((_width * ratios.maxFunctionWidth) / caseNumber).toInt()
                functionCaseSize?.let {
                    density?. let { _densisty ->
                        val size = (((_width * ratios.maxFunctionWidth) / caseNumber) / _densisty).toInt()
                        functionCaseSize = size
                    }
                }
                errorLog("mapCaseNumber", "${caseNumber}")
                errorLog("secondPartWidht", "${_width}")
                errorLog("funnctionCaseSize", "${functionCaseSize}")

                functionCaseSize
            }
//            functionCaseSize ?: 0
        }
//        functionCaseSize = ((secondPartWidth!! * ratios.maxFunctionWidth) / (maxCasesNumber!!)).toInt()
        functionCaseSize ?: 0
    }
    var functionCasePadding: Int? = null
    fun getFunctionCasePadding(): Int = functionCasePadding ?: run {
        functionCaseSize?.let {
            functionCasePadding = (it * ratios.functionCasePadding).toInt()
            functionCasePadding
        } ?: 0
    }
}