package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.toDp

const val maxNumberActionToDisplay = 10

object InGameSecondPart {
    var size = Sizes
    var ratios = Ratios

    object Ratios {
        const val height: Float = 6F

        const val actionRowFirstPart = 1.5F
        const val actionRowSecondPart = 9F
        const val actionRowStartPadding = 0.025F
        const val actionRowEndPadding = 0.005F
        const val actionRowHeight = 1F
        const val actionRowCaseBigger = 1.15F
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
        var width: Float = 0F
        var widthDp: Dp = Dp.Unspecified
        var height: Float = 0F
        var heightDp: Dp = Dp.Unspecified
        var functionCase: Int = 0
        var functionCaseIcon: Int = 0
        var functionCasePadding: Int = 0
        var bigFunctionCase: Int = 0
        var halfFunctionCase: Int = 0
        var oneThirdFunctionCase: Int = 0
        var twoThirdFunctionCase: Int = 0

        var actionRowCase: Int = 0
        var actionRowCaseBigger: Int = 0
        var actionRowIcon: Int = 0
        var actionRowCaseBorder: Int = 0
    }

    const val actionToDisplayNumber = 9
    private var functionsNumber: Int = 0
    private var maxCasesNumber: Int = 0
    private var maximumCaseNumberActionRow = 10
    private var minimumCaseNumberPerRow = 7


    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = widthFull.toFloat()
        size.widthDp = size.width.toDp(density)
        size.height = heightFull * Ratios.height
        size.heightDp = size.height.toDp(density)

        initFunctionInstructionsRows(level, density)
        initActionRow(density)
        infoLog("InGameSecondPart", "initiate")
        infoLog("width", "${size.width}")
        infoLog("height", "${size.heightDp}")

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
        infoLog("actionRowCaseBigger", "${size.actionRowCaseBigger}")
    }
    fun initActionRow(density: Float) {
        size.actionRowCase = ((size.width / maximumCaseNumberActionRow) / density).toInt()
        size.actionRowCaseBigger = (size.actionRowCase * ratios.actionRowCaseBigger).toInt()
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
        val functionCaseByWidth: Float = (size.width * ratios.maxFunctionWidth) / maxCasesNumber
//        val functionCaseByHeight: Float = size
        size.functionCase = (((size.width * ratios.maxFunctionWidth) / maxCasesNumber) / density).toInt()
        size.functionCaseIcon = size.functionCase
        size.bigFunctionCase = (size.functionCase * ( 1F + ratios.biggerFunctionCaseRatio )).toInt()
        size.halfFunctionCase = (size.functionCase / 2F).toInt()
        size.oneThirdFunctionCase = (size.functionCase * (1F/3F)).toInt()
        size.twoThirdFunctionCase = (size.functionCase * (2F/3F)).toInt()
        size.functionCasePadding = (size.functionCase * ratios.functionCasePadding).toInt()


    }
}
