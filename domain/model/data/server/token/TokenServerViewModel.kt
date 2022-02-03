package com.mobilegame.robozzle.domain.model.data.server.token

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import kotlinx.coroutines.runBlocking

class TokenServerViewModel(
    context: Context
): ViewModel() {
    val user = UserDataStoreViewModel(context).getUser()
    val service: JWTTokenService = JWTTokenService.create(user.name, user.password)

    fun getTokenServer(): String? = runBlocking {
        service.getJwtToken()
    }
//    val tokenServer: String? =
}