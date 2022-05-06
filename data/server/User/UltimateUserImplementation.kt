package com.mobilegame.robozzle.data.server.User

import android.util.Log
import com.mobilegame.robozzle.data.server.HttpRoutes.USER_REGISTRATION_PATH
import com.mobilegame.robozzle.data.server.ServerRet
import com.mobilegame.robozzle.data.server.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.server.dto.UserRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class UltimateUserImplementation (
    private val client: HttpClient
): UltimateUserService {
    override suspend fun getUltimateUser(name: String): UltimateUserRequest? {
//        var ret: UltimateUserRequest
        return try {
            client.get {
                url { encodedPath = "user/ultimate/${name}" }
            }
        } catch (e: NoTransformationFoundException) {
            //2xx- responses
            Log.e("2xx","Error: ${e.message}")
            null
//            ret = ServerSend.ERROR200.ret
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            null
//            ret = ServerSend.ERROR300.ret
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
//            ret = ServerSend.ERROR400.ret
            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
//            ret = ServerSend.ERROR500.ret
            null
        } catch (e: Exception) {
            Log.e("Exception","Error: ${e.message}")
//            ret = ServerSend.EXCETPTION.ret
            null
        }
//        return ret
    }

    override suspend fun postNewUser(user: UserRequest): String {
        var ret : String = ServerRet.NotAttribution.ret
        try {
            client.post {
                url { encodedPath = USER_REGISTRATION_PATH }
                contentType(ContentType.Application.Json)
                body = user
                ret = ServerRet.Positiv.ret
            }
        } catch (e: NoTransformationFoundException) {
            //2xx- responses
            Log.e("2xx","Error: ${e.message}")
            ret = ServerRet.Error200.ret
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            ret = ServerRet.Error300.ret
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            ret = ServerRet.Error400.ret
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            ret = ServerRet.Error500.ret
        } catch (e: Exception) {
            Log.e("Exception","Error: ${e.message}")
            ret = ServerRet.Exception.ret
        }
        return ret
    }
}
