package com.mobilegame.robozzle.data.layout.donation

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.RanksLevelLayout

object DonationScreenRatios {

    const val fistPartWeigth = 6F
    const val secondtPartWeight = 8F

    const val firstPartBottomPadding = secondtPartWeight / (fistPartWeigth + secondtPartWeight)
    const val headerWeight = 1F
    const val introWeight = 9F
    const val introVerticalPadding = 0.1F
    const val introHorizontalPadding = 0.05F

    const val foldableListHeightWeight = 6F
    const val selectedAddressBarHeightWeight = 1.2F
    const val secontPartTopPadding = fistPartWeigth / (fistPartWeigth + secondtPartWeight)
    const val selectedBarHeightRatio = 0.08F
    const val selectionBarAndIconColumnHorizontalPadd = 0.085F
    const val selectedAddressStartPadd = 0.5F
    const val selectedAddressWeight = 8F
    const val IconWeight = 1F

}