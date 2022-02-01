package com.mobilegame.robozzle.data.server.AppConfig

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.dto.AppConfigRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

interface AppConfigService {

    suspend fun getAppConfig(): AppConfigRequest?

    companion object {
        fun create(): AppConfigService {
            return AppConfigImplementation (
                client = HttpClient(Android) {
                    install(HttpTimeout) {
                        requestTimeoutMillis = 1500
                    }
//                    install(Auth) {
//                        digest {
//                            credentials {
//                                DigestAuthCredentials(username = "admin", password = "admin")
//                            }
//                            realm = "Access to the '/' path"
//                        }
//                    }
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
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            explicitNulls = false
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
                }
            )
        }
    }
}
