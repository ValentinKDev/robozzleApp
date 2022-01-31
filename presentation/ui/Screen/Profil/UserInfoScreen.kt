package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.domain.model.User.UserInfoViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun UserInfoScreen(navController: NavController, userInfoViewModel: UserInfoViewModel = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")

    Column() {
        Text(text = "UserInfoScreen")
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "user name ${userInfoViewModel.name}")
    }
}