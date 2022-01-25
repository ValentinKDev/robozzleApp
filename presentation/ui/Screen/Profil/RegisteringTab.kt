package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.User.ProfilViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.toREMOVE.PlayerData
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun DisplayRegisterTab(dataViewModel: PlayerData, vm: UserViewModel) {
    var fragPlayerName by remember { mutableStateOf("") }
    var fragPlayerPassword by remember { mutableStateOf("") }
    var isValidName = fragPlayerName.length > 1

    Column( )
    {
        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = fragPlayerName,
            onValueChange = {
                fragPlayerName = it.trim()
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = {
                //todo : warning about nonEmail id
                val label =
                    if (fragPlayerName.isEmpty()) "name / email"
                    else if (isValidName) "your login id :"
                    else if (dataViewModel.playerName.value.length < 2) { "your name can't be one character" }
                    else { "your name is incorrect" }
                Text(text = label)
            },
            isError = !isValidName,
        )
        Text("PlayerName : $fragPlayerName (is valid - $isValidName)", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier .align(Alignment.CenterHorizontally) ,
            value = fragPlayerPassword,
            onValueChange = {
                fragPlayerPassword = it.trim()
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Android, contentDescription = "pen") },
            label = {
                Text(text = "password")
            },
            enabled = isValidName,
        )

        Text("password : $fragPlayerPassword", Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(50.dp))

        ButtonRegister(fragPlayerName, fragPlayerPassword, vm)
    }
}

