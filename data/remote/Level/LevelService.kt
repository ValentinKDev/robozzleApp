package com.mobilegame.robozzle.data.remote.Level

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

import io.ktor.client.engine.cio.*
import kotlinx.serialization.*

interface LevelService {
    suspend fun getLevels(): List<LevelRequest>

    suspend fun getLevelsNumber(): Int

    suspend fun getLevelIdList(): List<Int>

    companion object {
        val tokenClient = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        fun create(): LevelService {
            return LevelImplementation (
            client = HttpClient(Android) {
                install(HttpTimeout) {
                    requestTimeoutMillis = 1500
                }
                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        //todo : is not in the server side
                    })
                    install(ResponseObserver) {
                        onResponse { response ->
                            Log.d("HTTP status:", "${response.status.value}")
                        }
                    }
                    install(DefaultRequest) {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }
                /*
                install(Auth) {
                    lateinit var tokenInfo: TokenInfo
                    var refreshTokenInfo: TokenInfo

                    bearer {
                        loadTokens {
                            tokenInfo = tokenClient.submitForm(
                                url = Auth_test,
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
                */
                defaultRequest {
                    host = HttpRoutes.HOST
                    port = HttpRoutes.PORT
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Logger Ktor =>", message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }
            )
        }
    }
}

@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String? = null,
    val scope: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("id_token") val idToken: String,
)
