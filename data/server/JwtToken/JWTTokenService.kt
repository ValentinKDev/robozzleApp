package com.mobilegame.robozzle.data.server.JwtToken

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.HttpRoutes.REQUEST_TIME
import com.mobilegame.robozzle.presentation.ui.Navigation.displayHttpClientInfo
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

interface JWTTokenService {
    suspend fun getJwtToken(): String?

    suspend fun verifyToken(): String

    companion object {
        fun create(username: String, password: String, token: String): JWTTokenService {
            return JWTTokenImplementation(
                client = HttpClient(Android) {
                    install(HttpTimeout) {
//                        requestTimeoutMillis = 1500
//                        requestTimeoutMillis = 200
                        requestTimeoutMillis = REQUEST_TIME
                    }
                    install(JsonFeature) {
                        acceptContentTypes = acceptContentTypes + ContentType("application","json+hal")
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            isLenient = true
                        })
                        install(ResponseObserver) {
                            onResponse { response ->
                                displayHttpClientInfo?.let {
                                    Log.d("HTTP status:", "${response.status.value}")
                                }
                            }
                        }
                        install(DefaultRequest) {
                            header(HttpHeaders.ContentType, ContentType.Application.Json)
                        }
                    }
                    defaultRequest {
                        host = HttpRoutes.HOST
                        port = HttpRoutes.PORT
                    }
                    install(Auth) {
                        basic {
                            credentials {
                                BasicAuthCredentials(username = username, password = password)
                            }
                        }
                        bearer {
                            loadTokens {
                                BearerTokens(
                                    accessToken = token,
                                    refreshToken = token,
                                )
                            }
                        }
                    }
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                displayHttpClientInfo?.let {
                                    Log.v("Logger Ktor =>", message)
                                }
                            }

                        }
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}
