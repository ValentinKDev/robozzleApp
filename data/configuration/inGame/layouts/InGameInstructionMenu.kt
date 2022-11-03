package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.toDp
import io.ktor.utils.io.*

object InGameInstructionMenu {
    val size = Sizes
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
        size.width = ((widthFull * (1F - ratios.startPadding - ratios.endPadding))) / density
        size.height = ((heightFull *(1F - ratios.topPadding - ratios.bottomPadding))) / density
//        size.width = widthFull / density
//        size.caseWithPadding = ((size.width / maxCases) - (maxCases))
        size.caseWithPadding = getSmallerFloat(size.width / maxCases, size.height / maxLines)
        size.casePadding = 0F
        size.case = size.caseWithPadding - (2F * Sizes.casePadding)
        size.icon = size.case * ratios.icon
        size.iconDp = size.icon.toDp(density)
        caseColoringIcon = CaseColoringIcon(size.case, density, ratios.caseColoringIcon)
//        size.windowWidth = if (size.width / maxCases < size.height / maxLines ) size.caseWithPadding * maxCases else size.casePadding * maxLines
//        size.windowHeight = if (size.width / maxCases < size.height / maxLines ) size.caseWithPadding * maxCases else size.casePadding * maxLines
        size.windowWidth = size.caseWithPadding * maxCases
        size.windowHeight = size.caseWithPadding * maxLines

        infoLog(" maxCases ", "${maxCases}")
        infoLog(" maxLines ", "${maxLines}")
        infoLog(" full width ", "$widthFull")
//        infoLog(" full width ", "${widthFull * 0.9}")
        infoLog(" width ", "${size.width}")
        infoLog(" height ", "${size.height}")
        infoLog(" window width ", "${size.windowWidth}")
        infoLog(" window height ", "${size.windowHeight}")
        infoLog(" caseWithPadding ", "${size.caseWithPadding}")
//        size.caseWithPadding = (size.height / maxLines)
//        infoLog(" caseWithPadding ", "${size.caseWithPadding}")
        infoLog(" casePaddinng ", "${size.casePadding}")
        infoLog(" case ", "${size.case}")
        infoLog(" icon ", "${size.icon}")
    }
}
