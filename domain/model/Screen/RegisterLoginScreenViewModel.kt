package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.server.ServerRet
import com.mobilegame.robozzle.data.server.User.UltimateUserService
import com.mobilegame.robozzle.data.server.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.server.dto.UserRequest
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.state.UserConnectionState
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.NOTOKEN
import com.mobilegame.robozzle.domain.state.TokenState
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//class RegisterScreenViewModel(context: Context): ViewModel() {
class RegisterLoginViewModel(application: Application): AndroidViewModel(application) {

    val tokenDataVm = TokenDataStoreViewModel( getApplication() )
    val userDataStoreVM = UserDataStoreViewModel( getApplication() )

    private val _userConnectionState = MutableStateFlow(UserConnectionState.NoUser.str)
    val userConnectionState: StateFlow<String> = _userConnectionState
    fun setUserConnectionState(stateStr: String) {
        _userConnectionState.value = stateStr
        userDataStoreVM.saveUserConnectionState(stateStr)
    }

//    private val _connectionEstablished = MutableStateFlow<Boolean>(false)
//    val connectionEstablished: StateFlow<Boolean> = _connectionEstablished

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()
    fun set_name(newName: String) {
        verbalLog("set_name", "$newName")
        _name.value = newName
        _validName.value = newName.isValid()
    }

    private val _tokenJwt = MutableStateFlow("")
    val tokenJwt: StateFlow<String> = _tokenJwt.asStateFlow()

    private val _validName = MutableStateFlow<Boolean>(false)
    val nameIsValid: StateFlow<Boolean> = _validName.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password.asStateFlow()

    private fun set_password (newPassword: String) {
        _password.value = newPassword
        _validPassword.value = newPassword.isValid()
    }

    private val _validPassword = MutableStateFlow<Boolean>(false)
    val passwordIsValid: StateFlow<Boolean> = _validPassword.asStateFlow()

    private val _showToast= MutableStateFlow<Int>(0)
    val showToast: StateFlow<Int> = _showToast.asStateFlow()
    fun showTaost() { _showToast.value += 1 }

//    todo should warn about the impossibility to log or register with this name and password
    private val _tokenState = MutableStateFlow<String>(TokenState.NoToken.ret)
    val tokenState: StateFlow<String> = _tokenState.asStateFlow()

    fun loginOnClickListner(navigator: Navigator) {
        infoLog("login", "onclicklistner()")
        viewModelScope.launch {
            verbalLog("get a token", "start")
            getAToken(name.value, password.value)
            waitForToken()
            setUserConnectionState(
                stateStr =
                if (tokenState.value == TokenState.Invalid.ret) UserConnectionState.InvalidNameOrPassword.str
                else getUserFromServerAndStore(
                        userName = name.value,
                        expectedState = UserConnectionState.Verified,
                        errorState = UserConnectionState.IssueWithServer
                ).str
            )
            delay(500)
            if (userConnectionState.value == UserConnectionState.Connected.str) {
                val newUserRanking = viewModelScope.async(Dispatchers.IO) {
                    RankVM (getApplication()).wipeRoomRankinAndDLUsersRanking()
                }
                newUserRanking.await()
                NavViewModel(navigator).navigateTo(Screens.UserInfo)
            } else
                showTaost()
        }
    }

    fun registerOnClickListner(navigator: Navigator) {
        infoLog("register", "onclicklistner()")
        infoLog("UserConnectionState 0 ", userConnectionState.value)
        viewModelScope.launch {
            infoLog("createnewsuerandgetState", "start")
            var serverRetFromCreation = createANewUserAndGetState()
            if (serverRetFromCreation == UserConnectionState.CreatedAndNotVerified) {
                infoLog("getAToken", "start")
                getAToken(name.value, password.value)
                delay(300)
                infoLog("waitfortoken", "start")
                waitForToken()
                infoLog("getUserFromServerAndStore", "start")
                serverRetFromCreation = getUserFromServerAndStore(
                    userName = name.value,
                    expectedState = UserConnectionState.Verified,
                    errorState = UserConnectionState.IssueWithServer
                )
            }
            setUserConnectionState(serverRetFromCreation.str)

            if (userConnectionState.value == UserConnectionState.Connected.str)
                NavViewModel(navigator).navigateTo(Screens.UserInfo)
            else
                showTaost()
        }
    }

    fun getConnectionErrorMessage(): String {
        return when (userConnectionState.value) {
            UserConnectionState.ServerNotReached.str -> "can't reach servers"
            UserConnectionState.IssueWithServer.str -> "issue with the servers"
            UserConnectionState.Connected.str -> "created"
            UserConnectionState.NotCreated.str -> "impossible to create user"
            else -> "default"
        }
    }

    suspend fun getUserFromServerAndStore(userName: String, expectedState: UserConnectionState, errorState: UserConnectionState): UserConnectionState {
        infoLog("connectUserToServer", "userName : ${userName}")
//        todo : what about the token State when it is deprecated and the server tels us so ?
        val ultimateUser: UltimateUserRequest? = getUltimateUserFromServer()
        return (
                if (ultimateUser != null)
                {
                    userDataStoreVM.saveUser(
                        User(
                            ultimateUser.id.toInt(),
                            ultimateUser.name,
                            ultimateUser.password
                        )
                    )
                    expectedState
                }
                else
                    errorState
                )
    }

    suspend fun getUltimateUserFromServer(): UltimateUserRequest? {
        val ultimateUserService: UltimateUserService = UltimateUserService.create(tokenJwt.value)
        val ultimateUser: UltimateUserRequest? = ultimateUserService.getUltimateUser(name.value)

        //todo : more state ?
//        if (ultimateUser == null) { setUserConnectionState(UserConnection.NotConnected.state) }
//        else { setUserConnectionState(UserConnection.Connected.state) }
        return ultimateUser
    }

    private suspend fun createANewUserAndGetState(): UserConnectionState {
        infoLog("createANewUserAndGetState()", "start")
        val ultimateUserService: UltimateUserService = UltimateUserService.create(token = NOTOKEN)
        val newUserRequest = UserRequest(name.value, password.value)
        val serverRet: String = ultimateUserService.postNewUser(newUserRequest)
        infoLog("user connection state before", userConnectionState.value)

        return when (serverRet) {
            ServerRet.Positiv.ret -> UserConnectionState.CreatedAndNotVerified
            else -> UserConnectionState.NotCreated
        }
    }

    fun checkConnectionState(): Boolean = userConnectionState.value != UserConnectionState.Verified.str

    //todo : do i have to store the token?
    suspend fun getAToken(name: String, password: String) {
        infoLog("userVM", "getAToken()")
//        var token = NOTOKEN
        val jwtTokenService: JWTTokenService = JWTTokenService.create(name, password,"")
        val tokenServer: String? = jwtTokenService.getJwtToken()

        if (tokenServer != null) {
            _tokenState.value = TokenState.Valid.ret
            _tokenJwt.value = tokenServer
            tokenDataVm.saveToken(tokenServer)
//            return token
        } else {
            _tokenState.value = TokenState.Invalid.ret
        }
//        return ""
    }

    private suspend fun waitForToken() {
        while (tokenState.value == TokenState.NoToken.ret) {
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
//    fun trimPasswordInputAndHide(input: String) {
//        trimPasswordInput(input)
//    }

    fun getPasswordProtected(input: String): String {
        var passwordProtected = ""
        repeat(input.length) { passwordProtected += "*" }
        return passwordProtected
    }

    fun getPasswordInputFieldLabel(): String {
//        return if (!passwordIsValid.value) "your password can't be less than 3 characters"
//        else  "password"
//            else if (!passwordIsValid.value) "your password can't be less than 3 characters"
//            else ""
        return "password :"
    }

    fun handleInputName(input: String) {
        viewModelScope.launch {
            set_name(input.trim().filterAuthorizedName())
        }
    }

    fun getNameInputFieldLabel(): String {
        //todo: protect against illegal character
        infoLog("getNameInputFieldLabel", "is Empty ${name.value.isEmpty()} / is Valid ${name.value.isValid()} / is Short ${name.value.isShort()}")
        return if (name.value.isEmpty()) { "name or email" }
        else if (name.value.isShort()) { "your name can't be less than 3 characters" }
        else if (name.value.isValid()) "your login name :"
        else { "your name is incorrect" }
    }

    private fun String.isValid(): Boolean = this.length >= 3
    private fun String.isShort(): Boolean = this.length < 3

    private fun String.filterAuthorizedName(): String {
        val nameLastIndex = this.lastIndex
        //todo: add tabulation and other weird shit in the unauthorized characters
        return if (this.isNotEmpty() && "[./:*?<>|~#%&+{}()\\[\\]-]".toRegex().matches(this[nameLastIndex].toString())) {
            this.substringBefore(this[nameLastIndex])
        } else { this }
    }
}