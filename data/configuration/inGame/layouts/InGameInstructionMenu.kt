package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.toDp

object InGameInstructionMenu {
    val size = Sizes
    val ratios = Ratios
    private var maxCases = 0
    lateinit var caseColoringIcon: CaseColoringIcon
    var initiated = false
//    var case

    object Ratios {
        const val topPadding= 0.3F
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
        size.width = ((widthFull * (1F - Ratios.startPadding - Ratios.endPadding))) / density
//        size.width = widthFull / density
//        size.caseWithPadding = ((size.width / maxCases) - (maxCases))
        size.caseWithPadding = (size.width / maxCases)
        size.casePadding = 0F
        size.case = size.caseWithPadding - (2F * Sizes.casePadding)
        size.icon = size.case * ratios.icon
        size.iconDp = size.icon.toDp(density)
        caseColoringIcon = CaseColoringIcon(size.case, density, ratios.caseColoringIcon)

        infoLog(" maxCases ", "${maxCases}")
        infoLog(" full width ", "$widthFull")
        infoLog(" full width ", "${widthFull * 0.9}")
        infoLog(" width ", "${size.width}")
        infoLog(" caseWithPadding ", "${size.caseWithPadding}")
        infoLog(" casePaddinng ", "${size.casePadding}")
        infoLog(" case ", "${size.case}")
        infoLog(" icon ", "${size.icon}")
    }
}
