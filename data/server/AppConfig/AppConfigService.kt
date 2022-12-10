package com.mobilegame.robozzle.data.server.AppConfig

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.HttpRoutes.REQUEST_TIME
import com.mobilegame.robozzle.data.server.dto.AppConfigRequest
import com.mobilegame.robozzle.presentation.ui.Navigation.displayHttpClientInfo
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface AppConfigService {

    suspend fun getAppConfig(): AppConfigRequest?

    companion object {
        fun create(): AppConfigService {
            return AppConfigImplementation (
                client = HttpClient(Android) {
                    install(HttpTimeout) {
//                        requestTimeoutMillis = 300
//                        requestTimeoutMillis = 1500
//                        requestTimeoutMillis = 200
                        requestTimeoutMillis = REQUEST_TIME
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
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            explicitNulls = false
                        })
                        install(ResponseObserver) {
                            onResponse { response ->
                                displayHttpClientInfo?.let {
                                    Log.d("AppConfigService::ResponseObserver", "content : ${response.content}")
                                    Log.d("AppConfigService::ResponseObserver", "call : ${response.call}")
                                    Log.d("AppConfigService::ResponseObserver", "status : ${response.status}")
                                    Log.d("AppConfigService::ResponseObserver", "status value : ${response.status.value}")
                                    Log.d("AppConfigService::ResponseObserver", "status description : ${response.status.description}")
                                    Log.d("AppConfigService::ResponseObserver", "request : ${response.request}")
                                }
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
