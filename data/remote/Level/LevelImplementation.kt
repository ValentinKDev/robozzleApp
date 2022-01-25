package com.mobilegame.robozzle.data.remote.Level

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes.LEVELS_NUMBER_PATH
import com.mobilegame.robozzle.data.remote.HttpRoutes.LEVEL_PATH
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.lang.Exception

class LevelImplementation (
    private val client: HttpClient
): LevelService {
    override suspend fun getLevels(): List<LevelRequest> {
        return try {
            client.get {
                url { encodedPath = LEVEL_PATH }
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            Log.e("getLevels","Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getLevelsNumber(): Int {
        return try {
            client.get {
                url { encodedPath = LEVELS_NUMBER_PATH }
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            -1
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            -1
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            -1
        } catch (e: Exception) {
            Log.e("getLevels","Error: ${e.message}")
            -1
        }
    }
}
