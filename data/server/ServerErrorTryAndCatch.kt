package com.mobilegame.robozzle.data.server

import android.util.Log
import com.mobilegame.robozzle.data.server.User.ServerRet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

suspend inline fun <reified T> HttpClient.tryGetAndCatchErrors(funName: String, encodedPath: String, errorRet: T? = null): T? {
    return try {
        this.get {
            url { this.encodedPath = encodedPath }
        }
    } catch (e: NoTransformationFoundException) {
        Log.e("$funName 2xx","Error: ${e.message}")
        errorRet
    }  catch (e: RedirectResponseException) {
        Log.e("$funName 3xx","Error: ${e.response.status.description}")
        errorRet
    } catch (e: ClientRequestException) {
        Log.e("$funName 4xx","Error: ${e.response.status.description}")
        errorRet
    } catch (e: ServerResponseException) {
        Log.e("$funName 5xx","Error: ${e.response.status.description}")
        errorRet
    } catch (e: Exception) {
        Log.e("$funName Exception","Error: ${e.message}")
        errorRet
    }
}

suspend inline fun <reified T> HttpClient.tryPostAndCatchErrors(funName: String, encodedPath: String, objToPost: T): String {
    return try {
        this.post {
            url { this.encodedPath = encodedPath }
            contentType(ContentType.Application.Json)
            body = objToPost ?: "body is null"
            ServerRet.Positiv.ret
        }
    } catch (e: NoTransformationFoundException) {
        Log.e("$funName 2xx","Error: ${e.message}")
        ServerRet.Error200.ret
    } catch (e: RedirectResponseException) {
        Log.e("$funName 3xx","Error: ${e.response.status.description}")
        ServerRet.Error300.ret
    } catch (e: ClientRequestException) {
        Log.e("$funName 4xx","Error: ${e.response.status.description}")
        ServerRet.Error400.ret
    } catch (e: ServerResponseException) {
        Log.e("$funName 5xx","Error: ${e.response.status.description}")
        ServerRet.Error500.ret
    } catch (e: Exception) {
        Log.e("$funName Exception","Error: ${e.message}")
        ServerRet.Exception.ret
    }
}
