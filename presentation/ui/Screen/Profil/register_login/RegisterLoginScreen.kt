package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.User.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.LoginTab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.RegisterTab
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@DelicateCoroutinesApi
@InternalCoroutinesApi
@Composable
//fun RegisterLoginScreen(navController: NavController, userVM: UserViewModel = viewModel(), registerLoginVM: RegisterLoginViewModel = viewModel()) {
fun RegisterLoginScreen(navController: NavController, userVM: UserViewModel) {
    infoLog("Launch", "RegisterLoginScreen")
    Column {
        RegisterLoginTabsHead(userVM = userVM)
        if (userVM.tab == 1) {
            infoLog("tab", "login")
            LoginTab(userVM = userVM)
        }
        else {
            infoLog("tab", "resgister")
            RegisterTab(navController, userVM)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@InternalCoroutinesApi
@Composable
fun RegisterLoginTabsHead(userVM: UserViewModel) {
    val tabSelected by userVM.registLogVM.tabSeclected.collectAsState(1)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.0f)
                //todo: could avoid the test on tabselect by using an boolean variable like isRegisterTabSelected
                .background(if (tabSelected == 1) Color.Transparent else Color.Gray)
//                .background(if (userVM.registLogVM.IsRegisterTabSelected()) Color.Gray else Color.Transparent)
//                    .height(70.dp)
                .clickable {
                    userVM.registLogVM.SelectLoginTab()
                }

        ) {
            Text(text = "Register", Modifier.align(Alignment.Center))
        }

        Box(
            modifier = Modifier
                .weight(1.0f)
//                .background(if (userVM.registLogVM.IsLoginTabSelected()) Color.Gray else Color.Transparent)
                .background(if (tabSelected == 2) Color.Transparent else Color.Gray)
                .clickable {
                    userVM.registLogVM.SelectRegisterTab()
                }
        ) {
            Text(text = "Login ${userVM.registLogVM.tabSeclected.value}", Modifier.align(Alignment.Center))
        }
    }
}
//todo: can't register as none cuz its a defaut variable/ or change the variable

