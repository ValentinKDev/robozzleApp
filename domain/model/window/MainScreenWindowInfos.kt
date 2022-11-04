package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import android.content.Context
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
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
    private val profileButtonWidthRatioTarget = 0.4f
    private val profileButtonHeightRatioTarget = 0.2f

    private val creatorButtonWidthRatio = 0.25f
    private val creatorButtonHeightRatio = 0.1f
    private val creatorButtonWidthRatioTarget = 0.50f
    private val creatorButtonHeightRatioTarget = 0.2f

    private val donationButtonWidthRatio = 0.25f
    private val donationButtonHeightRatio = 0.1f
    private val donationButtonWidthRatioTarget = 0.7f
    private val donationButtonHeightRatioTarget = 0.4f

    fun getButtonSizeTarget(buttonId: Int, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (buttonId) {
            Screens.Profil.key-> Size(width = (profileButtonWidthRatioTarget * screenWidth), height = (profileButtonHeightRatioTarget * screenHeight))
            in Screens.Difficulty1.key..Screens.Difficulty5.key -> Size(width = levelDifficultyButtonWidthRatioTarget * screenWidth, height =  levelDifficultyButtonHeightRatioTarget * screenHeight )
            Screens.Creator.key, Screens.Config.key -> Size(width = creatorButtonWidthRatioTarget * screenWidth, height =  creatorButtonHeightRatioTarget * screenHeight )
            Screens.Donation.key -> Size(width = donationButtonWidthRatioTarget * screenWidth, height =  donationButtonHeightRatioTarget * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

    fun getButtonSize(buttonId: Int, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (buttonId) {
            Screens.Profil.key -> Size(width = (profileButtonWidthRatio * screenWidth), height = (profileButtonHeightRatio * screenHeight))
            in Screens.Difficulty1.key..Screens.Difficulty5.key  -> Size(width = levelDifficultyButtonWidthRatio * screenWidth, height =  levelDifficultyButtonHeightRatio * screenHeight )
            Screens.Creator.key, Screens.Config.key -> Size(width = creatorButtonWidthRatio * screenWidth, height =  creatorButtonHeightRatio * screenHeight )
            Screens.Donation.key -> Size(width = donationButtonWidthRatio * screenWidth, height =  donationButtonHeightRatio * screenHeight )
            else -> Size(width = 0f, height = 0f)
        }
    }

    fun getWidthRatioFromId(id: Screens): Float = when (id) {
        Screens.RegisterLogin -> profileButtonWidthRatio
        Screens.UserInfo -> profileButtonWidthRatio
        Screens.Difficulty1 -> levelDifficultyButtonWidthRatio
        Screens.Difficulty2 -> levelDifficultyButtonWidthRatio
        Screens.Difficulty3 -> levelDifficultyButtonWidthRatio
        Screens.Difficulty4 -> levelDifficultyButtonWidthRatio
        Screens.Difficulty5 -> levelDifficultyButtonWidthRatio
        Screens.Creator -> donationButtonWidthRatio
        Screens.Config -> donationButtonWidthRatio
        Screens.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getWidthRatioTargetFromId(id: Screens): Float = when (id) {
        Screens.RegisterLogin -> profileButtonWidthRatio
        Screens.UserInfo -> profileButtonWidthRatio
        Screens.Difficulty1 -> levelDifficultyButtonWidthRatioTarget
        Screens.Difficulty2 -> levelDifficultyButtonWidthRatioTarget
        Screens.Difficulty3 -> levelDifficultyButtonWidthRatioTarget
        Screens.Difficulty4 -> levelDifficultyButtonWidthRatioTarget
        Screens.Difficulty5 -> levelDifficultyButtonWidthRatioTarget
        Screens.Creator -> donationButtonWidthRatio
        Screens.Config -> donationButtonWidthRatio
        Screens.Donation -> donationButtonWidthRatio
        else -> 0f
    }

    fun getHeightRatioFromId(button: Screens): Float = when (button) {
        Screens.RegisterLogin -> profileButtonHeightRatio
        Screens.UserInfo -> profileButtonHeightRatio
        Screens.Difficulty1 -> levelDifficultyButtonHeightRatio
        Screens.Difficulty2 -> levelDifficultyButtonHeightRatio
        Screens.Difficulty3 -> levelDifficultyButtonHeightRatio
        Screens.Difficulty4 -> levelDifficultyButtonHeightRatio
        Screens.Difficulty5 -> levelDifficultyButtonHeightRatio
        Screens.Creator -> donationButtonHeightRatio
        Screens.Config -> donationButtonHeightRatio
        Screens.Donation -> donationButtonHeightRatio
        else -> 0f
    }

    fun getHeightRatioTargetFromId(button: Screens): Float = when (button) {
        Screens.RegisterLogin -> profileButtonHeightRatio
        Screens.UserInfo -> profileButtonHeightRatio
        Screens.Difficulty1 -> levelDifficultyButtonHeightRatioTarget
        Screens.Difficulty2 -> levelDifficultyButtonHeightRatioTarget
        Screens.Difficulty3 -> levelDifficultyButtonHeightRatioTarget
        Screens.Difficulty4 -> levelDifficultyButtonHeightRatioTarget
        Screens.Difficulty5 -> levelDifficultyButtonHeightRatioTarget
        Screens.Creator -> donationButtonHeightRatio
        Screens.Config -> donationButtonHeightRatio
        Screens.Donation -> donationButtonHeightRatio
        else -> 0f
    }

//    private fun getRatioInRange(id: Int, array: Array<Float>): Float = array[id]
}