package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark2
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginTab(navigator: Navigator, vm: RegisterLoginViewModel = viewModel()) {

    val name by remember(vm) {vm.name}.collectAsState( initial = "" )
    val password by remember(vm) {vm.password}.collectAsState( initial = "" )
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val isValidName: Boolean = vm.nameIsValid.value
    val isValidPassword: Boolean = vm.passwordIsValid.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val showErrorMessage by vm.showToast.collectAsState()
    val ctxt = LocalContext.current

    Column( )
    {
        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = name,
            onValueChange = { vm.handleInputName(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = { Text(text = vm.getNameInputFieldLabel()) },
            isError = !isValidName,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = whiteDark2),
        )


        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            label = { Text(vm.getPasswordInputFieldLabel()) },
            value = password,
            onValueChange = {
                vm.trimPasswordInput(it)
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            isError = !isValidPassword,
            enabled = isValidName,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = whiteDark2),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "password unprotected" else "password protected"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, contentDescription = description)
                }
            }
        )


        Spacer(modifier = Modifier.height(50.dp))


        val enableButton = isValidName && isValidPassword
        ButtonRegisterLogin(
            enable = enableButton,
            text = "Login",
            clickable = Modifier.clickable {
                if (enableButton) {
                    keyboardController?.hide()
                    vm.loginOnClickListner(navigator)
                }
            }
        )
        if (showErrorMessage != 0)
            Toast.makeText(ctxt, "Nope", Toast.LENGTH_SHORT).show()
    }
}
