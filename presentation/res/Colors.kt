package com.mobilegame.robozzle.presentation.res

import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blueListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.errorColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greenListLight
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.redListLight

fun mapCaseColorList(toRecognize: Char): List<Color> {
    return when (toRecognize) {
//        'g' -> if (darkerFilter) grayListDark else grayListLight
//        'R' -> if (darkerFilter) redListDark else redListLight
//        'B' -> if (darkerFilter) blueListDar else blueListLight
//        'G' -> if (darkerFilter) greenListDark else greenListLight
        'g' -> grayListLight
        'R' -> redListLight
        'B' -> blueListLight
        'G' -> greenListLight
        else -> errorColor
    }
}