package com.mobilegame.robozzle.data.server.Level

import android.util.Log
import com.google.gson.Gson
import com.mobilegame.robozzle.data.server.HttpRoutes.LEVEL_ID_LIST_PATH
import com.mobilegame.robozzle.data.server.HttpRoutes.LEVELS_NUMBER_PATH
import com.mobilegame.robozzle.data.server.HttpRoutes.LEVEL_PATH
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.lang.Exception

class LevelImplementation (
    private val client: HttpClient
): LevelService {
//    override suspend fun getAllLevels(): List<LevelRequest> {
    override suspend fun getAllLevels(): List<String> {
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

//    override suspend fun getLevelsById(idList: List<Int>): List<LevelRequest> {
    override suspend fun getLevelsById(idList: List<Int>): List<String> {
        val idListJson = Gson().toJson(idList)
        return try {
            client.get{
                url { encodedPath = "$LEVEL_PATH/$idListJson" }
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
            Log.e("getLevelsById","Error: ${e.message}")
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
            Log.e("getLevelsNumber","Error: ${e.message}")
            -1
        }
    }

    override suspend fun getLevelIdList(): String? {
        return try {
            client.get {
                url { encodedPath = LEVEL_ID_LIST_PATH }
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            Log.e("getLevelIdList","Error: ${e.message}")
            null
        }
    }
}
