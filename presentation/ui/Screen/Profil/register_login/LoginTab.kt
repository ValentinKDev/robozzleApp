package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.domain.model.Screen.RegisterScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.ButtonRegister
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun LoginTab(navController: NavController, vm: RegisterScreenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
            Text(text = "Login : ${vm.tabSeclected.value}")
    }

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
