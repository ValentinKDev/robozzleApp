package com.mobilegame.robozzle.domain.model.data.server.token

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.server.User.UltimateUserService
import com.mobilegame.robozzle.domain.model.data.general.TokenState
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import kotlinx.coroutines.runBlocking

class TokenServerViewModel(
    context: Context,
): ViewModel() {
//    val tokenData = TokenDataStoreViewModel(context).
    val user = UserDataStoreViewModel(context).getUser()
    val service: JWTTokenService = JWTTokenService.create(user.name, user.password, "")

    init {
        errorLog("TokenServerViewModel", "init")
    }
    fun getTokenServer(): String? = runBlocking {
        errorLog("TokendServerViewModel","getTokenServer")
        service.getJwtToken()
    }

    fun verifyTokenValidity(token: String?): String = runBlocking {
        errorLog("TokendServerViewModel", "verifyTokenValidity")
        token?.let {
            val verifyingService: JWTTokenService = JWTTokenService.create(user.name, user.password, token)
            verifyingService.verifyToken()
        } ?: TokenState.NoToken.server
    }
}