package com.mobilegame.robozzle.data.layout.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.layout.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.toDp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object InGameSecondPart {
    var sizes = Sizes
    var ratios = Ratios
    var actionRow = ActionRow
//    var initiated = false

    private val _initiated = MutableStateFlow<Boolean>(false)
    val initiated: StateFlow<Boolean> = _initiated.asStateFlow()

    var caseColoringIcon: CaseColoringIcon = CaseColoringIcon(0F, 0F, ratios.functionCaseColoring)

    object Ratios {
        const val height: Float = 6F

        const val actionRowHeight = 1F
        const val actionRowFirstPart = 1.5F
        const val actionRowSecondPart = 9F
        const val actionRowStartPadding = 0.025F
        const val actionRowEndPadding = 0.005F
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

        const val trashesWidth = 0.08F
    }


    object Sizes {
        var width: Float = 0F
        var widthDp: Dp = Dp.Unspecified
        var height: Float = 0F
        var heightDp: Dp = Dp.Unspecified

        var functionPartHeight: Float = 0F
        var functionPartHeightDp: Dp = Dp.Unspecified
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

        var trashWidth = 0F
        var trashWidthDp = Dp.Unspecified
    }

    object ActionRow {
        var caseColoringIcon: CaseColoringIcon = CaseColoringIcon(0F,0F, 0F)
    }

    private fun initTrashes(density: Float) {
        sizes.trashWidth = sizes.width * ratios.trashesWidth
        sizes.trashWidthDp = sizes.trashWidth.toDp(density)
    }

    const val actionToDisplayNumber = 9
    private var maximumCaseNumberActionRow = 10
    private var minimumCaseNumberPerRow = 7

    fun initFunctionInstructionsRows(level: Level, density: Float) {
        sizes.functionPartHeight = (sizes.height * ratios.functionsRowPartHeight) / (ratios.functionsRowPartHeight + ratios.actionRowHeight)
        sizes.functionPartHeightDp = sizes.functionPartHeight.toDp(density)

        var rows = 0
        level.funInstructionsList.forEach { rows += if (it.instructions.length > 9) 2 else 1 }

        var maxCasesNumber: Int = minimumCaseNumberPerRow
        level.funInstructionsList.forEach {
            if (it.instructions.length in maxCasesNumber..9) maxCasesNumber = it.instructions.length
        }
        sizes.functionRowPaddingHeight = 20F

        val functionCaseByHeight: Float =
            (sizes.functionPartHeight - (level.funInstructionsList.size * sizes.functionRowPaddingHeight)) / (rows + 1)

        val functionCaseByWidth: Float = (sizes.width * ratios.maxFunctionRowWidth) / maxCasesNumber
        sizes.functionCase = getSmallerFloat(functionCaseByHeight, functionCaseByWidth)
        sizes.functionCaseDp = sizes.functionCase.toDp(density)

        sizes.functionRowPaddingHeight = (sizes.height - (rows * sizes.functionCase)) / (rows * 2)
        sizes.functionRowPaddingHeightDp = sizes.functionRowPaddingHeight.toDp(density)


        level.funInstructionsList.forEachIndexed { _index, _functionInstructions ->
            val caseNumber = _functionInstructions.instructions.length
            if (caseNumber in 0..9) {
                sizes.functionRowList.add( index = _index, Size( width = caseNumber * sizes.functionCase, height = sizes.functionCase) )
                sizes.functionRowWidthListDp.add(index = _index, (caseNumber * sizes.functionCase).toDp(density))
                sizes.functionRowHeightListDp.add(index = _index, sizes.functionCase.toDp(density))
            }
            else {
                sizes.functionRowList.add( index = _index, Size( width = (caseNumber/2) * sizes.functionCase, height = sizes.functionCase * 2) )
                sizes.functionRowWidthListDp.add(index = _index, ((caseNumber/2) * sizes.functionCase).toDp(density))
                sizes.functionRowHeightListDp.add(index = _index, (sizes.functionCase * 2).toDp(density))
            }
        }

        sizes.functionCaseIcon = sizes.functionCase * ratios.functionRowIcon
        sizes.functionCaseIconDp = sizes.functionCaseIcon.toDp(density)
        sizes.bigFunctionCase = sizes.functionCase * ( 1F + ratios.biggerFunctionCaseRatio )
        sizes.bigFunctionCaseDp = sizes.bigFunctionCase.toDp(density)
        sizes.halfFunctionCase = (sizes.functionCase / 2F).toInt()
        sizes.oneThirdFunctionCase = (sizes.functionCase * (1F/3F)).toInt()
        sizes.twoThirdFunctionCase = (sizes.functionCase * (2F/3F)).toInt()
        sizes.twoThirdFunctionCaseDp = sizes.twoThirdFunctionCase.toFloat().toDp(density)
        sizes.functionCasePadding = (sizes.functionCase * ratios.functionCasePadding).toInt()
        sizes.selectionCaseHaloStroke = sizes.functionCase * ratios.selectionCaseHalo
//        size.caseColoringIcon = size.functionCase * ratios.caseColoringIcon
//        size.caseColoringIconDp = size.caseColoringIcon.toDp(density)
//        size.caseColoringIconBorder = size.caseColoringIcon * ratios.caseColoringIconBorder
//        size.caseColoringIconBorderDp = size.caseColoringIconBorder.toDp(density)

        infoLog("init", "\tfunctions part")
        infoLog("rows", "$rows")
        infoLog("max Cases Number", "$maxCasesNumber")
        infoLog("function case", "${sizes.functionCase}")
        infoLog("function case dp", "${sizes.functionCaseDp}")
        infoLog("function Case Padding", "${sizes.functionCasePadding}")
        infoLog("row size list", "${sizes.functionRowList}")
        infoLog("row size list widht dp", "${sizes.functionRowWidthListDp}")
        infoLog("row size list height dp", "${sizes.functionRowHeightListDp}")
        infoLog("bigFuntionCase", "${sizes.bigFunctionCase}")
        infoLog("bigFuntionCaseDp", "${sizes.bigFunctionCaseDp}")
        infoLog("halfFuncitonCase", "${sizes.halfFunctionCase}")
        infoLog("1/3functionCase", "${sizes.oneThirdFunctionCase}")
        infoLog("2/3functionCase", "${sizes.twoThirdFunctionCase}")
        infoLog("function case max num", "${maximumCaseNumberActionRow}")

        errorLog("rows ", "${rows}")
        errorLog("function part height ", "${sizes.functionPartHeight}")
        errorLog("function part height to Dp", "${sizes.functionPartHeight.toDp(density)}")
        errorLog("function Row padding h ", "${sizes.functionRowPaddingHeight}")
        errorLog("function Row padding h dp", "${sizes.functionRowPaddingHeightDp}")
    }

    fun initActionRow(density: Float) {
        sizes.actionRowCase = (sizes.width / maximumCaseNumberActionRow) / density
        sizes.actionRowCaseDp = sizes.actionRowCase.toDp(density)
        sizes.actionRowCaseBigger = sizes.actionRowCase * ratios.actionRowCaseBigger
        sizes.actionRowCaseBiggerDp = sizes.actionRowCaseBigger.toDp(density)

//        size.actionRowCaseBorder = ( size.actionRowCase * ratios.actionRowBorder ).toInt()
        sizes.actionRowIcon = sizes.actionRowCase * ratios.actionRowIcon
        sizes.actionRowIconDp = sizes.actionRowIcon.toDp(density)
        sizes.actionRowBiggerIcon = sizes.actionRowCase * ratios.actionRowCaseBigger * ratios.actionRowIcon
        sizes.actionRowBiggerIconDp = sizes.actionRowBiggerIcon.toDp(density)
//        act
        actionRow.caseColoringIcon = CaseColoringIcon(sizes.actionRowCase, density, ratios.actionRowCaseColoringIcon)

        infoLog("init", "\tactionRow part")
        infoLog("actionRowCase", "${sizes.actionRowCase}")
        infoLog("actionRowCaseBigger", "${sizes.actionRowCaseBigger}")
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        sizes.width = widthFull.toFloat()
        sizes.widthDp = sizes.width.toDp(density)
//        size.height = heightFull * Ratios.height
        sizes.height = heightFull * (Ratios.height / (Ratios.height + InGameFirstPart.Ratios.height + InGameThirdPart.Ratios.height))
        sizes.heightDp = sizes.height.toDp(density)

        infoLog("InGameSecondPart", "initiate")
        infoLog("width", "${sizes.width}")
        infoLog("height", "${sizes.heightDp}")

        initFunctionInstructionsRows(level, density)
        initActionRow(density)
        caseColoringIcon = CaseColoringIcon(sizes.functionCase, density, ratios.functionCaseColoring)
        errorLog(".....................................", "caseColoring radius = ${caseColoringIcon.radiusIn}")
        _initiated.value = true
        initTrashes(density)
    }

}
