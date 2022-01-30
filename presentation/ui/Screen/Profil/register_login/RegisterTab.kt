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
import com.mobilegame.robozzle.domain.state.UserConnection
import com.mobilegame.robozzle.domain.state.UserConnectionState
import com.mobilegame.robozzle.domain.model.Screen.RegisterScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("CoroutineCreationDuringComposition")
@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisterTab(navController: NavController, vm: RegisterScreenViewModel) {
    val connectionState by vm.userConnectionState.collectAsState(UserConnectionState.NotConnected)

    infoLog("RegisterTab", "connectionState $connectionState")
    when (connectionState) {
        UserConnection.NoUser.state ->  {
            RegisteringElements(vm, navController = navController)
        }
        UserConnection.NotCreated.state ->  {
            Toast.makeText(LocalContext.current, "${vm.name.value} already exist", Toast.LENGTH_LONG).show()
            RegisteringElements(vm, navController = navController)
            vm.setUserConnectionState(UserConnection.NoUser.state)
        }
        //todo : personalize ret from server for weird error or just an already exsiting name
        UserConnection.Created.state -> {
            Toast.makeText(LocalContext.current, "Can't connect to the server", Toast.LENGTH_LONG).show()
            RegisteringElements(vm, navController = navController)
        }
        UserConnection.NotConnected.state ->  {
            Toast.makeText(LocalContext.current, "Server facing some issue with your profil", Toast.LENGTH_LONG).show()
            RegisteringElements(vm, navController = navController)
        }
        UserConnection.CreatedAndVerified.state -> {
            vm.setUserConnectionState(UserConnection.Connected.state)
            navController.navigate(Screens.ProfilScreen.route)
        }
        UserConnection.Connected.state -> {
        }
        else -> errorLog("Register Tab", "Error from the connectionState / value $connectionState")
    }
}

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisteringElements(vm: RegisterScreenViewModel, navController: NavController) {
    val name by remember(vm) {vm.name}.collectAsState( initial = "" )
    val password by remember(vm) {vm.password}.collectAsState( initial = "" )
    val isValidName: Boolean = vm.nameIsValid.value
    val isValidPassword: Boolean = vm.passwordIsValid.value

    Column( )
    {
        //todo: check if the inputs are resistant to application switching
        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = name,
            onValueChange = { vm.handleInputName(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = { Text(text = vm.getNameInputFieldLabel()) },
            isError = !isValidName,
        )

        if (!isValidName) Text("Player name \"$name\" is invalid", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            label = { Text(vm.getPasswordInputFieldLabel()) },
            value = password,
            onValueChange = {
                vm.trimPasswordInput(it)
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
