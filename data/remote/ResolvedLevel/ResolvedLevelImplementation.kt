package com.mobilegame.robozzle.data.remote.ResolvedLevel

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes
import com.mobilegame.robozzle.data.remote.dto.ResolvedLevelRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class ResolvedLevelImplementation (
    private val client: HttpClient
): ResolvedLevelService {
    override suspend fun GetResolvedLevelsList(name: String): List<ResolvedLevelRequest> {
        return try {
            client.get {
                url { encodedPath = "${HttpRoutes.WIN_LEVELS}/$name" }
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","getUser() Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            Log.e("getUser","Error: ${e.message}")
            emptyList()
        }
    }

    //    override suspend fun createUser(user: UserRequest): UserRequest? {
    override suspend fun CreateResolvedLevel(resolvedLevel: ResolvedLevelRequest) {
        val funName = "CreateWinLevel()"
        try {
            client.post {
                url { encodedPath = HttpRoutes.WIN_LEVELS }
                contentType(ContentType.Application.Json)
                body = resolvedLevel
            }
        } catch (e: NoTransformationFoundException) {
            //2xx- responses
            Log.e("2xx","$funName Error: ${e.message}")
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","$funName Error: ${e.response.status.description}")
//            null
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","$funName Error: ${e.response.status.description}")
//            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","$funName Error: ${e.response.status.description}")
//            null
        } catch (e: Exception) {
            Log.e("$funName","Error: ${e.message}")
//            null
        }
    }

}
