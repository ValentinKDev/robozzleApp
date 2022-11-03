package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.toDp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object InGameSecondPart {
    var size = Sizes
    var ratios = Ratios
    var actionRow = ActionRow
//    var initiated = false

    private val _initiated = MutableStateFlow<Boolean>(false)
    val initiated: StateFlow<Boolean> = _initiated.asStateFlow()

    var caseColoringIcon: CaseColoringIcon = CaseColoringIcon(0F, 0F, ratios.functionCaseColoring)

    object Ratios {
        const val height: Float = 6F

        const val actionRowFirstPart = 1.5F
        const val actionRowSecondPart = 9F
        const val actionRowStartPadding = 0.025F
        const val actionRowEndPadding = 0.005F
        const val actionRowHeight = 1F
        const val actionRowCaseBigger = 1.15F
        const val actionRowSurrounderHeight = 0.15F
        const val actionRowSurronderBlackLineHeight = 0.05F
        const val actionRowSurronderEmptyLineHeight = 0.08F
        const val actionRowBorder = 0.08F
        const val actionRowIcon = 1.7F
        const val functionRowIcon = 0.7F

        const val functionsRowPartHeight = 5F
        const val minimumFunctionRowPaddingHeight = 0.07F
        const val maxFunctionRowWidth = 0.8F
        const val functionRowPaddingWidth = 0.05F
        const val functionCasePadding = 0.1F
        const val biggerFunctionCaseRatio = 0.3F
//        const val selectionCaseHalo = 0.15F
        const val selectionCaseHalo = 0.10F
//        const val caseColoringIcon = 0.85F
        const val functionCaseColoring = 0.52F
//        const val caseColoringIcon = 2F
//        const val caseColoringIconBorder = 0.07F

        const val actionRowCaseColoringIcon = 1.15F
    }


    object Sizes {
        var width: Float = 0F
        var widthDp: Dp = Dp.Unspecified
        var height: Float = 0F
        var heightDp: Dp = Dp.Unspecified

        var functionPartHeight: Float = 0F
        var functionRowPaddingHeight: Float = 0F
        var functionRowPaddingHeightDp: Dp = Dp.Unspecified
        var functionRowPaddingWidth: Float = 0F
        var functionRowPaddingWidthDp: Dp = Dp.Unspecified
        var functionRowList: MutableList<Size> = mutableListOf()
        var functionRowWidthListDp: MutableList<Dp> = mutableListOf()
        var functionRowHeightListDp: MutableList<Dp> = mutableListOf()
        var functionCase: Float = 0F
        var functionCaseDp: Dp = Dp.Unspecified
        var functionCaseIcon: Float = 0F
        var functionCaseIconDp: Dp = Dp.Unspecified
        var functionCasePadding: Int = 0
        var bigFunctionCase: Float = 0F
        var bigFunctionCaseDp: Dp = Dp.Unspecified
        var halfFunctionCase: Int = 0
        var oneThirdFunctionCase: Int = 0
        var twoThirdFunctionCase: Int = 0
        var twoThirdFunctionCaseDp: Dp = Dp.Unspecified
        var selectionCaseHaloStroke: Float = 0F
        var caseColoringIcon: Float = 0F
//        var caseColoringIconDp: Dp = Dp.Unspecified
//        var caseColoringIconBorder: Float = 0F
//        var caseColoringIconBorderDp: Dp = Dp.Unspecified

        var actionRowCase: Float = 0F
        var actionRowCaseDp: Dp = Dp.Unspecified
        var actionRowCaseBigger: Float = 0F
        var actionRowCaseBiggerDp: Dp = Dp.Unspecified
        var actionRowIcon: Float = 0F
        var actionRowIconDp: Dp = Dp.Unspecified
        var actionRowBiggerIcon: Float = 0F
        var actionRowBiggerIconDp: Dp = Dp.Unspecified
    }

    object ActionRow {
        var caseColoringIcon: CaseColoringIcon = CaseColoringIcon(0F,0F, 0F)
    }

    const val actionToDisplayNumber = 9
    private var maximumCaseNumberActionRow = 10
    private var minimumCaseNumberPerRow = 7

    fun initFunctionInstructionsRows(level: Level, density: Float) {
        size.functionPartHeight = (size.height * ratios.functionsRowPartHeight) / (ratios.functionsRowPartHeight + ratios.actionRowHeight)

        var rows = 0
        level.funInstructionsList.forEach { rows += if (it.instructions.length > 9) 2 else 1 }

        var maxCasesNumber: Int = minimumCaseNumberPerRow
        level.funInstructionsList.forEach {
            if (it.instructions.length in maxCasesNumber..9) maxCasesNumber = it.instructions.length
        }
        size.functionRowPaddingHeight = 20F

        val functionCaseByHeight: Float =
            (size.functionPartHeight - (level.funInstructionsList.size * size.functionRowPaddingHeight)) / (rows + 1)

        val functionCaseByWidth: Float = (size.width * ratios.maxFunctionRowWidth) / maxCasesNumber
        size.functionCase = getSmallerFloat(functionCaseByHeight, functionCaseByWidth)
        size.functionCaseDp = size.functionCase.toDp(density)

        size.functionRowPaddingHeight = (size.height - (rows * size.functionCase)) / (rows * 2)
        size.functionRowPaddingHeightDp = size.functionRowPaddingHeight.toDp(density)


        level.funInstructionsList.forEachIndexed { _index, _functionInstructions ->
            val caseNumber = _functionInstructions.instructions.length
            if (caseNumber in 0..9) {
                size.functionRowList.add( index = _index, Size( width = caseNumber * size.functionCase, height = size.functionCase) )
                size.functionRowWidthListDp.add(index = _index, (caseNumber * size.functionCase).toDp(density))
                size.functionRowHeightListDp.add(index = _index, size.functionCase.toDp(density))
            }
            else {
                size.functionRowList.add( index = _index, Size( width = (caseNumber/2) * size.functionCase, height = size.functionCase * 2) )
                size.functionRowWidthListDp.add(index = _index, ((caseNumber/2) * size.functionCase).toDp(density))
                size.functionRowHeightListDp.add(index = _index, (size.functionCase * 2).toDp(density))
            }
        }

        size.functionCaseIcon = size.functionCase * ratios.functionRowIcon
        size.functionCaseIconDp = size.functionCaseIcon.toDp(density)
        size.bigFunctionCase = size.functionCase * ( 1F + ratios.biggerFunctionCaseRatio )
        size.bigFunctionCaseDp = size.bigFunctionCase.toDp(density)
        size.halfFunctionCase = (size.functionCase / 2F).toInt()
        size.oneThirdFunctionCase = (size.functionCase * (1F/3F)).toInt()
        size.twoThirdFunctionCase = (size.functionCase * (2F/3F)).toInt()
        size.twoThirdFunctionCaseDp = size.twoThirdFunctionCase.toFloat().toDp(density)
        size.functionCasePadding = (size.functionCase * ratios.functionCasePadding).toInt()
        size.selectionCaseHaloStroke = size.functionCase * ratios.selectionCaseHalo
//        size.caseColoringIcon = size.functionCase * ratios.caseColoringIcon
//        size.caseColoringIconDp = size.caseColoringIcon.toDp(density)
//        size.caseColoringIconBorder = size.caseColoringIcon * ratios.caseColoringIconBorder
//        size.caseColoringIconBorderDp = size.caseColoringIconBorder.toDp(density)

        infoLog("init", "\tfunctions part")
        infoLog("rows", "$rows")
        infoLog("max Cases Number", "$maxCasesNumber")
        infoLog("function case", "${size.functionCase}")
        infoLog("function case dp", "${size.functionCaseDp}")
        infoLog("function Case Padding", "${size.functionCasePadding}")
        infoLog("row size list", "${size.functionRowList}")
        infoLog("row size list widht dp", "${size.functionRowWidthListDp}")
        infoLog("row size list height dp", "${size.functionRowHeightListDp}")
        infoLog("bigFuntionCase", "${size.bigFunctionCase}")
        infoLog("bigFuntionCaseDp", "${size.bigFunctionCaseDp}")
        infoLog("halfFuncitonCase", "${size.halfFunctionCase}")
        infoLog("1/3functionCase", "${size.oneThirdFunctionCase}")
        infoLog("2/3functionCase", "${size.twoThirdFunctionCase}")
        infoLog("function case max num", "${maximumCaseNumberActionRow}")

        errorLog("rows ", "${rows}")
        errorLog("function part height ", "${size.functionPartHeight}")
        errorLog("function part height to Dp", "${size.functionPartHeight.toDp(density)}")
        errorLog("function Row padding h ", "${size.functionRowPaddingHeight}")
        errorLog("function Row padding h dp", "${size.functionRowPaddingHeightDp}")
    }

    fun initActionRow(density: Float) {
        size.actionRowCase = (size.width / maximumCaseNumberActionRow) / density
        size.actionRowCaseDp = size.actionRowCase.toDp(density)
        size.actionRowCaseBigger = size.actionRowCase * ratios.actionRowCaseBigger
        size.actionRowCaseBiggerDp = size.actionRowCaseBigger.toDp(density)

//        size.actionRowCaseBorder = ( size.actionRowCase * ratios.actionRowBorder ).toInt()
        size.actionRowIcon = size.actionRowCase * ratios.actionRowIcon
        size.actionRowIconDp = size.actionRowIcon.toDp(density)
        size.actionRowBiggerIcon = size.actionRowCase * ratios.actionRowCaseBigger * ratios.actionRowIcon
        size.actionRowBiggerIconDp = size.actionRowBiggerIcon.toDp(density)
//        act
        actionRow.caseColoringIcon = CaseColoringIcon(size.actionRowCase, density, ratios.actionRowCaseColoringIcon)

        infoLog("init", "\tactionRow part")
        infoLog("actionRowCase", "${size.actionRowCase}")
        infoLog("actionRowCaseBigger", "${size.actionRowCaseBigger}")
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = widthFull.toFloat()
        size.widthDp = size.width.toDp(density)
//        size.height = heightFull * Ratios.height
        size.height = heightFull * (Ratios.height / (Ratios.height + InGameFirstPart.Ratios.height + InGameThirdPart.Ratios.height))
        size.heightDp = size.height.toDp(density)

        infoLog("InGameSecondPart", "initiate")
        infoLog("width", "${size.width}")
        infoLog("height", "${size.heightDp}")

        initFunctionInstructionsRows(level, density)
        initActionRow(density)
        caseColoringIcon = CaseColoringIcon(size.functionCase, density, ratios.functionCaseColoring)
        errorLog(".....................................", "caseColoring radius = ${caseColoringIcon.radiusIn}")
        _initiated.value = true
    }
}
