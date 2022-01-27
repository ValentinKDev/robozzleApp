package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.HttpRoutes.HOST
import com.mobilegame.robozzle.data.remote.HttpRoutes.PORT
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen(mUserViewModel: UserViewModel) {
    infoLog("", "")
    val VM = testViewModel()
    Column() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
            },
        ) {
            Text(text = "2")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
            },
        ) {
            Text(text = "3")
        }
    }
}


class testViewModel(): ViewModel() {



    init {
    }

}