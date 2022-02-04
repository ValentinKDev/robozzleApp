package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.presentation.ui.Navigator
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

//@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen(navigator: Navigator, testViewModel: testViewModel = viewModel()) {

    infoLog("launch", "CreatorScreen()")

    Column() {
        val ctxt = LocalContext.current
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
                val test = TokenVM(ctxt).getToken()
            },
        ) {
            Text(text = "2")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
                     LoginViewModel(navigator).navToMain("truc", "machin")
            },
        ) {
            Text(text = "3")
        }
    }
}
class LoginViewModel(private val navigator: Navigator) : ViewModel() {

    fun navToMain(username: String, password: String) {
        viewModelScope.launch {
            // long running login process happens here
            errorLog("nav", "$username $password")

        }
    }
    fun navToCreator() {
        viewModelScope.launch {
        }
    }

}

class testViewModel(): ViewModel() {
    var num = 0
    init {
    }

}