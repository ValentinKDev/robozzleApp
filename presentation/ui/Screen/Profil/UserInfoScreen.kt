package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.domain.model.UserViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun UserInfoScreen(navController: NavController, userVM: UserViewModel = viewModel()) {
    infoLog("Launch", "UserInfoScreen()")

    Column() {
        Text(text = "UserInfoScreen")
        Text(text = "user name ${userVM.userDataStoreVM.getUser().name}")
    }
}

//class UserInfoViewModel(): ViewModel() {
//
//    private val _tokenJwt = MutableStateFlow<String>("")
//    val tokenJwt: StateFlow<String> = _tokenJwt
//
//    suspend fun GetAToken() {
//        withContext(Dispatchers.IO) {
////            val tokenService = JWTTokenService.create(registLogVM.name.value, registLogVM.password.value)
////            _tokenJwt.value = tokenService.getJwtToken(UserRequest(registLogVM.name.value, registLogVM.password.value))
//        }
//    }
//}