package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.layout.mainScreen.MainMenuColor
import com.mobilegame.robozzle.data.layout.mainScreen.MainMenuRatios
import com.mobilegame.robozzle.data.layout.mainScreen.MainMenuText

class MainScreenData(): ViewModel() {
    val text = MainMenuText

    val color = MainMenuColor
    val ratios = MainMenuRatios

}