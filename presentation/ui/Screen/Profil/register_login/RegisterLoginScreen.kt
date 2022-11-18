package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.TabSelectionViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tabs
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.LoginTab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.RegisterTab
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.*

@Composable
fun RegisterLoginScreen(navigator: Navigator, tab: TabSelectionViewModel = TabSelectionViewModel()) {
    val tabSelected: Tabs by tab.selected.collectAsState()

    LaunchedEffect(key1 = true) { verbalLog("Launch", "RegisterLoginScreen") }

    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.RegisterLogin.route)
    }

    Column {
        RegisterLoginTabsHead(tab, tabSelected)
        when (tabSelected) {
            Tabs.Register -> RegisterTab(navigator = navigator)
            Tabs.Login -> LoginTab(navigator = navigator)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun RegisterLoginTabsHead(tab: TabSelectionViewModel, tabSelected: Tabs) {

    //todo: adding animation from registering tab to Login tab and vice versa
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.0f)
                .background(
                    when (tabSelected) {
                        Tabs.Register -> Color.Transparent
                        Tabs.Login -> Color.Gray
                    }
                )
                .clickable {
                    tab.setSelecedTo(Tabs.Register)
                }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = whiteDark4,
                text = "Register",
            )
        }

        Box(
            modifier = Modifier
                .weight(1.0f)
                .background(
                    when (tabSelected) {
                        Tabs.Register -> Color.Gray
                        Tabs.Login -> Color.Transparent
                    }
                )
                .clickable { tab.setSelecedTo(Tabs.Login) }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = whiteDark4,
                text = "Login",
            )
        }
    }
}
//todo: can't register as none cuz its a defaut variable/ or change the variable