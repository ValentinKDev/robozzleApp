package com.mobilegame.robozzle.domain.model.Screen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.base.UltimateUser.User
import com.mobilegame.robozzle.data.remote.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.remote.User.ServerRet
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.UserConnection
import com.mobilegame.robozzle.domain.UserConnectionState
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.domain.model.store.TokenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.ERROR
import com.mobilegame.robozzle.domain.res.NOTOKEN
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

//class RegisterScreenViewModel(context: Context): ViewModel() {
class RegisterScreenViewModel(application: Application): AndroidViewModel(application) {

    val tokenDataVm = TokenDataStoreViewModel(
        service = DataStoreService.createTokenService(getApplication())
    )
    val userDataStoreVM = UserDataStoreViewModel(
        service = DataStoreService.createUserService(getApplication())
    )

    private val _userConnectionState = MutableStateFlow(UserConnection.NoUser.state)
    val userConnectionState: StateFlow<String> = _userConnectionState
    fun setUserConnectionState(state: String) {
        _userConnectionState.value = state
        userDataStoreVM.saveUserConnectionState(state)
    }

    private val _tabSelected = MutableStateFlow(1)
    val tabSeclected : StateFlow<Int> = _tabSelected

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name
    fun set_name(newName: String) {
        _name.value = newName
        _validName.value = newName.isValid()
    }

    private val _tokenJwt = MutableStateFlow(NOTOKEN) ;
    val tokenJwt: StateFlow<String> = _tokenJwt

    private val _validName = MutableStateFlow<Boolean>(false)
    val nameIsValid: StateFlow<Boolean> = _validName

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private fun set_password (newPassword: String) {
        _password.value = newPassword
        _validPassword.value = newPassword.isValid()
    }

    private val _validPassword = MutableStateFlow<Boolean>(false)
    val passwordIsValid: StateFlow<Boolean> = _validPassword

    fun registerOnClickListner() {
        infoLog("register", "onclicklistner()")
        viewModelScope.launch {
//            var connectionState = UserConnection.NoUser.state
            //create a new user to server
            infoLog("createnewsuerandgetState", "start")
            var connectionState = createANewUserAndGetState()
            if (connectionState == UserConnection.Created.state) {
                //get a token for the new user
                    infoLog("getAToken", "start")
                getAToken(name.value, password.value)
                infoLog("waitfortoken", "start")
                waitForToken()
                delay(500)
                //get the id of the new user with the token
                //store the user with the id and the connection state
                infoLog("getUserFromServerAndStore", "start")
                connectionState = getUserFromServerAndStore(name.value, password.value)
            }
            setUserConnectionState(connectionState)
        }
    }

    suspend fun getUserFromServerAndStore(userName: String, token: String): String {
        infoLog("connectUserToServer", "userName : ${userName} token : ${token}")
//        val returnState:
        val ultimateUser: UltimateUserRequest? = getUltimateUserFromServer()
        //todo : UltimateUser to User ?
        ultimateUser?.let {
            val userId = try {
                ultimateUser.id.toInt()
            } catch (e: NumberFormatException) {
                errorLog("getUserFromServer", "${e.message}")
                ERROR
            }
            userDataStoreVM.saveUser(
                User(
                    userId,
                    ultimateUser.name,
                    ultimateUser.password
                )
            )
        }
        return if (ultimateUser == null) UserConnection.NotConnected.state else UserConnection.CreatedAndVerified.state
    }


    suspend fun getUltimateUserFromServer(): UltimateUserRequest? {
        val userService: UserService = UserService.create(tokenJwt.value)
        val ultimateUser: UltimateUserRequest? = userService.getUltimateUser(name.value)

        //todo : more state ?
//        if (ultimateUser == null) { setUserConnectionState(UserConnection.NotConnected.state) }
//        else { setUserConnectionState(UserConnection.Connected.state) }
        return ultimateUser
    }

    private suspend fun createANewUserAndGetState(): String {
        infoLog("createANewUserAndGetState()", "start")
        val userService: UserService = UserService.create(token = NOTOKEN)
        val newUserRequest = UserRequest(name.value, password.value)
        val serverRet: String = userService.postNewUser(newUserRequest)
        infoLog("user connection state before", userConnectionState.value)

        return when (serverRet) {
            ServerRet.Positiv.ret -> {
                UserConnection.Created.state
            }
            else -> {
//                UserConnection.NotConnected.state
                UserConnection.NotCreated.state
            }
        }
//        setUserConnectionState( when (serverRet) {
//            ServerRet.Positiv.ret -> {
//                UserConnection.Created.state
//            }
//            else -> {
//                UserConnection.NotConnected.state
//            }
//        })
    }

//    fun newUserCreationProcess() {
//        viewModelScope.launch {
//            coroutineScope {
//                var token = NOTOKEN
//                token = getAToken(registLogVM.name.value, registLogVM.password.value)
//                waitForToken()
//                connectUserToServer(registLogVM.name.value, token)
//            }
//        }
//    }

    //todo : do i have to store the token?
    suspend fun getAToken(name: String, password: String): String {
        infoLog("userVM", "getAToken()")
        var token = NOTOKEN
        val jwtTokenService: JWTTokenService = JWTTokenService.create(name, password)
        token = jwtTokenService.getJwtToken()

        _tokenJwt.value = token
        tokenDataVm.saveToken(token)

        return token
    }

    private suspend fun waitForToken() {
        while (tokenJwt.value == NOTOKEN) {
            infoLog("wait", "token")
            delay(50)
        }
    }


    fun trimPasswordInput(input: String) {
        viewModelScope.launch {
            val newPassword = input.trim()
            _password.value = newPassword
            set_password(newPassword)
        }
    }

    fun getPasswordInputFieldLabel(): String {
        return if (password.value.isEmpty()) "password"
            else if (!passwordIsValid.value) "your password can't be less than 3 characters"
            else ""
    }

    fun handleInputName(input: String) {
        viewModelScope.launch { set_name(input.trim().filterAuthorizedName()) }
    }

    fun getNameInputFieldLabel(): String {
        return if (name.value.isEmpty()) { "name or email" }
        else if (name.value.isValid()) "your login name :"
        else {
            if (name.value.isShort()) { "your name can't be less than 3 characters" }
            //todo: protect against illegal character
            else { "your name is incorrect" }
        }
    }

    private fun String.isValid(): Boolean = this.length >= 3
    private fun String.isShort(): Boolean = this.length < 3

    private fun String.filterAuthorizedName(): String {
        val nameLastIndex = this.lastIndex
        //todo: add tabulation and other weird shit in the unauthorized characters
        return if (this.isNotEmpty() && "[./:*?<>|~#%&+{}-]".toRegex().matches(this[nameLastIndex].toString())) {
            this.substringBefore(this[nameLastIndex])
        } else { this }
    }

    fun SelectLoginTab() = run { _tabSelected.value = 1
        Log.e("SelectLoginTab", "${tabSeclected.value}")
    }
    fun SelectRegisterTab() = run {
        _tabSelected.value = 2
        Log.e("SelectRegisterTab", "${tabSeclected.value}")
    }
}

//@InternalCoroutinesApi
//class RegisterScreenViewModelFactory(
//    private val application: Application
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        if (modelClass.isAssignableFrom(RegisterScreenViewModel::class.java)) {
//            return RegisterScreenViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
