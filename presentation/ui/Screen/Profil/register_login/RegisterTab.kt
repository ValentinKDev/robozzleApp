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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.state.UserConnection
import com.mobilegame.robozzle.domain.state.UserConnectionState
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.RegisterLoginScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.Tab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.UserInfoScreen
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun RegisterTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {
    RegisteringElements(vm, navigator)
}

@Composable
fun RegisteringElements(vm: RegisterLoginViewModel, navigator: Navigator) {
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

        ButtonRegister(enable = isValidName && isValidPassword, name = name, password = password, vm = vm, navigator)
    }
}
//@Composable
//fun RegisterTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {
//    val connectionState by vm.userConnectionState.collectAsState(UserConnectionState.NotConnected)
//
//    infoLog("RegisterTab", "connectionState $connectionState")
//    when (connectionState) {
//        UserConnection.NoUser.state ->  {
//            RegisteringElements(vm, navigator)
//        }
//        UserConnection.NotCreated.state ->  {
//            Toast.makeText(LocalContext.current, "${vm.name.value} already exist", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//            vm.setUserConnectionState(UserConnection.NoUser.state)
//        }
//        //todo : personalize ret from server for weird error or just an already exsiting name
//        UserConnection.Created.state -> {
//            Toast.makeText(LocalContext.current, "Can't connect to the server", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//        }
//        UserConnection.NotConnected.state ->  {
//            Toast.makeText(LocalContext.current, "Server facing some issue with your profil", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//        }
//        UserConnection.CreatedAndVerified.state -> {
//            vm.setUserConnectionState(UserConnection.Connected.state)
////            NavigationVM().goTo(destination = Screens.Profil, navigator = navigator)
////            NavViewModel(navigator).navigateTo(Screens.Profil)
//
//            NavViewModel(navigator).navigateTo(
//                if (UserDataStoreViewModel(LocalContext.current).getName().isNullOrBlank())
//                    Screens.RegisterLogin
//                else
//                    Screens.UserInfo
//            )
////                NavViewModel(navigator).navigateTo(Screens.RegisterLogin)
////                RegisterLoginScreen(navigator, Tab())
////            else
////                UserInfoScreen(navigator)
////            vm.navigation(Screens.Profil, navigator)
////            navigator.navigate(Screens.Profil)
//        }
//        UserConnection.Connected.state -> {
//        }
//        else -> errorLog("Register Tab", "Error from the connectionState / value $connectionState")
//    }
//}
