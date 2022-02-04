package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import kotlinx.coroutines.InternalCoroutinesApi

//@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen() {

    infoLog("launch", "CreatorScreen()")
//    val co by mUserViewModel.connected.collectAsState()

//    val co by remember(mUserViewModel) {mUserViewModel.collectConnectedState()}.collectAsState(
//        initial = UserConnectionState.Connected
//    )
//        initial = UserConnectionState.NOUSER
//    )
//    infoLog("co", co.toString())


    Column() {
//        val tes= RankVM(LocalContext.current).getLevelRanking(5)
        val ctxt = LocalContext.current
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                     TokenVM(LocalContext.current as Application)
//                infoLog("num before", "${testViewModel.num}")
//                testViewModel.num = -42
//                infoLog("num after", "${testViewModel.num}")
                val test = TokenVM(ctxt).getToken()
            },
        ) {
            Text(text = "2")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                     mUserViewModel.connectedTO(UserConnectionState.CONNECTED)
            },
        ) {
//            Text(text = "${co}")
        }
    }
}


class testViewModel(): ViewModel() {
    var num = 0
    init {
    }

}