package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
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

    Text(text = "UserInfoScreen")
    userVM.getUserInDataStore()
    //return positiv so no problem so navigate UserInfo Screen
//            navController.navigate(Screens.ProfilScreen.route)
    //loading screen
    //todo: Loading Screen
    //ask server token with name and pass
    //todo: is coroutine lauchn by trigering the ui dangerous ?
//    GlobalScope.launch { usrinfoViewModel.GetAToken() }

    //ask server User id with token
//            vm.saveUserInDatastore(registerLoginVM.)

}

class UserInfoViewModel(): ViewModel() {

    private val _tokenJwt = MutableStateFlow<String>("")
    val tokenJwt: StateFlow<String> = _tokenJwt

    //todo : do i have to store the token?
    suspend fun GetAToken() {
        withContext(Dispatchers.IO) {
//            val tokenService = JWTTokenService.create(registLogVM.name.value, registLogVM.password.value)
//            _tokenJwt.value = tokenService.getJwtToken(UserRequest(registLogVM.name.value, registLogVM.password.value))
        }
    }
}