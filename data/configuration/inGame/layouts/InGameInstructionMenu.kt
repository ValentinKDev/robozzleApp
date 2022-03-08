package com.mobilegame.robozzle.data.configuration.inGame.layouts

import androidx.compose.ui.geometry.Rect
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.level.Level

object InGameInstructionMenu {
    val size = Sizes
    val ratios = Ratios
    private var maxCases = 0

    object Ratios {
        const val topPadding= 0.3F
        const val bottomPadding= 0.5F
        const val startPadding= 0.1F
        const val endPadding= 0.1F

        const val casePadding = 0.1F
        const val iconBiggerThanCase = 0.15F
    }

    object Sizes {
        var width = 0F
        var caseWithPadding = 0F
        var casePadding = 0F
        var case = 0F
        var icon = 0F
    }

    fun init(window: Rect, level: Level) {
        maxCases = level.instructionsMenu.first().instructions.length
        size.width = ((window.width * (1F - Ratios.startPadding - Ratios.endPadding)))
        size.caseWithPadding = ((Sizes.width / maxCases) - (maxCases))
        size.case = Sizes.casePadding - 2F * Sizes.casePadding
        size.icon = ratios.iconBiggerThanCase * size.case
    }
}
