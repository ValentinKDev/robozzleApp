package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.UserConnectionState
import com.mobilegame.robozzle.domain.model.UserViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.InternalCoroutinesApi

//@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen(mUserViewModel: UserViewModel, testViewModel: testViewModel = viewModel()) {

    infoLog("launch", "CreatorScreen()")
//    val co by mUserViewModel.connected.collectAsState()

//    val co by remember(mUserViewModel) {mUserViewModel.collectConnectedState()}.collectAsState(
//        initial = UserConnectionState.Connected
//    )
//        initial = UserConnectionState.NOUSER
//    )
//    infoLog("co", co.toString())


    Column() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                infoLog("num before", "${testViewModel.num}")
//                testViewModel.num = -42
//                infoLog("num after", "${testViewModel.num}")
            },
        ) {
            Text(text = "2")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                     mUserViewModel.connectedTO(UserConnectionState.CONNECTED)
            },
        ) {
//            Text(text = "${co}")
        }
    }
}


class testViewModel(): ViewModel() {
    var num = 0
    init {
    }

}