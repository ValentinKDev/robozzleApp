package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj

object MainScreenLayout {
    var tuto = TutoObj
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

    private fun initTuto() {
        tuto = TutoObj.create()
    }

    fun create(): MainScreenLayout {
        initTuto()

        return this
    }
}