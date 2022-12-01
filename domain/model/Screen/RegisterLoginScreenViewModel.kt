package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.prettyPrint
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.server.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.server.ServerRet
import com.mobilegame.robozzle.data.server.User.UltimateUserService
import com.mobilegame.robozzle.data.server.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.server.dto.UserRequest
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.state.UserConnectionState
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.NOTOKEN
import com.mobilegame.robozzle.domain.state.TokenState
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


//class RegisterScreenViewModel(context: Context): ViewModel() {
class RegisterLoginViewModel(application: Application): AndroidViewModel(application) {

    val tokenDataVm = TokenDataStoreViewModel( getApplication() )
    val userDataStoreVM = UserDataStoreViewModel( getApplication() )

    private var serverRet: ServerRet = ServerRet.NotAttribution

    private val _userConnectionState = MutableStateFlow(UserConnectionState.NoUser.str)
    val userConnectionState: StateFlow<String> = _userConnectionState
    fun setUserConnectionState(stateStr: String) {
        _userConnectionState.value = stateStr
        userDataStoreVM.saveUserConnectionState(stateStr)
    }

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()
    fun set_name(newName: String) {
        verbalLog("set_name", "$newName")
        _name.value = newName
        _validName.value = newName.isValid()
    }
    fun filterName() {
        set_name(name.value.filterAuthorizedName())
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
        filterName()
        viewModelScope.launch {
            getAToken(name.value, password.value)
            waitForToken()
            setUserConnectionState(
                stateStr = if (tokenState.value == TokenState.Valid.ret) getUserFromServerAndStore(
                    userName = name.value,
                    expectedState = UserConnectionState.Connected,
                    errorState = UserConnectionState.IssueWithServer
                ).str
                else
                    UserConnectionState.InvalidNameOrPassword.str
            )
            delay(500)
            if (userConnectionState.value == UserConnectionState.Connected.str) {
                RankVM(getApplication()).upDateServerLevelWinFromRoom()
                RankVM(getApplication()).upDateLevelWinRoomFromServer()
                NavViewModel(navigator).navigateToMainMenu(Screens.Profile.route)
            } else
                showTaost()
        }
    }

    fun registerOnClickListner(navigator: Navigator) {
        filterName()
        viewModelScope.launch {
            var ret = createANewUserAndGetState()
            serverRet = ret
            var serverRetFromCreation: UserConnectionState = when (ret) {
                ServerRet.Positiv -> UserConnectionState.CreatedAndNotVerified
                else -> UserConnectionState.NotCreated
            }
            if (serverRetFromCreation == UserConnectionState.CreatedAndNotVerified) {
                getAToken(name.value, password.value)
                delay(300)
                waitForToken()
                setUserConnectionState(
                    stateStr = if (tokenState.value == TokenState.Valid.ret) getUserFromServerAndStore(
                        userName = name.value,
                        expectedState = UserConnectionState.Connected,
                        errorState = UserConnectionState.IssueWithServer
                    ).str
                    else
                        UserConnectionState.InvalidNameOrPassword.str
                )
                delay(500)
            }

            if (userConnectionState.value == UserConnectionState.Connected.str) {
                RankVM(getApplication()).upDateServerLevelWinFromRoom()
                NavViewModel(navigator).navigateToMainMenu(Screens.Profile.route)
            }
            else
                showTaost()
        }
    }

    fun getConnectionErrorMessage(): String {
        return when (serverRet) {
            ServerRet.Conflict -> "This name is already taken"
            ServerRet.Exception -> "Server is unavailable for the moment"
            else -> {
                when (userConnectionState.value) {
                    UserConnectionState.ServerNotReached.str -> "can't reach servers"
                    UserConnectionState.IssueWithServer.str -> "issue with the servers"
                    UserConnectionState.Connected.str -> "created"
                    UserConnectionState.NotCreated.str -> "impossible to create user"
                    else -> "default"
                }
            }
        }
    }

    suspend fun getUserFromServerAndStore(userName: String, expectedState: UserConnectionState, errorState: UserConnectionState): UserConnectionState {
        infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "userName : ${userName}")
//        todo : what about the token State when it is deprecated and the server tels us so ?
        val ultimateUser: UltimateUserRequest? = getUltimateUserFromServer()
        infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "userNameFormServer : ${ultimateUser?.let { it.name }}")
        return (
                if (ultimateUser != null) {
                    userDataStoreVM.saveUser(
                        User(
                            ultimateUser.id.toInt(),
                            ultimateUser.name,
                            ultimateUser.password
                        )
                    )
                    expectedState
                }
                else {
                    infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "ERROR -> ultimateUser = $ultimateUser")
                    errorState
                }
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

    private suspend fun createANewUserAndGetState(): ServerRet {
        infoLog("RegisterLoginScreenVM::createANewUserAndGetState", "start")
        val ultimateUserService: UltimateUserService = UltimateUserService.create(token = NOTOKEN)
        val newUserRequest = UserRequest(name.value, password.value)
        val serverRet: ServerRet = ultimateUserService.postNewUser(newUserRequest)
        infoLog("RegisterLoginScreenVM::createANewUserAndGetState", "userconnectionState.value ${userConnectionState.value}")

        return serverRet
//        return when (serverRet) {
//            ServerRet.Positiv.ret -> UserConnectionState.CreatedAndNotVerified
//            else -> UserConnectionState.NotCreated
//        }
    }

    fun checkConnectionState(): Boolean = userConnectionState.value != UserConnectionState.Verified.str

    //todo : do i have to store the token?
    suspend fun getAToken(name: String, password: String) {
        infoLog("RegisterLoginScreenVM::getAToken", "start")
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
            infoLog("RegisterLoginScreenVM::waitForToken", "token")
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
        infoLog("RegisterLoginScrennVM::getNameInputFieldLabel", "is Empty ${name.value.isEmpty()} / is Valid ${name.value.isValid()} / is Short ${name.value.isShort()} / is Long ${name.value.isLong()}")
        return if (name.value.isEmpty()) { "name : " }
        else if (name.value.isShort()) { "your name must be longer" }
        else if (name.value.isLong()) { "your name must be shorter" }
        else { "your name is :" }
    }

    private fun String.isValid(): Boolean = !this.isShort() && !this.isLong()
    private fun String.isShort(): Boolean = this.length < 3
    private fun String.isLong(): Boolean = this.length > 15

    private fun String.filterAuthorizedName(): String {
        val nameLastIndex = this.lastIndex
        //todo: add tabulation and other weird shit in the unauthorized characters
        return (this.replace("[@.,/:*?<>|~#%&+{}()\\[\\]-]".toRegex(), ""))
//        return if (this.isNotEmpty() && "[@./:*?<>|~#%&+{}()\\[\\]-]".toRegex().matches(this[nameLastIndex].toString())) {
//            this.substringBefore(this[nameLastIndex])
//        } else { this }
    }
}

//class RegisterLoginViewModel(application: Application): AndroidViewModel(application) {
//
//    val tokenDataVm = TokenDataStoreViewModel( getApplication() )
//    val userDataStoreVM = UserDataStoreViewModel( getApplication() )
//    val rankVM = RankVM( getApplication() )
//
//    private var serverRet: ServerRet = ServerRet.NotAttribution
//
//    private val _userConnectionState = MutableStateFlow(UserConnectionState.NoUser.str)
//    val userConnectionState: StateFlow<String> = _userConnectionState
//    fun setUserConnectionState(stateStr: String) {
//        _userConnectionState.value = stateStr
//        userDataStoreVM.saveUserConnectionState(stateStr)
//    }
//
//    private val _name = MutableStateFlow<String>("")
//    val name: StateFlow<String> = _name.asStateFlow()
//    fun set_name(newName: String) {
//        verbalLog("set_name", "$newName")
//        _name.value = newName
//        _validName.value = newName.isValid()
//    }
//    fun filterName() {
//        set_name(name.value.filterAuthorizedName())
//    }
//
//    private val _tokenJwt = MutableStateFlow("")
//    val tokenJwt: StateFlow<String> = _tokenJwt.asStateFlow()
//
//    private val _validName = MutableStateFlow<Boolean>(false)
//    val nameIsValid: StateFlow<Boolean> = _validName.asStateFlow()
//
//    private val _password = MutableStateFlow<String>("")
//    val password: StateFlow<String> = _password.asStateFlow()
//
//    private fun set_password (newPassword: String) {
//        _password.value = newPassword
//        _validPassword.value = newPassword.isValid()
//    }
//
//    private val _validPassword = MutableStateFlow<Boolean>(false)
//    val passwordIsValid: StateFlow<Boolean> = _validPassword.asStateFlow()
//
//    private val _showToast= MutableStateFlow<Int>(0)
//    val showToast: StateFlow<Int> = _showToast.asStateFlow()
//    fun showTaost() { _showToast.value += 1 }
//
////    todo should warn about the impossibility to log or register with this name and password
//    private val _tokenState = MutableStateFlow<String>(TokenState.NoToken.ret)
//    val tokenState: StateFlow<String> = _tokenState.asStateFlow()
//
//    fun loginOnClickListner(navigator: Navigator) {
//        filterName()
//        viewModelScope.launch {
//            getAToken(name.value, password.value)
//            waitForToken()
//            setUserConnectionState(
//                stateStr = if (tokenState.value == TokenState.Valid.ret) getUserFromServerAndStore(
//                    userName = name.value,
//                    expectedState = UserConnectionState.Connected,
//                    errorState = UserConnectionState.IssueWithServer
//                ).str
//                else
//                    UserConnectionState.InvalidNameOrPassword.str
//            )
//            delay(500)
//            if (userConnectionState.value == UserConnectionState.Connected.str) {
////                delay(2500)
//                rankVM.upDateLevelWins()
//                NavViewModel(navigator).navigateToMainMenu(Screens.Profile.route)
//            } else
//                showTaost()
//        }
//    }
//
//    fun registerOnClickListner(navigator: Navigator) {
//        filterName()
//        viewModelScope.launch {
//            var ret = createANewUserAndGetState()
//            serverRet = ret
//            var serverRetFromCreation: UserConnectionState = when (ret) {
//                ServerRet.Positiv -> UserConnectionState.CreatedAndNotVerified
//                else -> UserConnectionState.NotCreated
//            }
//            if (serverRetFromCreation == UserConnectionState.CreatedAndNotVerified) {
//                getAToken(name.value, password.value)
//                delay(300)
//                waitForToken()
//                setUserConnectionState(
//                    stateStr = if (tokenState.value == TokenState.Valid.ret) getUserFromServerAndStore(
//                        userName = name.value,
//                        expectedState = UserConnectionState.Connected,
//                        errorState = UserConnectionState.IssueWithServer
//                    ).str
//                    else
//                        UserConnectionState.InvalidNameOrPassword.str
//                )
//                delay(500)
//            }
//
//            if (userConnectionState.value == UserConnectionState.Connected.str) {
//                RankVM(getApplication()).upDateServerLevelWinFromRoom()
//                NavViewModel(navigator).navigateToMainMenu(Screens.Profile.route)
//            }
//            else
//                showTaost()
//        }
//    }
//
//    fun getConnectionErrorMessage(): String {
//        return when (serverRet) {
//            ServerRet.Conflict -> "This name is already taken"
//            ServerRet.Exception -> "Server is unavailable for the moment"
//            else -> {
//                when (userConnectionState.value) {
//                    UserConnectionState.ServerNotReached.str -> "can't reach servers"
//                    UserConnectionState.IssueWithServer.str -> "issue with the servers"
//                    UserConnectionState.Connected.str -> "created"
//                    UserConnectionState.NotCreated.str -> "impossible to create user"
//                    else -> "default"
//                }
//            }
//        }
//    }
//
//    suspend fun getUserFromServerAndStore(userName: String, expectedState: UserConnectionState, errorState: UserConnectionState): UserConnectionState {
//        infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "userName : ${userName}")
////        todo : what about the token State when it is deprecated and the server tels us so ?
//        val ultimateUser: UltimateUserRequest? = getUltimateUserFromServer()
//        infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "userNameFormServer : ${ultimateUser?.let { it.name }}")
//        return (
//                if (ultimateUser != null) {
//                    userDataStoreVM.saveUser(
//                        User(
//                            ultimateUser.id.toInt(),
//                            ultimateUser.name,
//                            ultimateUser.password
//                        )
//                    )
//                    expectedState
//                }
//                else {
//                    infoLog("RegisterLoginScreenViewModel::getUserFromserverAndStore", "ERROR -> ultimateUser = $ultimateUser")
//                    errorState
//                }
//                )
//    }
//
//    suspend fun getUltimateUserFromServer(): UltimateUserRequest? {
//        val ultimateUserService: UltimateUserService = UltimateUserService.create(tokenJwt.value)
//        val ultimateUser: UltimateUserRequest? = ultimateUserService.getUltimateUser(name.value)
//
//        //todo : more state ?
////        if (ultimateUser == null) { setUserConnectionState(UserConnection.NotConnected.state) }
////        else { setUserConnectionState(UserConnection.Connected.state) }
//        return ultimateUser
//    }
//
//    private suspend fun createANewUserAndGetState(): ServerRet {
//        infoLog("RegisterLoginScreenVM::createANewUserAndGetState", "start")
//        val ultimateUserService: UltimateUserService = UltimateUserService.create(token = NOTOKEN)
//        val newUserRequest = UserRequest(name.value, password.value)
//        val serverRet: ServerRet = ultimateUserService.postNewUser(newUserRequest)
//        infoLog("RegisterLoginScreenVM::createANewUserAndGetState", "userconnectionState.value ${userConnectionState.value}")
//
//        return serverRet
////        return when (serverRet) {
////            ServerRet.Positiv.ret -> UserConnectionState.CreatedAndNotVerified
////            else -> UserConnectionState.NotCreated
////        }
//    }
//
//    fun checkConnectionState(): Boolean = userConnectionState.value != UserConnectionState.Verified.str
//
//    //todo : do i have to store the token?
//    suspend fun getAToken(name: String, password: String) {
//        infoLog("RegisterLoginScreenVM::getAToken", "start")
////        var token = NOTOKEN
//        val jwtTokenService: JWTTokenService = JWTTokenService.create(name, password,"")
//        val tokenServer: String? = jwtTokenService.getJwtToken()
//
//        prettyPrint("RegisterLoginScreenVM::getAToken", "TokenFromServer", tokenServer, Log.WARN)
//        if (tokenServer != null) {
//            _tokenState.value = TokenState.Valid.ret
//            _tokenJwt.value = tokenServer
//            tokenDataVm.saveToken(tokenServer)
////            return token
//        } else {
//            _tokenState.value = TokenState.Invalid.ret
//        }
////        return ""
//    }
//
//    private suspend fun waitForToken() {
//        while (tokenState.value == TokenState.NoToken.ret) {
//            infoLog("RegisterLoginScreenVM::waitForToken", "token")
//            delay(50)
//        }
//    }
//
//    fun trimPasswordInput(input: String) {
//        viewModelScope.launch {
//            val newPassword = input.trim()
//            _password.value = newPassword
//            set_password(newPassword)
//        }
//    }
//
//    fun getPasswordProtected(input: String): String {
//        var passwordProtected = ""
//        repeat(input.length) { passwordProtected += "*" }
//        return passwordProtected
//    }
//
//    fun getPasswordInputFieldLabel(): String {
//        return "password :"
//    }
//
//    fun handleInputName(input: String) {
//        viewModelScope.launch {
//            set_name(input.trim().filterAuthorizedName())
//        }
//    }
//
//    fun getNameInputFieldLabel(): String {
//        //todo: protect against illegal character
//        infoLog("RegisterLoginScrennVM::getNameInputFieldLabel", "is Empty ${name.value.isEmpty()} / is Valid ${name.value.isValid()} / is Short ${name.value.isShort()} / is Long ${name.value.isLong()}")
//        return if (name.value.isEmpty()) { "name : " }
//        else if (name.value.isShort()) { "your name must be longer" }
//        else if (name.value.isLong()) { "your name must be shorter" }
//        else { "your name is :" }
//    }
//
//    private fun String.isValid(): Boolean = !this.isShort() && !this.isLong()
//    private fun String.isShort(): Boolean = this.length < 3
//    private fun String.isLong(): Boolean = this.length > 15
//
//    private fun String.filterAuthorizedName(): String {
//        val nameLastIndex = this.lastIndex
//        //todo: add tabulation and other weird shit in the unauthorized characters
//        return (this.replace("[@.,/:*?<>|~#%&+{}()\\[\\]-]".toRegex(), ""))
////        return if (this.isNotEmpty() && "[@./:*?<>|~#%&+{}()\\[\\]-]".toRegex().matches(this[nameLastIndex].toString())) {
////            this.substringBefore(this[nameLastIndex])
////        } else { this }
//    }
//}