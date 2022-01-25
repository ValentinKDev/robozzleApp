package com.mobilegame.robozzle.data.remote.User

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.Level.TokenInfo
import com.mobilegame.robozzle.data.remote.dto.UserRequest
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


interface UserService {
//    suspend fun getUser(id: Int): UserRequest?

//    suspend fun createUser(user: UserRequest): UserRequest?
    suspend fun postNewUser(user: UserRequest)
//    suspend fun getUsers(): List<UserRequest>

//    suspend fun createPlayerAuth(player: UserRequest): UserRequest?
    suspend fun test(user: UserRequest): TokenInfo?
    suspend fun test3(user: UserRequest)

    companion object {
        fun create(): UserService {
            return UserImplementation(
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
//                                    accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4ODg4L2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODg4OCIsIm5hbWUiOiJhZG1pbiIsImV4cCI6MTY0Mjk3NDQ4MSwidXNlcklkIjoiMCJ9.xA9BeR3jQBb8KQt3Zu0AqxJXxP-tGrTZ2aV9TEJVAy0",
//                                    refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4ODg4L2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODg4OCIsIm5hbWUiOiJhZG1pbiIsImV4cCI6MTY0Mjk3NDQ4MSwidXNlcklkIjoiMCJ9.xA9BeR3jQBb8KQt3Zu0AqxJXxP-tGrTZ2aV9TEJVAy0",
                                    accessToken = "sdlf",
                                    refreshToken = "sdlf",
                                )
                            }
                        }
                    }
//                client = HttpClient(Android) {
////                    headers {
////
////                    }
//                    install(HttpTimeout) {
////                        requestTimeoutMillis = 1500
//                        requestTimeoutMillis = 1000
//                    }
////                    install(Auth) {
////                        digest {
////                            credentials {
////                                DigestAuthCredentials(username = "jetbrains", password = "foobar")
////                                DigestAuthCredentials(username = username, password = password)
////                            }
////                            realm = "Access to the '/' path"
////                        }
////                    }
//                    defaultRequest {
//                        host = HttpRoutes.HOST
//                        port = HttpRoutes.PORT
//                    }
//                    install(Logging) {
//                        logger = object : Logger {
//                            override fun log(message: String) {
//                                Log.v("Logger Ktor =>", message)
//                            }
//
//                        }
//                        level = LogLevel.ALL
//                    }
//                    install(JsonFeature) {
////                        serializer = GsonSerializer()
//                        acceptContentTypes = acceptContentTypes + ContentType("application","json+hal")
//                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
//                            prettyPrint = true
//                            isLenient = true
//                            //todo : is not in the server side
////                            ignoreUnknownKeys = true
//                        })
//                        install(ResponseObserver) {
//                            onResponse { response ->
//                                Log.d("HTTP status:", "${response.status.value}")
//                            }
//                        }
//
//                        install(DefaultRequest) {
//                            header(HttpHeaders.ContentType, ContentType.Application.Json)
//                        }
//                    }
//                }
                }
            )
        }
    }
}