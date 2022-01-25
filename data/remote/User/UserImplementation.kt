package com.mobilegame.robozzle.data.remote.User

import android.util.Log
import com.mobilegame.robozzle.data.remote.HttpRoutes.TEST_TOKEN
import com.mobilegame.robozzle.data.remote.HttpRoutes.USER_PATH
import com.mobilegame.robozzle.data.remote.HttpRoutes.USER_REGISTRATION_PATH
import com.mobilegame.robozzle.data.remote.Level.TokenInfo
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class UserImplementation (
    private val client: HttpClient
): UserService {
    override suspend fun getUser(id: Int): UserRequest? {
        return try {
            client.get {
                url { encodedPath = "${USER_PATH}/$id" }
            }
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","getUser() Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            Log.e("getUser","Error: ${e.message}")
            null
        }
    }

    //    override suspend fun createUser(user: UserRequest): UserRequest? {
    override suspend fun postNewUser(user: UserRequest) {
//    return try {
        try {
            client.post {
//                url(HttpRoutes.PLAYER_URL)
                url { encodedPath = USER_REGISTRATION_PATH }
                contentType(ContentType.Application.Json)
                body = user
//                headers {
//                    accept(ContentType.Application.Json)
//                }
            }
//        } catch (e: NoTransformationFoundException) {
//            2xx- responses
//            Log.e("2xx","Error: ${e.response.status.description}")
//            Log.e("2xx","CreateUser() Error: ${e.message}")
//            Log.e("custom","Error: ${e.cause}")
//            null
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
//            null
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
//            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
//            null
        } catch (e: Exception) {
            Log.e("createUser","Error: ${e.message}")
//            null
        }

//    var reception = "none"
//    try {
//        reception = client.get {
//            url { encodedPath = USER_REGISTRATION_PATH }
//            contentType(ContentType.Application.Json)
//            body = TokenInfo
//        }
//    } catch (e: RedirectResponseException) {
//        //3xx- responses
//        Log.e("3xx","Error: ${e.response.status.description}")
////            null
//    } catch (e: ClientRequestException) {
//        //4xx- responses
//        Log.e("4xx","Error: ${e.response.status.description}")
////            null
//    } catch (e: ServerResponseException) {
//        //5xx- responses
//        Log.e("5xx","Error: ${e.response.status.description}")
////            null
//    } catch (e: Exception) {
//        Log.e("test reception","Error: ${e.localizedMessage}")
//        Log.e("test reception","Error: ${e.message}")
////            null
//    }
//    infoLog("reception", reception)
    }

    //    override suspend fun test(user: UserRequest) {
    override suspend fun test(user: UserRequest): TokenInfo? {
//        client.
        try {
            client.post {
//                url(HttpRoutes.PLAYER_URL)
//                url { encodedPath = USER_REGISTRATION_PATH }
                url { encodedPath =  TEST_TOKEN}
                contentType(ContentType.Application.Json)
                body = user
//                headers {
//                    accept(ContentType.Application.Json)
//                }
            }
//        } catch (e: NoTransformationFoundException) {
//            2xx- responses
//            Log.e("2xx","Error: ${e.response.status.description}")
//            Log.e("2xx","CreateUser() Error: ${e.message}")
//            Log.e("custom","Error: ${e.cause}")
//            null
        } catch (e: RedirectResponseException) {
            //3xx- responses
            Log.e("3xx","Error: ${e.response.status.description}")
//            null
        } catch (e: ClientRequestException) {
            //4xx- responses
            Log.e("4xx","Error: ${e.response.status.description}")
//            null
        } catch (e: ServerResponseException) {
            //5xx- responses
            Log.e("5xx","Error: ${e.response.status.description}")
//            null
        } catch (e: Exception) {
            Log.e("createUser","Error: ${e.message}")
            val token = e.message
            Log.i("token","${token}")
//            null
        }
        return try {
//            val reception =
            client.get {
                url { encodedPath = TEST_TOKEN }
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
            Log.e("createUser","Error: ${e.message}")
            val token = e.message
            Log.i("token","${token}")
            null
        }
//            client.get {
//                url { encodedPath = TEST_TOKEN }
//            }
//            val oui = client.get {
//                url { encodedPath =  TEST_TOKEN}
//                contentType(ContentType.Application.Json)
//                body = user
//            }
//        } catch ()
    }

    override suspend fun test3(user: UserRequest) {
       try {
           client.get {
            url { encodedPath =  "/my"}
//            contentType(ContentType.Application.Json)
//            body = "une putain de string"
//                headers {
//                    accept(ContentType.Application.Json)
//                }
        }
//        } catch (e: NoTransformationFoundException) {
//            2xx- responses
//            Log.e("2xx","Error: ${e.response.status.description}")
//            Log.e("2xx","CreateUser() Error: ${e.message}")
//            Log.e("custom","Error: ${e.cause}")
//            null
    } catch (e: RedirectResponseException) {
        //3xx- responses
        Log.e("3xx","Error: ${e.response.status.description}")
//            null
    } catch (e: ClientRequestException) {
        //4xx- responses
        Log.e("4xx","Error: ${e.response.status.description}")
//            null
    } catch (e: ServerResponseException) {
        //5xx- responses
        Log.e("5xx","Error: ${e.response.status.description}")
//            null
    } catch (e: Exception) {
        Log.e("createUser","Error: ${e.message}")
        val token = e.message
        Log.i("token","${token}")
//            null
    }
}

}
