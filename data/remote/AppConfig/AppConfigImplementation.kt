package com.mobilegame.robozzle.data.remote.AppConfig

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.dto.AppConfigRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.lang.Exception

class AppConfigImplementation (
    private val client: HttpClient
): AppConfigService {
    override suspend fun getAppConfig(): AppConfigRequest? {
        return try {
            client.get {
                url { encodedPath = HttpRoutes.APP_CONFIG }
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
            Log.e("getLevels","Error: ${e.message}")
            null
        }
    }

//    override suspend fun getAvailableUpdate(): Boolean? {
//    }
//    override suspend fun getVersion(): String? {
//        return try {
//            client.get {
//                url { encodedPath = HttpRoutes.LEVELS_VERSION }
//            }
//        } catch (e: RedirectResponseException) {
//            //3xx- responses
//            Log.e("3xx","Error: ${e.response.status.description}")
////            emptyList()
//            null
//        } catch (e: ClientRequestException) {
//            //4xx- responses
//            Log.e("4xx","Error: ${e.response.status.description}")
////            emptyList()
//            null
//        } catch (e: ServerResponseException) {
//            //5xx- responses
//            Log.e("5xx","Error: ${e.response.status.description}")
////            emptyList()
//            null
//        } catch (e: Exception) {
//            Log.e("getLevels","Error: ${e.message}")
////            emptyList()
//            null
//        }
//    }
}
