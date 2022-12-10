package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj

object MainScreenLayout {
    var tuto = TutoObj.create()
    val button = Button
    val popup = PopupMainMenu


    object PopupMainMenu {
        val color = ColorsPopup
        val ratios = RatiosPopup

        object RatiosPopup {
            const val topPaddingRatio = 0.3F
            const val bottomPaddingRatio = 0.5F
            const val startPaddingRatio = 0.1F
            const val endPaddingRatio = 0.1F
        }
        object ColorsPopup {
            val background = MyColor.applicationSurface
            val text = MyColor.whiteDark6
            val elevation = 15.dp
        }
    }

    object Button {
        val colors = ColorsButton

        object ColorsButton {
            val enableBackground = MyColor.grayDark3
            val disableBackground = MyColor.grayDark4Plus
            val enableText = MyColor.whiteDark4
            val disableText = MyColor.whiteDark7
        }
    }

    fun create(): MainScreenLayout {

        return this
    }
}