package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

class MainScreenWindowsInfos() {
    /** Parts */
    val firstPartScreenWeight = 0.2f
    val secondPartScreenWeight = 0.6f
    val thirdPartScreenWeight = 0.2f

    /** Buttons */
    val buttonColor = grayDark3
    private val levelDifficultyButtonWidthRatio = 0.75f
    private val levelDifficultyButtonHeightRatio = 0.1f
    private val levelDifficultyButtonWidthRatioTarget = 1f
    private val levelDifficultyButtonHeightRatioTarget = 0.15f
    private val profileButtonWidthRatio = 0.2f
    private val profileButtonHeightRatio = 0.1f
    private val donationButtonWidthRatio = 0.25f
    private val donationButtonHeightRatio = 0.1f

    fun getWidthRatioFromId(id: ButtonId): Float = when (id) {
        ButtonId.Profile -> profileButtonWidthRatio
        ButtonId.LevelDiff1 -> levelDifficultyButtonWidthRatio
        ButtonId.LevelDiff2 -> levelDifficultyButtonWidthRatio
        ButtonId.LevelDiff3 -> levelDifficultyButtonWidthRatio
        ButtonId.LevelDiff4 -> levelDifficultyButtonWidthRatio
        ButtonId.LevelDiff5 -> levelDifficultyButtonWidthRatio
        ButtonId.Creator -> donationButtonWidthRatio
        ButtonId.Config -> donationButtonWidthRatio
        ButtonId.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getWidthRatioTargetFromId(id: ButtonId): Float = when (id) {
        ButtonId.Profile -> profileButtonWidthRatio
        ButtonId.LevelDiff1 -> levelDifficultyButtonWidthRatioTarget
        ButtonId.LevelDiff2 -> levelDifficultyButtonWidthRatioTarget
        ButtonId.LevelDiff3 -> levelDifficultyButtonWidthRatioTarget
        ButtonId.LevelDiff4 -> levelDifficultyButtonWidthRatioTarget
        ButtonId.LevelDiff5 -> levelDifficultyButtonWidthRatioTarget
        ButtonId.Creator -> donationButtonWidthRatio
        ButtonId.Config -> donationButtonWidthRatio
        ButtonId.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getHeightRatioFromId(button: ButtonId): Float = when (button) {
        ButtonId.Profile -> profileButtonHeightRatio
        ButtonId.LevelDiff1 -> levelDifficultyButtonHeightRatio
        ButtonId.LevelDiff2 -> levelDifficultyButtonHeightRatio
        ButtonId.LevelDiff3 -> levelDifficultyButtonHeightRatio
        ButtonId.LevelDiff4 -> levelDifficultyButtonHeightRatio
        ButtonId.LevelDiff5 -> levelDifficultyButtonHeightRatio
        ButtonId.Creator -> donationButtonHeightRatio
        ButtonId.Config -> donationButtonHeightRatio
        ButtonId.Donation -> donationButtonHeightRatio
        else -> 0f
    }

    fun getHeightRatioTargetFromId(button: ButtonId): Float = when (button) {
        ButtonId.Profile -> profileButtonHeightRatio
        ButtonId.LevelDiff1 -> levelDifficultyButtonHeightRatioTarget
        ButtonId.LevelDiff2 -> levelDifficultyButtonHeightRatioTarget
        ButtonId.LevelDiff3 -> levelDifficultyButtonHeightRatioTarget
        ButtonId.LevelDiff4 -> levelDifficultyButtonHeightRatioTarget
        ButtonId.LevelDiff5 -> levelDifficultyButtonHeightRatioTarget
        ButtonId.Creator -> donationButtonHeightRatio
        ButtonId.Config -> donationButtonHeightRatio
        ButtonId.Donation -> donationButtonHeightRatio
        else -> 0f
    }

//    private fun getRatioInRange(id: Int, array: Array<Float>): Float = array[id]
}