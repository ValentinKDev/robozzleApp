package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonRegister(enable: Boolean, name: String, password: String, vm: RegisterLoginViewModel, navigator: Navigator) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val showErrorMessage by vm.showToast.collectAsState()
    val ctxt = LocalContext.current

//    val keyboard = KeyboardActions {  }
////To hide keyboard
////To show keyboard
//    keyboardController?.show()
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
                keyboardController?.hide()
                vm.registerOnClickListner(navigator)
            },
            enabled = enable
        ) {
//            Text(text = "Register", Modifier.align(Alignment.Center))
            Text(text = "Register")
        }
        if (showErrorMessage > 0)
            Toast.makeText( ctxt, vm.getConnectionErrorMessage(), Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonLogin(enable: Boolean, name: String, password: String, vm: RegisterLoginViewModel, navigator: Navigator) {
    val keyboardController = LocalSoftwareKeyboardController.current
//    val connectionEstablished by vm.connectionEstablished.collectAsState()
//    val showErrorMessage by vm.canNotLog.collectAsState()
    val showErrorMessage by vm.showToast.collectAsState()
    val ctxt = LocalContext.current

//    errorLog("connectionEstablished", "${}")

//    if (connectionEstablished)
//        NavViewModel(navigator).navigateTo(
//            UserDataStoreViewModel(ctxt).getName()?.let { Screens.RegisterLogin }?: Screens.UserInfo
//        )
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
                keyboardController?.hide()
                vm.loginOnClickListner(navigator)
            },
            enabled = enable
        ) {
            Text(text = "Login")
        }
    }
//    if (showErrorMessage > 0) Toast.makeText(ctxt, "Can't reach Servers", Toast.LENGTH_LONG).show()
//    if (showErrorMessage < 0) Toast.makeText(ctxt, "Wrong login", Toast.LENGTH_LONG).show()
    if (showErrorMessage != 0)
//        Toast.makeText(ctxt, vm.getConnectionErrorMessage(), Toast.LENGTH_SHORT).show()
        Toast.makeText(ctxt, "Nope", Toast.LENGTH_SHORT).show()

}
