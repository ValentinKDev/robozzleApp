package com.mobilegame.robozzle.presentation.res

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.ui.graphics.Color

//sealed class MyColors(color: Color) {
//}

inline class MyColor(val color: Color){
    companion object {
        val white0 = Color(0xFFFFFFFF)
        val white1 = Color(0xDFFFFFFF)
        val white2 = Color(0xBFFFFFFF)
        val white3 = Color(0x9FFFFFFF)
        val white4 = Color(0x7FFFFFFF)
        val white5 = Color(0x5FFFFFFF)

        val whiteDark0 = Color(0xFFFFFFFF)
        val whiteDark1 = Color(0xFFEEEEEE)
        val whiteDark2 = Color(0xFFDDDDDD)
        val whiteDark3 = Color(0xFFCCCCCC)
        val whiteDark4 = Color(0xFFBBBBBB)
        val whiteDark5 = Color(0xFFAAAAAA)
        val whiteDark6 = Color(0xFF999999)
        val whiteDark6Plus = Color(0xFF959595)
        val whiteDark7 = Color(0xFF888888)
        val whiteDark8 = Color(0xFF777777)
        val whiteDark9 = Color(0xFF666666)

        val gray0 = Color(0xFF888888)
        val gray1 = Color(0xDF888888)
        val gray2 = Color(0xBF888888)
        val gray3 = Color(0x9F888888)
        val gray4 = Color(0x7F888888)
        val gray5 = Color(0x5F888888)
        val gray6 = Color(0x3F888888)
        val gray7 = Color(0x1F888888)
        val gray8 = Color(0x0E888888)
        val gray9 = Color(0x0C888888)
        val black7 = Color(0x1F000000)
        val black8 = Color(0x0E000000)
        val black9 = Color(0x0C000000)
        val grayDark0 = Color(0xFF888888)
        //val grayDark0 = Color(0xFF888888)
        val grayDark1_0 = Color(0xFF787878)
        val grayDark1_1 = Color(0xFF767676)
        val grayDark1_2 = Color(0xFF747474)
        val grayDark1_3 = Color(0xFF727272)
        val grayDark1_4 = Color(0xFF707070)
        val grayDark2 = Color(0xFF686868)
        val grayDark3 = Color(0xFF585858)
        val grayDark4 = Color(0xFF484848)
        val grayDark5 = Color(0xFF383838)
        val grayDark6 = Color(0xFF343434)
        val grayDark7 = Color(0xFF282828)
        val grayDark8 = Color(0xFF191919)
        val grayDark9 = Color(0xFF101010)
        fun grayDark(darker: Int, shade: Int? = null): Color {
            var grayTint: Long = 0xFF888888
            val darkTint = 0x00090909
            val shadeTint = 0x00020202
            grayTint -= darkTint * darker
            shade?.let { grayTint -= (shadeTint * it) }
            return Color(grayTint)
        }



        val green0 = Color(0xFF00FF00)
        val green1 = Color(0xDF00FF00)
        val green2 = Color(0xBF00FF00)
        val green3 = Color(0x9F00FF00)
        val green4 = Color(0x7F00FF00)
        val green5 = Color(0x5F00FF00)
        val green6 = Color(0x3F00FF00)
        val green7 = Color(0x1F00FF00)
        val green8 = Color(0x0E00FF00)
        val green9 = Color(0x0C00FF00)
        val green10 = Color(0x0800FF00)
        val green11 = Color(0x0400FF00)
        val green12 = Color(0x0100FF00)
        val greendark0 = Color(0xFF00FF00)
        val greendark1 = Color(0xFF00DF00)
        val greendark2 = Color(0xFF00CF00)
        val greendark3 = Color(0xFF00BF00)
        val greendark4 = Color(0xFF00AF00)
        val greendark5 = Color(0xFF009F00)
        val greendark6 = Color(0xFF008F00)
        val greendark7 = Color(0xFF007F00)
        val greendark8 = Color(0xFF006F00)
        val greendark9 = Color(0xFF005F00)
        val greendark10 = Color(0xFF004F00)
        val greendark11 = Color(0xFF003F00)
        val greendark12 = Color(0xFF002F00)

        val blue0 = Color(0xFF0000FF)
        val blue1 = Color(0xDF0000FF)
        val blue2 = Color(0xBF0000FF)
        val blue3 = Color(0x9F0000FF)
        val blue4 = Color(0x7F0000FF)
        val blue5 = Color(0x5F0000FF)
        val blue6 = Color(0x3F0000FF)
        val blue7 = Color(0x1F0000FF)
        val blue8 = Color(0x0E0000FF)
        val blue9 = Color(0x0C0000FF)
        val blueDark0 = Color(0xFF0000FF)
        val blueDark1 = Color(0xFF0000EF)
        val blueDark2 = Color(0xFF0000DF)
        val blueDark3 = Color(0xFF0000CF)
        val blueDark4 = Color(0xFF0000BF)
        val blueDark5 = Color(0xFF0000AF)
        val blueDark6 = Color(0xFF00009F)
        val blueDark7 = Color(0xFF000080)
        val blueDark8 = Color(0xFF00007F)
        val blueDark9 = Color(0xFF00008F)
        val blueDark10 = Color(0xFF00007F)
        val blueDark11 = Color(0xFF00006F)
        val blueDark12 = Color(0xFF00005F)

        val yellow0 = Color(0xFFFFFF00)
        val yellow1 = Color(0xDFFFFF00)
        val yellow2 = Color(0xBFFFFF00)
        val yellow3 = Color(0x9FFFFF00)
        val yellow4 = Color(0x7FFFFF00)
        val yellow5 = Color(0x5FFFFF00)
        val yellow6 = Color(0x3FFFFF00)
        val yellow7 = Color(0x1FFFFF00)
        val yellow8 = Color(0x0EFFFF00)
        val yellow9 = Color(0x0CFFFF00)
        val yellow10 = Color(0x09FFFF00)
        val yellow11 = Color(0x05FFFF00)
        val yellow12 = Color(0x02FFFF00)
        val yellowDark0 = Color(0xFFEFEF00)
        val yellowDark1 = Color(0xFFDFDF00)
        val yellowDark2 = Color(0xFFCFCF00)
        val yellowDark3 = Color(0xFFBFBF00)
        val yellowDark4 = Color(0xFFAFAF00)
        val yellowDark5 = Color(0xFF9F9F00)
        val yellowDark6 = Color(0xFF8F8F00)
        val yellowDark7 = Color(0xFF7F7F00)
        val yellowDark8 = Color(0xFF6F6F00)
        val yellowDark9 = Color(0xFF5F5F00)
        val yellowDark10 = Color(0xFF4F4F00)
        val yellowDark11 = Color(0xFF3F3F00)
        val yellowDark12 = Color(0xFF2F2F00)

        val red0= Color(0xFFFF0000)
        val red1= Color(0xDFFF0000)
        val red2= Color(0xBFFF0000)
        val red3= Color(0x9FFF0000)
        val red4= Color(0x7FFF0000)
        val red5= Color(0x5FFF0000)
        val red6= Color(0x3FFF0000)
        val red7= Color(0x1FFF0000)
        val red8= Color(0x0EFF0000)
        val red9= Color(0x0CFF0000)
        val red10= Color(0x0AFF0000)
        val redDark0= Color(0xFFFF0000)
        val redDark1= Color(0xFFDF0000)
        val redDark2= Color(0xFFCF0000)
        val redDark3= Color(0xFFBF0000)
        val redDark4= Color(0xFFAF0000)
        val redDark5= Color(0xFF9F0000)
        val redDark6= Color(0xFF8F0000)
        val redDark7= Color(0xFF7F0000)
        val redDark8= Color(0xFF6F0000)
        val redDark9= Color(0xFF5F0000)
        val redDark10= Color(0xFF5F0000)
        val redDark11= Color(0xFF4F0000)
        val redDark12= Color(0xFF3F0000)
    }
}

