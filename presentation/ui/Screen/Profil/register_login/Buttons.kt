package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.RegisterScreenViewModel
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
    Box(Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
            ,
            onClick = {
                infoLog("Login", "onClick")
            },
            enabled = enable
        ) {
            Text(text = "Login")
        }
    }
}
