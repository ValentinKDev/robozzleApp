package com.mobilegame.robozzle.data.server.JwtToken

import android.util.Log
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.server.HttpRoutes
import com.mobilegame.robozzle.data.server.HttpRoutes.USER_GET_TOKEN
import com.mobilegame.robozzle.data.server.HttpRoutes.USER_VERIFY_TOKEN
import com.mobilegame.robozzle.data.server.User.ServerRet
import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import com.mobilegame.robozzle.domain.model.data.general.TokenState
//import com.mobilegame.robozzle.domain.state.TokenState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.lang.Exception
import java.util.concurrent.TimeoutException

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

    override suspend fun verifyToken(): String {
        return try {
            client.get {
                url { encodedPath = USER_VERIFY_TOKEN }
//                ServerRet.Positiv.ret
                TokenState.ValidateBy.server
            }
        } catch (e: NoTransformationFoundException) {
            Log.e("2xx","Error: ${e.message}")
            TokenState.IssueWith.server
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            TokenState.IssueWith.server
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            TokenState.UnauthorizedBy.server
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            TokenState.IssueWith.server
        } catch (e: Exception) {
            Log.e("verifyToken", "Error: ${e.message}")
//            Log.e("verifyToken", "Error: ${e.respon}")
            TokenState.IssueWith.server
        }
    }
}