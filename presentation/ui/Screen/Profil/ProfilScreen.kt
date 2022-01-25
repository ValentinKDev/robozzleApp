package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.User.ProfilViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.toREMOVE.PlayerData
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProfilScreen(playerData: PlayerData, userVM: UserViewModel) {
//    val selectedTab by userVM.profilVM.tabSeclected.observeAsState(initial = 1)
    val selectedTab by userVM.profilVM.tabSeclected.collectAsState()

    infoLog("", "launch profil Screen")
    Column(
    ) {
        DisplayProfilTabsHeader(userVM)
        Spacer(modifier = Modifier.height(50.dp))
        if (selectedTab == 1) DisplayLoginTab() else DisplayRegisterTab(playerData, userVM)
    }
}


@InternalCoroutinesApi
@Composable
fun DisplayProfilTabsHeader(userVM: UserViewModel) {
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

