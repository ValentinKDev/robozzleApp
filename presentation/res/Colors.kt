package com.mobilegame.robozzle.presentation.res

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blueListDar
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blueListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.errorColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayListDark
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greenListDark
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greenListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.redListDark
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.redListLight

fun mapCaseColorList(toRecognize: Char, darkerFilter: Boolean = false): List<Color> {
    return when (toRecognize) {
        'g' -> if (darkerFilter) grayListDark else grayListLight
        'R' -> if (darkerFilter) redListDark else redListLight
        'B' -> if (darkerFilter) blueListDar else blueListLight
        'G' -> if (darkerFilter) greenListDark else greenListLight
        else -> errorColor
    }
}