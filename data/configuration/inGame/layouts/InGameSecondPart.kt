package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Rect
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level

object InGameSecondPart {
    var size = Sizes
    var ratios = Ratios

    object Ratios {
        const val height: Float = 6F

        const val actionRowFirstPart = 1F
        const val actionRowSecondPart = 9F
        const val actionRowHeight = 1F
        const val actionRowSurronderHeight = 0.15F
        const val actionRowSurronderBlackLineHeight = 0.05F
        const val actionRowSurronderEmptyLineHeight = 0.08F
        const val actionRowBorder = 0.08F

        const val functionsRowHeight = 5F
        const val maxFunctionWidth = 0.8F
        const val functionCasePadding = 0.1F
        const val biggerFunctionCaseRatio = 0.3F
        const val selectionCaseHalo = 0.15F

    }

    object Sizes {
        var width: Int = 0
        var height: Int = 0
        var functionCase: Int = 0
        var functionCaseIcon: Int = 0
        var functionCasePadding: Int = 0
        var bigFunctionCase: Int = 0
        var halfFunctionCase: Int = 0
        var oneThirdFunctionCase: Int = 0
        var twoThirdFunctionCase: Int = 0

        var actionRowCase: Int = 0
        var actionRowIcon: Int = 0
        var actionRowCaseBorder: Int = 0
    }

    private var functionsNumber: Int = 0
    private var maxCasesNumber: Int = 0
    private var maximumCaseNumberActionRow = 10
    private var minimumCaseNumberPerRow = 7


    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = (widthFull).toInt()
        size.height = (heightFull * Ratios.height).toInt()

        initFunctionInstructionsRows(level, density)
        initActionRow(density)
    }
    fun initActionRow(density: Float) {
        size.actionRowCase = ((size.width / maximumCaseNumberActionRow) / density).toInt()
        size.actionRowCaseBorder = ( size.actionRowCase * ratios.actionRowBorder ).toInt()
        size.actionRowIcon = size.actionRowCase
    }
    fun initFunctionInstructionsRows(level: Level, density: Float) {
        functionsNumber = level.funInstructionsList.size
        maxCasesNumber = minimumCaseNumberPerRow
        level.funInstructionsList.forEach {
            if (it.instructions.length > maxCasesNumber)
                maxCasesNumber = it.instructions.length
        }


        //todo : take the number of row in the calcul
//        size.functionCase = ((size.width * ratios.maxFunctionWidth) / maxCasesNumber).toInt()
        size.functionCase = (((size.width * ratios.maxFunctionWidth) / maxCasesNumber) / density).toInt()
        size.functionCaseIcon = size.functionCase
        size.bigFunctionCase = (size.functionCase * ( 1F + ratios.biggerFunctionCaseRatio )).toInt()
        size.halfFunctionCase = (size.functionCase / 2F).toInt()
        size.oneThirdFunctionCase = (size.functionCase * (1F/3F)).toInt()
        size.twoThirdFunctionCase = (size.functionCase * (2F/3F)).toInt()
        size.functionCasePadding = (size.functionCase * ratios.functionCasePadding).toInt()

        infoLog("InGameSecondPart", "initiate")
        infoLog("width", "${size.width}")
        infoLog("height", "${size.height}")

        infoLog("functions Number", "$functionsNumber")
        infoLog("max Cases Number", "$maxCasesNumber")
        infoLog("function case", "${size.functionCase}")
        infoLog("functionCasePadding", "${size.functionCasePadding}")
        infoLog("bigFuntionCase", "${size.bigFunctionCase}")
        infoLog("halfFuncitonCase", "${size.halfFunctionCase}")
        infoLog("1/3functionCase", "${size.oneThirdFunctionCase}")
        infoLog("2/3functionCase", "${size.twoThirdFunctionCase}")
        infoLog("function case max num", "${maximumCaseNumberActionRow}")
        infoLog("actionRowCase", "${size.actionRowCase}")
    }
}
