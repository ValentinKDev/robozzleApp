package com.mobilegame.robozzle.domain.model.User

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Error

class ProfilViewModel(): ViewModel() {

//    private val _tabSelected = MutableLiveData(1)
    private val _tabSelected = MutableStateFlow(1)
//    val tabSeclected : MutableLiveData<Int> = _tabSelected
    val tabSeclected : StateFlow<Int> = _tabSelected
    fun SelectLoginTab() = run { _tabSelected.value = 1
        Log.e("SelectLoginTab", "${tabSeclected.value}")
    }
    fun SelectRegisterTab() = run {
        _tabSelected.value = 2
        Log.e("SelectRegisterTab", "${tabSeclected.value}")
    }
    fun IsLoginTabSelected(): Boolean {return (_tabSelected.value == 1)}
    fun IsRegisterTabSelected(): Boolean {return (_tabSelected.value == 2)}

    init {
//        _tabSelected.value = 1
    }
//    val service = UserService.create()
//
//
//    private var _userNameRegistered: String = "init"
//    fun SetUserName(newName: String) { _userNameRegistered = newName}
////    private val _userName = MutableLiveData("noName")
////    val userName = _userName
////    fun SetUserName(newName: String) { _userName.value = newName}
//
//    private var _passwordRegistered: String = "init"
//    fun Setpassword(password: String) { _passwordRegistered = password}
////    private val _password = MutableLiveData("123")
////    val password = _password
////    fun Setpassword(password: String) { _password.value = password}
////    var internalUser: User = User(1, _userNameRegistered, _passwordRegistered)
//
//    fun RegisteringNewUser(name: String, password: String) {
//        SetUserName(name)
//        Setpassword(password)
//        viewModelScope.launch(Dispatchers.Default) {
//            try {
//                service.createUser(UserRequest(_userNameRegistered, _passwordRegistered))
//            } catch (e: Error) {
//                Log.e("RegisteringNewUser", "Can't access server to Create a New User")
//            }
//        }
//    }
}
