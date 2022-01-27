package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
fun RegisterLoginScreen(navController: NavController, userVM: UserViewModel = viewModel(), registerLoginVM: RegisterLoginViewModel = viewModel()) {
    infoLog("Launch", "RegisterLoginScreen")
    Column {
        RegisterLoginTabs(userVM = userVM)
        if (userVM.tab == 1) {
            infoLog("tab", "login")
            LoginTab(userVM = userVM)
        }
        else {
            infoLog("tab", "resgister")
            RegisterTab(navController, userVM, registerLoginVM)
        }
//        DisplayProfilTabsHeader(userVM)
        Spacer(modifier = Modifier.height(50.dp))
//        if (selectedTab == 1) DisplayLoginTab() else DisplayRegisterTab(playerData, userVM)
    }
}

@InternalCoroutinesApi
@Composable
fun RegisterLoginTabs(userVM: UserViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.0f)
                .background(if (userVM.profilVM.IsRegisterTabSelected()) Color.Gray else Color.Transparent)
//                    .height(70.dp)
                .clickable {
                    userVM.profilVM.SelectLoginTab()
                }
        ) {
            Text(text = "Login ${userVM.profilVM.tabSeclected.value}", Modifier.align(Alignment.Center))
        }

        Box(
            modifier = Modifier
                .weight(1.0f)
                .background(if (userVM.profilVM.IsLoginTabSelected()) Color.Gray else Color.Transparent)
                .clickable {
                    userVM.profilVM.SelectRegisterTab()
                }
        ) {
            Text(text = "Register", Modifier.align(Alignment.Center))
        }
    }
}
//todo: can't register as none cuz its a defaut variable/ or change the variable

