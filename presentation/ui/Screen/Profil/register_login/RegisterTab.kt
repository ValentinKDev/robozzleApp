package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.presentation.res.whiteDark2
import com.mobilegame.robozzle.presentation.ui.Navigator

@Composable
fun RegisterTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {
    val name by remember(vm) {vm.name}.collectAsState( initial = "" )
    val password by remember(vm) {vm.password}.collectAsState( initial = "" )
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
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
            colors = TextFieldDefaults.textFieldColors(backgroundColor = whiteDark2),
        )

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            label = { Text(vm.getPasswordInputFieldLabel()) },
            onValueChange = {
                vm.trimPasswordInput(it)
            },

            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            isError = !isValidPassword,
            enabled = isValidName,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = whiteDark2),
//            value = if (passwordVisible) password else vm.getPasswordProtected(password),
            value = password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "password unprotected" else "password protected"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, contentDescription = description)
                }
            }
        )


        Spacer(modifier = Modifier.height(50.dp))

        ButtonRegister(enable = isValidName && isValidPassword, name = name, password = password, vm = vm, navigator)
    }
}

//@Composable
//fun RegisterTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {
//    RegisteringElements(vm, navigator)
//}


//@Composable
//fun RegisterTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {
//    val connectionState by vm.userConnectionState.collectAsState(UserConnectionState.NotConnected)
//
//    infoLog("RegisterTab", "connectionState $connectionState")
//    when (connectionState) {
//        UserConnectionState.NoUser.str ->  {
//            RegisteringElements(vm, navigator)
//        }
//        UserConnectionState.NotCreated.str ->  {
//            Toast.makeText(LocalContext.current, "${vm.name.value} already exist", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//            vm.setUserConnectionState(UserConnectionState.NoUser.str)
//        }
//        //todo : personalize ret from server for weird error or just an already exsiting name
//        UserConnectionState.CreatedAndNotVerified.str -> {
//            Toast.makeText(LocalContext.current, "Can't connect to the server", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//        }
//        UserConnectionState.NotConnected.str ->  {
//            Toast.makeText(LocalContext.current, "Server facing some issue with your profil", Toast.LENGTH_LONG).show()
//            RegisteringElements(vm, navigator)
//        }
//        UserConnectionState.CreatedAndVerified.str -> {
//            vm.setUserConnectionState(UserConnectionState.Connected.str)
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
//        UserConnectionState.Connected.str -> {
//        }
//        else -> errorLog("Register Tab", "Error from the connectionState / value $connectionState")
//    }
//}