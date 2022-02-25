package com.mobilegame.robozzle.domain.model.window

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.res.gray4
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.UserInfosButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class UserInfoScreenData(): ViewModel() {

    /** Parts */
    val firstPartScreenWeight = 0.1f
    val secondPartScreenWeight = 0.9f

    /** Elements */
    val logoutButtonRatioHeight = 0.05f

    val cardNameColor = grayDark3
    val cardNameElevation = 15.dp
//    val cardNamePadding = 0.07f
    //todo: test the name with the maximum name lenght
    val cardNameRatioWidth = 0.30f
    val cardNameRatioHeight = 0.075f

//    private val _offsetMap = MutableStateFlow<MutableMap<Int, Offset>>(mapOf<Int, Offset>().toMutableMap())
//    val offsetMap = _offsetMap.asStateFlow()
//    fun getOffset(buttonId: Int): Offset = offsetMap.value[buttonId] ?: Offset(0f,0f)
//    fun setOffset(buttonId: Int, offset: Offset) {
//        _offsetMap.value[buttonId] = offset
//    }

    fun userInfosButtonSize(button: UserInfosButton, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (button) {
            UserInfosButton.LogOut -> Size(width = logoutButtonRatioHeight * screenWidth, height = logoutButtonRatioHeight * screenWidth)
            UserInfosButton.NameCard -> Size(width = cardNameRatioWidth * screenWidth, height = cardNameRatioHeight * screenHeight)
            else -> Size(width = 0f, height = 0f)
        }
    }
}