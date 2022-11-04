package com.mobilegame.robozzle.data.layout.inGame.layouts

import android.content.Context
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.layout.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.toDp

object InGameInstructionMenu {
    val sizes = Sizes
    val ratios = Ratios
    private var maxCases = 0
    private var maxLines = 0
    lateinit var caseColoringIcon: CaseColoringIcon
    var initiated = false
//    var case

    object Ratios {
        const val topPadding= 0.25F
        const val bottomPadding= 0.4F
        const val startPadding= 0.05F
        const val endPadding= 0.05F

        const val casePadding = 0.1F
        const val iconBiggerThanCase = 0.15F
        const val icon = 1.6F
        const val caseColoringIcon = 1.15F
    }

    object Sizes {
        var width = 0F
        var height = 0F
        var windowWidth = 0F
        var windowHeight = 0F
        var caseWithPadding = 0F
        var casePadding = 0F
        var case = 0F
        var icon: Float = 0F
        var iconDp: Dp = Dp.Unspecified
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        maxCases = level.instructionsMenu.first().instructions.length
        maxLines = level.instructionsMenu.size + 1
        sizes.width = ((widthFull * (1F - ratios.startPadding - ratios.endPadding))) / density
        sizes.height = ((heightFull *(1F - ratios.topPadding - ratios.bottomPadding))) / density
//        size.width = widthFull / density
//        size.caseWithPadding = ((size.width / maxCases) - (maxCases))
        sizes.caseWithPadding = getSmallerFloat(sizes.width / maxCases, sizes.height / maxLines)
        sizes.casePadding = 0F
        sizes.case = sizes.caseWithPadding - (2F * Sizes.casePadding)
        sizes.icon = sizes.case * ratios.icon
        sizes.iconDp = sizes.icon.toDp(density)
        caseColoringIcon = CaseColoringIcon(sizes.case, density, ratios.caseColoringIcon)
//        size.windowWidth = if (size.width / maxCases < size.height / maxLines ) size.caseWithPadding * maxCases else size.casePadding * maxLines
//        size.windowHeight = if (size.width / maxCases < size.height / maxLines ) size.caseWithPadding * maxCases else size.casePadding * maxLines
        sizes.windowWidth = sizes.caseWithPadding * maxCases
        sizes.windowHeight = sizes.caseWithPadding * maxLines

        infoLog(" maxCases ", "${maxCases}")
        infoLog(" maxLines ", "${maxLines}")
        infoLog(" full width ", "$widthFull")
//        infoLog(" full width ", "${widthFull * 0.9}")
        infoLog(" width ", "${sizes.width}")
        infoLog(" height ", "${sizes.height}")
        infoLog(" window width ", "${sizes.windowWidth}")
        infoLog(" window height ", "${sizes.windowHeight}")
        infoLog(" caseWithPadding ", "${sizes.caseWithPadding}")
//        size.caseWithPadding = (size.height / maxLines)
//        infoLog(" caseWithPadding ", "${size.caseWithPadding}")
        infoLog(" casePaddinng ", "${sizes.casePadding}")
        infoLog(" case ", "${sizes.case}")
        infoLog(" icon ", "${sizes.icon}")
    }
}
