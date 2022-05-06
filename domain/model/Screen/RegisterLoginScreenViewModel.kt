package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.server.ServerRet
import com.mobilegame.robozzle.data.server.User.UltimateUserService
import com.mobilegame.robozzle.data.server.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.server.dto.UserRequest
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.state.UserConnection
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.NOTOKEN
import com.mobilegame.robozzle.domain.state.TokenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//class RegisterScreenViewModel(context: Context): ViewModel() {
class RegisterLoginViewModel(application: Application): AndroidViewModel(application) {

    val tokenDataVm = TokenDataStoreViewModel(
        getApplication()
    )
    val userDataStoreVM = UserDataStoreViewModel(
        getApplication()
    )

    private val _userConnectionState = MutableStateFlow(UserConnection.NoUser.state)
    val userConnectionState: StateFlow<String> = _userConnectionState
    fun setUserConnectionState(state: String) {
        _userConnectionState.value = state
        userDataStoreVM.saveUserConnectionState(state)
    }

    private val _connectionEstablished = MutableStateFlow<Boolean>(false)
    val connectionEstablished: StateFlow<Boolean> = _connectionEstablished


    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()
    fun set_name(newName: String) {
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

    private val _canNotLog = MutableStateFlow<Int>(0)
    val canNotLog: StateFlow<Int> = _canNotLog.asStateFlow()

//    todo should warn about the impossibility to log or register with this name and password
    private val _tokenState = MutableStateFlow<String>(TokenState.NoToken.ret)
    val tokenState: StateFlow<String> = _tokenState.asStateFlow()

    fun loginOnClickListner() {
        infoLog("login", "onclicklistner()")
        viewModelScope.launch {
            //get a token
            var connection = UserConnection.NoUser.state
            getAToken(name.value, password.value)
            if (tokenState.value == TokenState.Invalid.ret) _canNotLog.value += 1
            else {
                waitForToken()
                //get the ultimate user by using the id
                //save ultimateUser
                connection = getUserFromServerAndStore(
                    userName = name.value,
                    expectedState = UserConnection.CreatedAndVerified.state,
                    errorState = UserConnection.NotConnected.state
                )
            }
            infoLog("going to set user connection to ", if (connection != UserConnection.NotConnected.state) { UserConnection.Connected.state } else connection )
            setUserConnectionState(
                state =  if (connection != UserConnection.NotConnected.state) {
                    UserConnection.Connected.state
                } else connection
            )
            delay(500)
            _connectionEstablished.value = checkConnectionState()

            //dl stats from server
            if (connection == UserConnection.Connected.state) RankVM(getApplication()).wipeRoomRankinAndDLUsersRanking()
        }
    }

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
                delay(300)
                infoLog("waitfortoken", "start")
                waitForToken()
                //get the id of the new user with the token
                //store the user with the id and the connection state
                infoLog("getUserFromServerAndStore", "start")
                connectionState = getUserFromServerAndStore(
                    userName = name.value,
                    expectedState = UserConnection.CreatedAndVerified.state,
                    errorState = UserConnection.NotConnected.state
                )
            }
            errorLog("set user connection state", connectionState)
            setUserConnectionState(connectionState)
            //todo: use this connection established flow to clean the navigation logic
            _connectionEstablished.value = checkConnectionState()
        }
    }

    suspend fun getUserFromServerAndStore(userName: String, expectedState: String, errorState: String): String {
        infoLog("connectUserToServer", "userName : ${userName}")
//        val returnState:
//        todo : what about the token State when it is deprecated and the server tels us so ?
        val ultimateUser: UltimateUserRequest? = getUltimateUserFromServer()
        //todo : UltimateUser to User ?
        return ( if (ultimateUser != null)
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
        {
            _canNotLog.value += 1
            errorState }
                )
    }
//        ultimateUser?.let {
//        if (ultimateUser != null) {
//            val userId = try {
//                ultimateUser.id.toInt()
//            } catch (e: NumberFormatException) {
//                errorLog("getUserFromServer", "${e.message}")
//                ERROR
//            }
//            userDataStoreVM.saveUser(
//                User(
//                    userId,
//                    ultimateUser.name,
//                    ultimateUser.password
//                )
//            )
//        } else _canNotLog.value += 1
//        return if (ultimateUser == null) errorState else expectedState


    suspend fun getUltimateUserFromServer(): UltimateUserRequest? {
        val ultimateUserService: UltimateUserService = UltimateUserService.create(tokenJwt.value)
        val ultimateUser: UltimateUserRequest? = ultimateUserService.getUltimateUser(name.value)

        //todo : more state ?
//        if (ultimateUser == null) { setUserConnectionState(UserConnection.NotConnected.state) }
//        else { setUserConnectionState(UserConnection.Connected.state) }
        return ultimateUser
    }

    private suspend fun createANewUserAndGetState(): String {
        infoLog("createANewUserAndGetState()", "start")
        val ultimateUserService: UltimateUserService = UltimateUserService.create(token = NOTOKEN)
        val newUserRequest = UserRequest(name.value, password.value)
        val serverRet: String = ultimateUserService.postNewUser(newUserRequest)
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
    }

    fun checkConnectionState(): Boolean = userConnectionState.value != UserConnection.CreatedAndVerified.state

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

//    fun navigation(navigDestination: NavigationDestination, navigator: Navigator) {
//        viewModelScope.launch() {
//            navigator.navig(navigDestination)
//        }
//    }
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
