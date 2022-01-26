package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.User.ServerSend
import com.mobilegame.robozzle.domain.model.User.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.ButtonRegister
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisterTab(navController: NavController, vm: UserViewModel, registerLoginVM: RegisterLoginViewModel) {
    val serverReturn by registerLoginVM.registerReturn.collectAsState()


    when (serverReturn) {
        ServerSend.POSITIV.ret -> {
            navController.navigate(Screens.UserInfoScreen.route)
        }
        else -> {
            Box {
                //todo: may be just write a message under the Register button ???
                RegisteringElements(registerLoginVM = registerLoginVM)
                if (
                    serverReturn == ServerSend.ERROR200.ret
                    || serverReturn == ServerSend.ERROR300.ret
                    || serverReturn == ServerSend.ERROR400.ret
                    || serverReturn == ServerSend.ERROR500.ret
                    //exception server not availbale ?
                    || serverReturn == ServerSend.EXCETPTION.ret
                ) {
                    Row(
                        Modifier.fillMaxHeight().align(Alignment.Center)
                    ) {
                        Column(
                        ) {
                            Box(
                                Modifier
                                    .background(Color.Red)
                                    .width(250.dp)
                                    .height(150.dp)
                                    .clickable {
                                        registerLoginVM.resetRegisterReturn()
                                    }
//                                .clickable()
                            ){
                                Text("Error")
                            }
                        }
                    }
                }
            }
        }
    }
}

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RegisteringElements(registerLoginVM: RegisterLoginViewModel) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isValidName: Boolean = name.length >= 3
    val isValidPassword: Boolean = password.length >= 3

    Column( )
    {
        //todo: check if the inputs are resistant to application switching
        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = name,
            onValueChange = {
                name = it.trim()
                //todo: add tabulation and other weird shit in the unauthorized characters
                if (name.isNotEmpty() && "[./:*?<>|~#%&+{}-]".toRegex().matches(name[name.lastIndex].toString())) {
                    name = name.substringBefore(name[name.lastIndex])
                }
                infoLog("name input", name)
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = {
//                val diplayedText = "name or email"
                val label =
                    if (name.isEmpty()) "name or email"
                    else if (!isValidName) {
                        if (registerLoginVM.name.value.length < 3) { "your name can't be less than 3 characters" }
                        //todo: protect against illegal character
//                        else if (("[./:*?<>|~#%&+{}-]\t".toRegex()).find(name)) { "your name contains unauthorized characeter" }
//                        else if (name.con)k
//                        else if (name.contains("[./:*?<>|~#%&+{}-]".toRegex())) { "your name contains unauthorized characeter" }
                        else { "your name is incorrect" }
                    }
                    else "your login id :"
                Text(text = label)
            },
            isError = !isValidName,
        )

//        val textValidInfo = if (isValidName) "valid" else "invalid"
        if (name.isNotEmpty() && !isValidName) Text("Player name \"$name\" is invalid", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = password,
            onValueChange = {
                password = it.trim()
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = {
                val label =
                    if (password.isEmpty()) "password"
                else if (!isValidPassword) "your password can't be less than 3 characters"
                else ""
                Text(text = label)
            },
            enabled = isValidName,
        )

        Text("password : $password", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        ButtonRegister(enable = isValidName && isValidPassword, name = name, password = password, vm = registerLoginVM)
    }
}