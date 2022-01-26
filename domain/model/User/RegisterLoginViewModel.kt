package com.mobilegame.robozzle.domain.model.User

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.User.ServerSend
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.User.UserServiceReturn
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.domain.res.UNKNOWN
import com.mobilegame.robozzle.res.POSITIV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class RegisterLoginViewModel(): ViewModel() {

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private val _registerReturn = MutableStateFlow<String>(ServerSend.NOTRECEIVED.ret)
    var registerReturn: StateFlow<String> = _registerReturn
    fun resetRegisterReturn() { _registerReturn.value = ServerSend.NOTRECEIVED.ret}

    //todo: tab datastored
//    private val _tab = MutableStateFlow<Int>("")
//    val tab: StateFlow<Int> = _tab

    val userService = UserService.create()

    suspend fun RegisterProcess(name: String, password: String) {
        _name.value = name
        _password.value = password
         SendNewUserToServer()
    }

    private suspend fun SendNewUserToServer() {
        infoLog("..Send New User To Server()", "_name ${_name.value} _password ${_password.value}")
            withContext(Dispatchers.IO) {
                _registerReturn.value = userService.postNewUser(UserRequest(_name.value, _password.value))
            }
    }
}