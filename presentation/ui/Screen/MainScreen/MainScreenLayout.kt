package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.MyColor

object MainScreenLayout {
    val tuto = Tuto
    val button = Button


    object Button {
        val colors = ColorsButton

        object ColorsButton {
            val enableBackground = MyColor.grayDark3
            val disableBackground = MyColor.grayDark4Plus
            val enableText = MyColor.whiteDark4
            val disableText = MyColor.whiteDark7
        }
    }

    object Tuto {
        val colors = ColorsTuto
        val popup = PopupTuto

        object ColorsTuto {
            val filter = MyColor.black3
            val enlighteningButtonInitial = MyColor.gray3
            val enlighteningButtonTarget = MyColor.white4
            val enlighteningTextInitial = MyColor.whiteDark2
            val enlighteningTextTarget = MyColor.whiteDark0
            val popupBackground = MyColor.grayDark3
            val popupText = MyColor.whiteDark3
        }

        object PopupTuto {
            const val topPadding = 0.8F
            const val startPadding = 0.1F
            const val endPadding = 0.1F
            const val bottomPadding = 0.05F
            val shadow = 15.dp
        }
    }
    private fun initTuto() {

    }

    fun create(): MainScreenLayout {
        initTuto()

        return this
    }
}