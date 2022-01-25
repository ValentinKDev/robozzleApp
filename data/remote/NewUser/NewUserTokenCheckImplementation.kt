package com.mobilegame.robozzle.data.remote.NewUser

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class NewUserTokenCheckImplementation (
    private val client: HttpClient
): NewUserTokenCheckService {
    override suspend fun serverAllowNewRegistration(newUser: UserRequest): Boolean {
        var serverAllowNewRegistration = false
        try {
            client.post{
                url { encodedPath = HttpRoutes.USER_REGISTRATION_PATH }
                contentType(ContentType.Application.Json)
                body = String
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            serverAllowNewRegistration = true
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
            serverAllowNewRegistration = true
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            serverAllowNewRegistration = true
        } catch (e: Exception) {
            Log.e("exception","Error: ${e.message}")
            serverAllowNewRegistration = true
        }
        return serverAllowNewRegistration
    }
}
