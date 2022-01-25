package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.base.User.User
import com.mobilegame.robozzle.data.remote.AppConfig.AppConfigService
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.HttpRoutes.Auth_test
import com.mobilegame.robozzle.data.remote.HttpRoutes.HOST
import com.mobilegame.robozzle.data.remote.HttpRoutes.LEVELS_NUMBER_PATH
import com.mobilegame.robozzle.data.remote.HttpRoutes.PORT
import com.mobilegame.robozzle.data.remote.Level.LevelService
import com.mobilegame.robozzle.data.remote.Level.LevelService.Companion.tokenClient
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.lang.Exception

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen() {
    val VM = testViewModel()
    Column() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                 VM.post()
                VM.post2()
            },
        ) {
            Text(text = "2")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                 VM.post()
//            VM.post2()
                VM.post3()
            },
        ) {
            Text(text = "3")
        }
    }
}


class testViewModel(): ViewModel() {

    val userService: UserService
    val userTest = User( id = -1, name = "unAutre", password = "123", )
//    val userTest = User( id = -1, name = "moi", password = "123", )
//    val userTest = User( id = -1, name = "admin", password = "123", )

    init {
        userService = UserService.create()
    }

    fun post() {
        viewModelScope.launch(Dispatchers.IO) {
            userService.postNewUser(UserRequest(userTest.name, userTest.password))
        }
    }
    val BASE = "http://$HOST:$PORT"

    fun post2() {

        viewModelScope.launch(Dispatchers.IO) {
            val tokenInfo = userService.test(UserRequest("admin", "admin"))
            infoLog("tokentInfo", "${tokenInfo}")
        }
    }
    fun post3() {

        viewModelScope.launch(Dispatchers.IO) {
            val tokenInfo = userService.test3(UserRequest("admin", "admin"))
            infoLog("tokentInfo", "${tokenInfo}")
        }
    }

    val tokenClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    val client = HttpClient(Android) {
//        expectSuccess = false
        install(HttpTimeout) {
            requestTimeoutMillis = 1500
        }
//        install(JsonFeature) {
//            serializer = KotlinxSerializer(Json)
//        }
//        install(JsonFeature) {
//            serializer = KotlinxSerializer(Json {
//                prettyPrint = true
//                isLenient = true
//            })
//        }
//            )
//            install(ResponseObserver) {
//                onResponse { response ->
//                    Log.d("HTTP status:", "${response.status.value}")
//                }
//            }
//            install(DefaultRequest) {
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//            }
//        }
//        /*
        install(Auth) {
            lateinit var tokenInfo: TokenInfo
//            var refreshTokenInfo: TokenInfo

            bearer {
                loadTokens {
                    tokenInfo = tokenClient.submitForm(
//                        url = Auth_test,
                        url = "http://$HOST:$PORT"+ "/users/register",
//                                url = "https://accounts.google.com/o/oauth2/token",
                        formParameters = Parameters.build {
//                                    append("grant_type", "authorization_code")
//                                    append("code", authorizationCode)
//                                    append("client_id", clientId)
//                                    append("redirect_uri", redirectUri)
                            append("name", "admin")
//                                    append("userId", "0")
                        }
                    )
                    BearerTokens(
                        accessToken = tokenInfo.accessToken,
                        refreshToken = tokenInfo.refreshToken!!
                    )
                }
            }
        }
//        */
//        defaultRequest {
//            host = HttpRoutes.HOST
//            port = HttpRoutes.PORT
//        }
//        install(Logging) {
//            logger = object : Logger {
//                override fun log(message: String) {
//                    Log.v("Logger Ktor =>", message)
//                }
//            }
//            level = LogLevel.ALL
//        }
    }
}
@Serializable
data class NewUser(
    val name: String,
    val password: String
)

@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String? = null,
    val scope: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("id_token") val idToken: String,
)
