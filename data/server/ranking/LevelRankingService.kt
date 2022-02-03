package com.mobilegame.robozzle.data.server.ranking

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.server.Level.LevelImplementation
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
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

interface LevelRankingService {
    suspend fun getWinnerListJson(id: Int): String?

    suspend fun postPlayerWin(playerWinJson: String)

    companion object {
        fun create(token: String): LevelRankingService {
            return LevelRankingImplementation (
                client = HttpClient(Android) {
                    install(HttpTimeout) {
                        requestTimeoutMillis = 1500
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            isLenient = true
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
                    install(Auth) {
                        bearer {
                            loadTokens {
                                BearerTokens(
                                    accessToken = token,
                                    //todo: how to refresh a token ?
                                    refreshToken = token,
                                )
                            }
                        }
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
