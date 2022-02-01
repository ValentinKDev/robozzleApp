package com.mobilegame.robozzle.data.server.User

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.server.dto.UserRequest
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


interface UltimateUserService {
    suspend fun getUltimateUser(name: String): UltimateUserRequest?

    //todo : return a value to know if the user is already in the database ?
    suspend fun postNewUser(user: UserRequest): String

    companion object {
        fun create(token: String): UltimateUserService {
            return UltimateUserImplementation(
                client = HttpClient(Android) {
                    install(HttpTimeout) {
                        requestTimeoutMillis = 1500
                    }
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
                    install(JsonFeature) {
//                        serializer = GsonSerializer()
                        acceptContentTypes = acceptContentTypes + ContentType("application","json+hal")
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            isLenient = true

                            //todo : is not in the server side
//                            ignoreUnknownKeys = true
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
                    install(Auth) {
                        bearer {
                            loadTokens {
                                BearerTokens(
                                    accessToken = token,
                                    refreshToken = token,
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}