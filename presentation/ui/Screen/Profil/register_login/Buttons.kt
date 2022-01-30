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
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.RegisterScreenViewModel
import com.mobilegame.robozzle.domain.state.UserConnection
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun ButtonRegister(enable: Boolean, name: String, password: String, vm: RegisterScreenViewModel, navController: NavController) {
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
                vm.registerOnClickListner()
            },
            enabled = enable
        ) {
//            Text(text = "Register", Modifier.align(Alignment.Center))
            Text(text = "Register")
        }
    }
}

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun ButtonLogin(enable: Boolean, name: String, password: String, vm: RegisterScreenViewModel, navController: NavController) {
    val connectionEstablished by vm.connectionEstablished.collectAsState()
    val showErrorMessage by vm.canNotLog.collectAsState()
//    val error

    errorLog("connectionEstablished", "${connectionEstablished}")

    if (connectionEstablished) navController.navigate(Screens.ProfilScreen.route)
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
                vm.loginOnClickListner()
            },
            enabled = enable
        ) {
            Text(text = "Login")
        }
    }
    if (showErrorMessage > 0) Toast.makeText(LocalContext.current, "Can't reach Servers", Toast.LENGTH_LONG).show()
    if (showErrorMessage < 0) Toast.makeText(LocalContext.current, "Wrong login", Toast.LENGTH_LONG).show()
}
