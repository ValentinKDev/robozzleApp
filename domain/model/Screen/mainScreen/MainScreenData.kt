package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.configuration.mainScreen.MainMenuColor
import com.mobilegame.robozzle.data.configuration.mainScreen.MainMenuRatios
import com.mobilegame.robozzle.data.configuration.mainScreen.MainMenuText

class MainScreenData(): ViewModel() {
    val text = MainMenuText

    val color = MainMenuColor
    val ratios = MainMenuRatios

}