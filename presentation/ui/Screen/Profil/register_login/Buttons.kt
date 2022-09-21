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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@Composable
fun ButtonRegister(enable: Boolean, name: String, password: String, vm: RegisterLoginViewModel, navigator: Navigator) {
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
//                vm.registerOnClickListner()
            },
            enabled = enable
        ) {
//            Text(text = "Register", Modifier.align(Alignment.Center))
            Text(text = "Register")
        }
    }
}

@Composable
fun ButtonLogin(enable: Boolean, name: String, password: String, vm: RegisterLoginViewModel, navigator: Navigator) {
    val connectionEstablished by vm.connectionEstablished.collectAsState()
    val showErrorMessage by vm.canNotLog.collectAsState()
    val ctxt = LocalContext.current

    errorLog("connectionEstablished", "${connectionEstablished}")

    if (connectionEstablished)
        NavViewModel(navigator).navigateTo(
            UserDataStoreViewModel(ctxt).getName()?.let { Screens.RegisterLogin }?: Screens.UserInfo
        )
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
//                vm.loginOnClickListner()
            },
            enabled = enable
        ) {
            Text(text = "Login")
        }
    }
    if (showErrorMessage > 0) Toast.makeText(ctxt, "Can't reach Servers", Toast.LENGTH_LONG).show()
    if (showErrorMessage < 0) Toast.makeText(ctxt, "Wrong login", Toast.LENGTH_LONG).show()
}
