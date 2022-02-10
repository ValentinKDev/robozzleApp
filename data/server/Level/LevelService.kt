package com.mobilegame.robozzle.data.server.Level

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.dto.LevelRequest
import com.mobilegame.robozzle.domain.Player.PlayerWin
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
    suspend fun getLevelIdList(): String?

    suspend fun getAllLevels(): List<String>

    suspend fun getLevelsById(idList: List<Int>): List<String>

    companion object {
        fun create(): LevelService {
            return LevelImplementation (
            client = HttpClient(Android) {
                install(HttpTimeout) {
//                    requestTimeoutMillis = 300
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
