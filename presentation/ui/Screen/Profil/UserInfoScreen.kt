package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun UserInfoScreen(userViewModel: UserViewModel) {
    infoLog("Launch", "UserInfoScreen()")

    //return positiv so no problem so navigate UserInfo Screen
//            navController.navigate(Screens.ProfilScreen.route)
    //loading screen
    //todo: Loading Screen
    //ask server token with name and pass
    //todo: is coroutine lauchn by trigering the ui dangerous ?
    GlobalScope.launch { userViewModel.GetAToken() }

    //ask server User id with token
//            vm.saveUserInDatastore(registerLoginVM.)

}