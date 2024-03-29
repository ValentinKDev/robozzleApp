package com.mobilegame.robozzle.data.layout.config

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object ConfigScreenLayout {
    val header = Header
    val list = List
    val button = Button

    object Button {
        val ratios = RatiosButton
        val sizes = SizesButton
        val colors = ColorsButton
        val padding = PaddingButton

        object RatiosButton {
            const val height = 0.03F
            const val width = 0.09F
        }
        object SizesButton {
            var heightDp = Dp.Unspecified
            var widthDp = Dp.Unspecified
        }
        object ColorsButton {
            var shadow = MyColor.black7
            var light = MyColor.white9
        }
        object PaddingButton {
            const val topLight = 0.1F
            const val bottomLight = 0.1F
            const val startLight = 0.08F
            const val endLight = 0.08F
        }
    }

    object Header {
        val sizes = Sizes
        val ratios = Ratios
        val colors = Colors
        val text = Text

        object Ratios {
            var height = 1F
            var text = 0.35F
        }
        object Sizes{
            var height = 0F
            var text = 0F
            var textDp = Dp.Unspecified
            var textSp = 0.sp
        }
        object Colors {
            val text = whiteDark4
        }
        object Text {
            const val line = "Config"
        }
    }

    object List {
        val ratios = Ratios
        internal val sizes = Sizes
        val texts = Texts
        val colors = Colors

        object Ratios {
            var height = 10F
            var rowWidthPadding = 0.035F
            var rowHeightPadding = 0.05F
            var button = 1F
            var description = 9F
            var optionText = 0.22F
        }
        internal object Sizes {
            const val rowToDisplay = 10
            var height = 0F
            var width = 0F
            var rowHeight = 0F
            var rowHeightDp = Dp.Unspecified
            var rowWidthPadding = 0F
            var rowWidthPaddingDp = Dp.Unspecified

            var optionText = 0F
            var optionTextSp = 0.sp
        }
        object Texts {
            const val lightThemeLine = "Switch to set light theme"
            const val dragAndDropToTrash = "Switch to remove or put back trash in Game"
            const val displayLevelWin = "Switch to display levels you won in the list"
            const val orientation = "Switch off to use the app in landscape orientation"
            const val tutorial = "Press the button to restart the tutorial"
//            const val tutorialButton = ""
        }
        object Colors {
            val optionText = whiteDark4
        }
    }

    private var widthFull = 0
    private var heightFull = 0
    private var density = 0F

    fun init(context: Context): ConfigScreenLayout {
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density

        header.sizes.height = heightFull * (header.ratios.height / (header.ratios.height + list.ratios.height))
        header.sizes.text = header.sizes.height * header.ratios.text
        header.sizes.textDp = header.sizes.text.toDp(density)
        header.sizes.textSp = header.sizes.text.toSp(density)

        list.sizes.height = heightFull * (list.ratios.height / (header.ratios.height + list.ratios.height))
        list.sizes.rowHeight = list.sizes.height / list.sizes.rowToDisplay
        list.sizes.rowHeightDp = list.sizes.rowHeight.toDp(density)

        list.sizes.rowWidthPadding = widthFull * list.ratios.rowWidthPadding
        list.sizes.rowWidthPaddingDp = list.sizes.rowWidthPadding.toDp(density)
        list.sizes.optionText = list.sizes.rowHeight * list.ratios.optionText
        list.sizes.optionTextSp = list.sizes.optionText.toSp(density)

        button.sizes.heightDp = (heightFull * button.ratios.height).toDp(density)
        button.sizes.widthDp = (widthFull * button.ratios.width).toDp(density)

        displayUIData?.let {
            verbalLog("ConfigScreenLayout::init", "Start")
            infoLog("heightFull", "$heightFull")
            infoLog("header.size.text", "${header.sizes.text}")
            infoLog("header.size.textDp", "${header.sizes.textDp}")
            infoLog("header.size.textSp", "${header.sizes.textSp}")

            infoLog("list.size.height", "${list.sizes.height}")
            infoLog("list.sizes.rowToDisplay", "${list.sizes.rowToDisplay}")
            infoLog("list.sizes.row", "${list.sizes.rowHeight}")
            infoLog("list.sizes.rowDp", "${list.sizes.rowHeightDp}")
            infoLog("list.sizes.rowPadding", "${list.sizes.rowWidthPadding}")
            infoLog("list.sizes.rowPaddingDp", "${list.sizes.rowWidthPaddingDp}")
            infoLog("list.sizes.optionText", "${list.sizes.optionText}")
            infoLog("list.sizes.optionTextSp", "${list.sizes.optionTextSp}")

            infoLog("button.sizes.height", "${button.sizes.heightDp}")
            infoLog("button.sizes.width", "${button.sizes.widthDp}")
        }
        return this
    }
}
