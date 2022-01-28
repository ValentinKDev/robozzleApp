package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.UserConnectionState
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.ButtonRegister
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("CoroutineCreationDuringComposition")
@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisterTab(navController: NavController, vm: UserViewModel) {
    val connectionState by vm.userConnectionSate.collectAsState(UserConnectionState.NotConnected)

    when (connectionState) {
        UserConnectionState.NoUser ->  {
            infoLog("RegisterTab", "connectionState not connected")
            RegisteringElements(vm, navController = navController)
        }
        //todo : personalize ret from server for weird error or just an already exsiting name
        UserConnectionState.NotConnected ->  {
            infoLog("RegisterTab", "connectionState not connected")
            RegisteringElements(vm, navController = navController)
            Toast.makeText(LocalContext.current, "Error impossible to registrate with this login", Toast.LENGTH_SHORT).show()
        }
        UserConnectionState.Created -> {
            infoLog("RegisterTab", "connectionState created")
            vm.newUserCreationProcess()
//            vm.getAToken(vm.registLogVM.name.value, vm.registLogVM.password.value)
//            vm.connectUserToServer(vm.registLogVM.name.value, vm.registLogVM.password.value)
        }
        UserConnectionState.Connected -> {
            infoLog("RegisterTab", "connectionState connected")
            navController.navigate(Screens.MainScreen.route)
        }
        else -> errorLog("Register Tab", "Error from the connectionState / value $connectionState")
    }
}

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisteringElements(vm: UserViewModel, navController: NavController) {
    val name by remember(vm.registLogVM) {vm.registLogVM.name}.collectAsState( initial = "" )
    val password by remember(vm.registLogVM) {vm.registLogVM.password}.collectAsState( initial = "" )
    val isValidName: Boolean = vm.registLogVM.nameIsValid.value
    val isValidPassword: Boolean = vm.registLogVM.passwordIsValid.value

    Column( )
    {
        //todo: check if the inputs are resistant to application switching
        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = name,
            onValueChange = { vm.registLogVM.handleInputName(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = { Text(text = vm.registLogVM.getNameInputFieldLabel()) },
            isError = !isValidName,
        )

        if (!isValidName) Text("Player name \"$name\" is invalid", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            label = { Text(vm.registLogVM.getPasswordInputFieldLabel()) },
            value = password,
            onValueChange = {
                vm.registLogVM.trimPasswordInput(it)
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            isError = !isValidPassword,
            enabled = isValidName,
        )

        Text("password : $password", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        ButtonRegister(enable = isValidName && isValidPassword, name = name, password = password, vm = vm, navController = navController)
    }
}
