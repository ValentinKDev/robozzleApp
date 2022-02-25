package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import android.content.Context
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainScreenWindowsInfos(): ViewModel() {
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

    fun getButtonSizeTarget(button: MainMenuButton, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (button) {
            MainMenuButton.Profile -> Size(width = (profileButtonWidthRatio * screenWidth), height = (profileButtonHeightRatio * screenHeight))
            MainMenuButton.LevelDiff1 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            MainMenuButton.LevelDiff2 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            MainMenuButton.LevelDiff3 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            MainMenuButton.LevelDiff4 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            MainMenuButton.LevelDiff5 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            MainMenuButton.Creator -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            MainMenuButton.Config -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            MainMenuButton.Donation -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

    fun getButtonSize(button: MainMenuButton, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (button) {
            MainMenuButton.Profile -> Size(width = (profileButtonWidthRatio * screenWidth), height = (profileButtonHeightRatio * screenHeight))
            MainMenuButton.LevelDiff1 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            MainMenuButton.LevelDiff2 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            MainMenuButton.LevelDiff3 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            MainMenuButton.LevelDiff4 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            MainMenuButton.LevelDiff5 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            MainMenuButton.Creator -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            MainMenuButton.Config -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            MainMenuButton.Donation -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

    fun getWidthRatioFromId(id: MainMenuButton): Float = when (id) {
        MainMenuButton.Profile -> profileButtonWidthRatio
        MainMenuButton.LevelDiff1 -> levelDifficultyButtonWidthRatio
        MainMenuButton.LevelDiff2 -> levelDifficultyButtonWidthRatio
        MainMenuButton.LevelDiff3 -> levelDifficultyButtonWidthRatio
        MainMenuButton.LevelDiff4 -> levelDifficultyButtonWidthRatio
        MainMenuButton.LevelDiff5 -> levelDifficultyButtonWidthRatio
        MainMenuButton.Creator -> donationButtonWidthRatio
        MainMenuButton.Config -> donationButtonWidthRatio
        MainMenuButton.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getWidthRatioTargetFromId(id: MainMenuButton): Float = when (id) {
        MainMenuButton.Profile -> profileButtonWidthRatio
        MainMenuButton.LevelDiff1 -> levelDifficultyButtonWidthRatioTarget
        MainMenuButton.LevelDiff2 -> levelDifficultyButtonWidthRatioTarget
        MainMenuButton.LevelDiff3 -> levelDifficultyButtonWidthRatioTarget
        MainMenuButton.LevelDiff4 -> levelDifficultyButtonWidthRatioTarget
        MainMenuButton.LevelDiff5 -> levelDifficultyButtonWidthRatioTarget
        MainMenuButton.Creator -> donationButtonWidthRatio
        MainMenuButton.Config -> donationButtonWidthRatio
        MainMenuButton.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getHeightRatioFromId(button: MainMenuButton): Float = when (button) {
        MainMenuButton.Profile -> profileButtonHeightRatio
        MainMenuButton.LevelDiff1 -> levelDifficultyButtonHeightRatio
        MainMenuButton.LevelDiff2 -> levelDifficultyButtonHeightRatio
        MainMenuButton.LevelDiff3 -> levelDifficultyButtonHeightRatio
        MainMenuButton.LevelDiff4 -> levelDifficultyButtonHeightRatio
        MainMenuButton.LevelDiff5 -> levelDifficultyButtonHeightRatio
        MainMenuButton.Creator -> donationButtonHeightRatio
        MainMenuButton.Config -> donationButtonHeightRatio
        MainMenuButton.Donation -> donationButtonHeightRatio
        else -> 0f
    }

    fun getHeightRatioTargetFromId(button: MainMenuButton): Float = when (button) {
        MainMenuButton.Profile -> profileButtonHeightRatio
        MainMenuButton.LevelDiff1 -> levelDifficultyButtonHeightRatioTarget
        MainMenuButton.LevelDiff2 -> levelDifficultyButtonHeightRatioTarget
        MainMenuButton.LevelDiff3 -> levelDifficultyButtonHeightRatioTarget
        MainMenuButton.LevelDiff4 -> levelDifficultyButtonHeightRatioTarget
        MainMenuButton.LevelDiff5 -> levelDifficultyButtonHeightRatioTarget
        MainMenuButton.Creator -> donationButtonHeightRatio
        MainMenuButton.Config -> donationButtonHeightRatio
        MainMenuButton.Donation -> donationButtonHeightRatio
        else -> 0f
    }

//    private fun getRatioInRange(id: Int, array: Array<Float>): Float = array[id]
}