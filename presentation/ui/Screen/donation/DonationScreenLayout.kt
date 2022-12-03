package com.mobilegame.robozzle.presentation.ui.Screen.donation

import android.content.Context
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object DonationScreenLayout {
    val header = Header
    val presentation = PresentationDonation
    val selector = SelectorDonation

    object Header {
        val sizes = SizesHeader
        val ratios = RatiosHeader
        val colors = ColorsHeader
        val text = TextHeader

        object RatiosHeader {
            var height = 1F
            var text = 0.35F
        }
        object SizesHeader {
            var height = 0F
            var text = 0F
            var textDp = Dp.Unspecified
            var textSp = 0.sp
        }
        object ColorsHeader {
            val text = MyColor.whiteDark4
        }
        object TextHeader {
            const val line = "Donation"
        }
    }

    object PresentationDonation {
        val ratios = RatiosPresentation

        object RatiosPresentation {
            const val heightWeight = 5F
            const val startPadding = 0.08F
            const val endPadding = 0.05F
        }
    }

    object SelectorDonation {
        val ratios = RatiosSelector
        val foldedBar = FoldedBarSelector
        val list = ListSelector
        val keyboardSpace = KeyboardSpace

        object KeyboardSpace {
            var heightDp = Dp.Unspecified
        }

        object RatiosSelector {
            const val heightWeight = 7F
            const val topPadding = 0.08F
            const val sidePadding = 0.085F
            var bottomPadding = 0F
        }

        object FoldedBarSelector {
            val ratios = RatiosFoldedBar
            val shape = ShapeFoldedBar
            val colors = ColorsFoldedBar

            object RatiosFoldedBar {
                const val heightWeight = 1.5F
            }
            object ShapeFoldedBar {
                val corner = 5.dp
                val elevation = 20.dp
            }
            object ColorsFoldedBar {
                val background = MyColor.grayDark4
            }
        }
        object ListSelector {
            val ratios = RatiosList
            val sizes = SizesList
            val colors = ColotsList

            object RatiosList {
                var heightWeight = 0F
                const val numberOfElementDisplayed = 7.5F
            }
            object SizesList {
                var bottomHeightCoordinates: Float? = null
                var animInitialHeight: Int = 0
                var animTargetHeight: Int = 0

                var height = 0
                var elementHeightDp = Dp.Unspecified
                val elementDelimiter = 2.dp
            }
            object ColotsList {
                var background = MyColor.applicationSurfaceDark
            }
        }
    }


    private var widthFull = 0
    private var heightFull = 0
    private var density = 0F
    private var allWeights = 0F

    private fun initSelector() {
        selector.ratios.bottomPadding = (allWeights - selector.foldedBar.ratios.heightWeight - header.ratios.height - presentation.ratios.heightWeight) / allWeights
        selector.list.ratios.heightWeight = selector.ratios.heightWeight - selector.foldedBar.ratios.heightWeight
        selector.list.sizes.height = ((selector.list.ratios.heightWeight/ allWeights) * heightFull).toInt()
        selector.list.sizes.elementHeightDp = (selector.list.sizes.height.toFloat() / selector.list.ratios.numberOfElementDisplayed).toDp(density)

        selector.keyboardSpace.heightDp = (selector.ratios.bottomPadding * heightFull).toDp(density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::initSelector", "Start")
            verbalLog("DonationScreenLayout::initSelector", "bottomPadding ${selector.ratios.bottomPadding}")
            verbalLog("DonationScreenLayout::initSelector", "list weight ${selector.list.ratios.heightWeight}")
            verbalLog("DonationScreenLayout::initSelector", "list height ${selector.list.sizes.height}")
            verbalLog("DonationScreenLayout::initSelector", "list element heightDp ${selector.list.sizes.elementHeightDp}")
            verbalLog("DonationScreenLayout::initSelector", "keyboardspace heightDp ${selector.keyboardSpace.heightDp}")

        }
    }

    private fun initHeader() {
        header.sizes.height = heightFull * (header.ratios.height / (header.ratios.height + presentation.ratios.heightWeight + selector.ratios.heightWeight))
        header.sizes.text = header.sizes.height * header.ratios.text
        header.sizes.textDp = header.sizes.text.toDp(density)
        header.sizes.textSp = header.sizes.text.toSp(density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::initHeader", "Start")
            verbalLog("DonationScreenLayout::initHeader", "height ${header.sizes.height}")
        }
    }

    fun create(context: Context): DonationScreenLayout {
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density
        allWeights = header.ratios.height + presentation.ratios.heightWeight + selector.ratios.heightWeight


        displayUIData?.let {
            infoLog("DonationScreenLayout::create", "Start")
            verbalLog("DonationScreenLayout::create", "heightFull ${heightFull}")
            verbalLog("DonationScreenLayout::create", "widthFull ${widthFull}")
            verbalLog("DonationScreenLayout::create", "density ${density}")
            verbalLog("DonationScreenLayout::create", "allWeights ${allWeights}")
        }
        initHeader()
        initSelector()
        return this
    }

    fun setListSize(layoutCoordinates: LayoutCoordinates) {
        selector.list.sizes.bottomHeightCoordinates ?: let {
            selector.list.sizes.bottomHeightCoordinates = layoutCoordinates.boundsInRoot().height / density
            selector.list.sizes.animInitialHeight = (selector.list.sizes.bottomHeightCoordinates!! * 0.5F).toInt()
            selector.list.sizes.animTargetHeight = (layoutCoordinates.boundsInRoot().height * 2).toInt()
            displayUIData?.let {
                verbalLog("DonationScreenLayout::setListSize", "layoutCoordinates ${layoutCoordinates.boundsInRoot().height}")
                verbalLog("DonationScreenLayout::setListSize", "list layout Size ${selector.list.sizes.bottomHeightCoordinates}")
                verbalLog("DonationScreenLayout::setListSize", "initial Height ${selector.list.sizes.animInitialHeight}")
                verbalLog("DonationScreenLayout::setListSize", "target Height ${selector.list.sizes.animTargetHeight}")
            }
        }
    }
}