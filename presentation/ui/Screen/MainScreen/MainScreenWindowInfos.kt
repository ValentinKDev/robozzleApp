package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import android.content.Context
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

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

    fun getButtonSizeTarget(button: ButtonId, context: Context, density: Density): Size {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        return when (button) {
            ButtonId.Profile -> Size(width = (profileButtonWidthRatio * screenWidth), height = (profileButtonHeightRatio * screenHeight))
            ButtonId.LevelDiff1 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            ButtonId.LevelDiff2 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            ButtonId.LevelDiff3 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            ButtonId.LevelDiff4 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            ButtonId.LevelDiff5 -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            ButtonId.Creator -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            ButtonId.Config -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            ButtonId.Donation -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

    fun getButtonSize(button: ButtonId, context: Context, density: Density): Size {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        return when (button) {
            ButtonId.Profile -> Size(width = (profileButtonWidthRatio * screenWidth), height = (profileButtonHeightRatio * screenHeight))
            ButtonId.LevelDiff1 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            ButtonId.LevelDiff2 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            ButtonId.LevelDiff3 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            ButtonId.LevelDiff4 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            ButtonId.LevelDiff5 -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            ButtonId.Creator -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            ButtonId.Config -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            ButtonId.Donation -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

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