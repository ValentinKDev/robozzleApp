package com.mobilegame.robozzle.domain.model.User

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.remote.User.ServerRet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterLoginViewModel(): ViewModel() {

    private val _tabSelected = MutableStateFlow(1)
    val tabSeclected : StateFlow<Int> = _tabSelected

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name
    fun set_name(newName: String) {
        _name.value = newName
        _validName.value = newName.isValid()
    }

    private val _validName = MutableStateFlow<Boolean>(false)
    val nameIsValid: StateFlow<Boolean> = _validName

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password
//    val password: StateFlow<String> = _password

    private fun set_password (newPassword: String) {
        _password.value = newPassword
        _validPassword.value = newPassword.isValid()
    }

    private val _validPassword = MutableStateFlow<Boolean>(false)
    val passwordIsValid: StateFlow<Boolean> = _validPassword

    private val _registerReturn = MutableStateFlow(ServerRet.NotAttribution.ret)
    var registerReturn: StateFlow<String> = _registerReturn
    fun resetRegisterReturn() { _registerReturn.value = ServerRet.NotAttribution.ret}

    fun trimPasswordInput(input: String) {
        viewModelScope.launch {
//            val newPassword = password.value.trim()
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