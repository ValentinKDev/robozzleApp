package com.mobilegame.robozzle.data.server.JwtToken

import android.util.Log
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.server.HttpRoutes.USER_GET_TOKEN
import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.lang.Exception

class JWTTokenImplementation(
    private val client: HttpClient
): JWTTokenService {
    override suspend fun getJwtToken(): String? {
        verbalLog("getJwtToken", "ask server")
        return client.tryGetAndCatchErrors<String?>(
            funName = "getJwtToken",
            encodedPath = USER_GET_TOKEN,
//            errorRet =,
        )
    }
//    override suspend fun getJwtToken(): String? {
//        verbalLog("getJwtToken", "ask server")
//        return try {
//            client.get {
//                url { encodedPath = USER_GET_TOKEN }
//            }
//        } catch (e: RedirectResponseException) {
//            //3xx- responses
//            Log.e("3xx","Error: ${e.response.status.description}")
//            null
//        } catch (e: ClientRequestException) {
//            //4xx- responses
//            Log.e("4xx","Error: ${e.response.status.description}")
//            null
//        } catch (e: ServerResponseException) {
//            //5xx- responses
//            Log.e("5xx","Error: ${e.response.status.description}")
//            null
//        } catch (e: Exception) {
//            Log.e("getUser","Error: ${e.message}")
//            null
//        }
//    }
}