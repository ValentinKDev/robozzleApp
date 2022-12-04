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
    val keyboardSpace = KeyboardSpace
    val list = List

    object Header {
        val sizes = SizesHeader
        val ratios = RatiosHeader
        val colors = ColorsHeader
        val text = TextHeader

        object RatiosHeader {
            var heightWeight = 1F
            var heightPercentage = 0F
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
        val padding = PaddingPresentation
        val text = DonationScreenText
        val colors = ColorsPresentation

        object RatiosPresentation {
            const val heightWeight = 4.5F
            var heightPercentage = 0F
            const val startPadding = 0.08F
            const val endPadding = 0.05F
            const val bottomPadding = 0.05F
        }
        object PaddingPresentation {
            var startPadding = Dp.Unspecified
            var endPadding = Dp.Unspecified
            var bottomPadding = Dp.Unspecified
        }
        object ColorsPresentation {
            var text = MyColor.applicationText
        }
    }

    object SelectorDonation {
        val ratios = RatiosSelector
        val sizes = SizeSelector
        val colors = ColorsSelector
        val shape = ShapeSelector

        object RatiosSelector {
            const val heightWeight = 0.8F
            var heightPercentage = 0F
        }
        object SizeSelector {
            var heightDp = Dp.Unspecified
        }
        object ShapeSelector {
            val corner = 5.dp
            val elevation = 20.dp
        }
        object ColorsSelector {
            val background = MyColor.applicationSurfaceDarkDark
        }
    }

    object List {
        val ratios = RatiosList
        val sizes = SizesList
        val colors = ColotsList

        object RatiosList {
            var heightWeight = 0F
            var heightPercentage = 0F
            const val numberOfElementDisplayed = 7.5F
            const val sidePadding = 0.085F
        }
        object SizesList {
            var bottomHeightCoordinates: Float? = null
            var animInitialHeight: Int = 0
            var animTargetHeight: Int = 0

            var height = 0F
            var elementHeightDp = Dp.Unspecified
            val elementDelimiter = 2.dp
        }
        object ColotsList {
            var background = MyColor.applicationSurfaceDark
        }
    }

    object KeyboardSpace {
        val ratios = RatioKeyboardSpace
        val sizes = SizesKeyboardSpace

        object RatioKeyboardSpace {
            const val heightWeight = 4.7F
            var heightPercentage = 0F
        }
        object SizesKeyboardSpace {
            var heightDp = Dp.Unspecified
        }
    }


    private var widthFull = 0
    private var heightFull = 0
    private var density = 0F
    private var allWeights = 0F

    fun initList() {
        list.ratios.heightWeight = presentation.ratios.heightWeight
        list.ratios.heightPercentage = list.ratios.heightWeight / allWeights
        list.sizes.height = list.ratios.heightPercentage * heightFull
        list.sizes.elementHeightDp = (list.sizes.height / (list.ratios.numberOfElementDisplayed)).toDp(density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::initList", "Start")
            verbalLog("DonationScreenLayout::initList", "height% ${list.ratios.heightPercentage}")
            verbalLog("DonationScreenLayout::initList", "height ${list.sizes.height}")
            verbalLog("DonationScreenLayout::initList", "element height ${list.sizes.elementHeightDp}")
        }
    }

    fun initKeyboardSpace() {
        keyboardSpace.ratios.heightPercentage = keyboardSpace.ratios.heightWeight / allWeights
        keyboardSpace.sizes.heightDp = (keyboardSpace.ratios.heightPercentage * heightFull).toDp( density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::iniKeyboardSpace", "Start")
            verbalLog("DonationScreenLayout::iniKeyboardSpace", "height% ${keyboardSpace.ratios.heightPercentage}")
            verbalLog("DonationScreenLayout::iniKeyboardSpace", "heightDp ${keyboardSpace.sizes.heightDp}")
        }
    }

    private fun initSelector() {
        selector.ratios.heightPercentage = selector.ratios.heightWeight / allWeights
        selector.sizes.heightDp = (heightFull * selector.ratios.heightPercentage).toDp(density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::initSelector", "Start")
            verbalLog("DonationScreenLayout::initSelector", "height% ${selector.ratios.heightPercentage}")
            verbalLog("DonationScreenLayout::initSelector", "heightDp ${selector.sizes.heightDp}")
        }
    }

    private fun initHeader() {
        header.ratios.heightPercentage = header.ratios.heightWeight / allWeights
        header.sizes.height = header.ratios.heightPercentage * heightFull
        header.sizes.text = header.sizes.height * header.ratios.text
        header.sizes.textDp = header.sizes.text.toDp(density)
        header.sizes.textSp = header.sizes.text.toSp(density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::initHeader", "Start")
            verbalLog("DonationScreenLayout::initHeader", "heightPercentage ${header.ratios.heightPercentage}")
            verbalLog("DonationScreenLayout::initHeader", "height ${header.sizes.height}")
        }
    }
    private fun initPresentation() {
        presentation.ratios.heightPercentage = presentation.ratios.heightWeight / allWeights
        presentation.padding.bottomPadding = (presentation.ratios.bottomPadding * heightFull).toDp( density)
        presentation.padding.startPadding = (presentation.ratios.startPadding * widthFull).toDp( density)
        presentation.padding.endPadding = (presentation.ratios.endPadding * widthFull).toDp( density)

        displayUIData?.let {
            infoLog("DonationScreenLayout::iniPresentation", "start")
            verbalLog("DonationScreenLayout::iniPresentation", "heightPercentage ${presentation.ratios.heightPercentage}")
            verbalLog("DonationScreenLayout::iniPresentation", "startPaddingDp ${presentation.padding.startPadding}")
            verbalLog("DonationScreenLayout::iniPresentation", "endPaddingDp ${presentation.padding.endPadding}")
            verbalLog("DonationScreenLayout::iniPresentation", "bottomPadding ${presentation.padding.bottomPadding}")
        }
    }
    fun create(context: Context): DonationScreenLayout {
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density
        allWeights = header.ratios.heightWeight + presentation.ratios.heightWeight + selector.ratios.heightWeight + keyboardSpace.ratios.heightWeight

        displayUIData?.let {
            infoLog("DonationScreenLayout::create", "Start")
            verbalLog("DonationScreenLayout::create", "heightFull ${heightFull}")
            verbalLog("DonationScreenLayout::create", "widthFull ${widthFull}")
            verbalLog("DonationScreenLayout::create", "density ${density}")
            verbalLog("DonationScreenLayout::create", "allWeights ${allWeights}")
        }

        initHeader()
        initPresentation()
        initSelector()
        initKeyboardSpace()
        initList()
        return this
    }

    fun setListSize(layoutCoordinates: LayoutCoordinates) {
        list.sizes.bottomHeightCoordinates ?: let {
            list.sizes.bottomHeightCoordinates = layoutCoordinates.boundsInRoot().height / density
            list.sizes.animInitialHeight = (list.sizes.bottomHeightCoordinates!! * 0.5F).toInt()
            list.sizes.animTargetHeight = (layoutCoordinates.boundsInRoot().height * 2).toInt()
            displayUIData?.let {
                verbalLog("DonationScreenLayout::setListSize", "layoutCoordinates ${layoutCoordinates.boundsInRoot().height}")
                verbalLog("DonationScreenLayout::setListSize", "list layout Size ${list.sizes.bottomHeightCoordinates}")
                verbalLog("DonationScreenLayout::setListSize", "initial Height ${list.sizes.animInitialHeight}")
                verbalLog("DonationScreenLayout::setListSize", "target Height ${list.sizes.animTargetHeight}")
            }
        }
    }
}