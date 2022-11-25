package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj
import com.mobilegame.robozzle.utils.Extensions.toDp
import io.ktor.utils.io.*

object LevelsScreenByDifficultyLayout {
    var tuto = TutoObj
    var levelOverview = LevelOverview
    var header = HeaderLevelsScreen

    object HeaderLevelsScreen {
        val ratios = RatiosHeaderLevelsScreen
        val sizes = SizesHeaderLevelsScreen

        object RatiosHeaderLevelsScreen {
            const val height = 0.15F
        }
        object SizesHeaderLevelsScreen {
            var heightDp = Dp.Unspecified
        }
    }
    object LevelOverview {
        val ratios = RatiosLevelOverview
        val padding = PaddingLevelOverview
        val sizes = SizesLevelOverview
        val colors = ColorsLevelOverview

        object RatiosLevelOverview {
            const val height = 0.14F
            const val topPadd = 0.008F
            const val bottomPadd = 0.008F
            const val sidePadd = 0.018F
            const val mapWeight = 1.2F
            const val descriptionWeight = 1.6F
            const val stateIconWeight = 0.5F
            const val rankIconWeight = 0.7F
        }
        object SizesLevelOverview {
            var height = 0F
            var heightDp = Dp.Unspecified
        }
        object PaddingLevelOverview {
            var top = Dp.Unspecified
            var bottom = Dp.Unspecified
            var side = Dp.Unspecified
        }
        object ColorsLevelOverview {
            val backgroundColor = MyColor.grayDark3
            val filterIntial = Color.Transparent
            val filterTarget = MyColor.white9
        }
    }

    private var widthFull = 0
    private var heightFull = 0
    private var density = 0F

    private fun initHeader() {
        header.sizes.heightDp = (header.ratios.height * heightFull).toDp(density)

        displayUIData?.let {
            verbalLog("LevelsScreenByDiffLayout::initHeader", "start")
            infoLog("LevelsScreenByDiffLayout::initHeader", "height = ${header.sizes.heightDp}")
        }
    }
    private fun initLevelOverview() {
        levelOverview.sizes.height = heightFull * levelOverview.ratios.height
        levelOverview.sizes.heightDp = levelOverview.sizes.height.toDp(density)
        levelOverview.padding.top = (heightFull * levelOverview.ratios.topPadd).toDp(density)
        levelOverview.padding.bottom = (heightFull * levelOverview.ratios.bottomPadd).toDp(density)
        levelOverview.padding.side = (widthFull * levelOverview.ratios.sidePadd).toDp(density)

        displayUIData?.let {
            verbalLog("LevelsScreenByDiffLayout::initLevelOverview", "start")
            infoLog("LevelsScreenByDiffLayout::initLevelOverview", "height = ${levelOverview.sizes.height}")
            infoLog("LevelsScreenByDiffLayout::initLevelOverview", "height = ${levelOverview.sizes.heightDp}")
            infoLog("LevelsScreenByDiffLayout::initLevelOverview", "padd Top = ${levelOverview.padding.top}")
            infoLog("LevelsScreenByDiffLayout::initLevelOverview", "padd Bottom = ${levelOverview.padding.bottom}")
            infoLog("LevelsScreenByDiffLayout::initLevelOverview", "padd Side = ${levelOverview.padding.side}")
        }
    }

    fun create(context: Context): LevelsScreenByDifficultyLayout {
        errorLog("LevelsScreenByDiffLayout::create", "start")
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density

        tuto = TutoObj.create()
        initHeader()
        initLevelOverview()

        return this
    }
}