package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.UserViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun RegisterLoginScreen(userVM: UserViewModel) {
    infoLog("Launch", "RegisterLoginScreen")
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

