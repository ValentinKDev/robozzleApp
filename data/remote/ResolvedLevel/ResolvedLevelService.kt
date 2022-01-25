package com.mobilegame.robozzle.data.remote.ResolvedLevel

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.dto.ResolvedLevelRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

interface ResolvedLevelService {
//    suspend fun GetWinLevel(name: String, id): WinLevelRequest?

    suspend fun GetResolvedLevelsList(name: String): List<ResolvedLevelRequest>

    suspend fun CreateResolvedLevel(resolvedLevel: ResolvedLevelRequest)

    companion object {
        fun create(): ResolvedLevelService {
            return ResolvedLevelImplementation(
                client = HttpClient(Android) {
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
                }
            )
        }
    }
}
