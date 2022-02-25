package com.mobilegame.robozzle.domain.model.window

import android.content.Context
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.UserInfosButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class UserInfosWindowInfos(): ViewModel() {

    val firstPartScreenWeight = 0.1f
    val secondPartScreenWeight = 0.9f

    val logoutButtonRatioHeight = 0.05f

    fun userInfosButtonSize(button: UserInfosButton, context: Context, density: Density): Size = runBlocking(Dispatchers.Default) {
        val screenWidth: Float = context.resources.displayMetrics.widthPixels / density.density
        val screenHeight: Float = context.resources.displayMetrics.heightPixels / density.density

        when (button) {
            UserInfosButton.LogOut -> Size(width = logoutButtonRatioHeight * screenWidth, height = logoutButtonRatioHeight * screenWidth)
            else -> Size(width = 0f, height = 0f)
        }
    }
}