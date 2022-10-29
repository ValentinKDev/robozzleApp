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
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.LoginTab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.RegisterTab
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.*

@Composable
fun RegisterLoginScreen(navigator: Navigator, tab: Tab) {
    val tabSelected: Int by tab.selected.collectAsState()

    LaunchedEffect(key1 = true) { verbalLog("Launch", "RegisterLoginScreen") }

    BackHandler {
//        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Profil.route)
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.RegisterLogin.route)
//        NavViewModel(navigator).navigateTo(destination = Screens.MainMenu, argStr = Screens.RegisterLogin.route)
    }

    Column {
        RegisterLoginTabsHead(tab)
        if (tabSelected == 1) { RegisterTab(navigator) }
        else { LoginTab(navigator) }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun RegisterLoginTabsHead(tab: Tab) {
    val tabRegister: Boolean = tab.selected.value == 1
    val tabLogin: Boolean = !tabRegister

    //todo: adding animation from registering tab to Login tab and vice versa
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.0f)
                //todo: could avoid the test on tabselect by using an boolean variable like isRegisterTabSelected
                .background(if (tabRegister) Color.Transparent else Color.Gray)
                .clickable { TabSelectionViewModel().setTabToRegister(tab) }
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
                .background(if (tabLogin) Color.Transparent else Color.Gray)
                .clickable {
                    TabSelectionViewModel().setTabToLogin(tab)
                }
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

class Tab() {
    private val _selected: MutableStateFlow<Int> = MutableStateFlow(1)
    val selected : StateFlow<Int> = _selected.asStateFlow()

    fun setSelecedTo(tab: Int) {
        _selected.value = tab
        infoLog("_selected", "${_selected.value}")
    }
}
