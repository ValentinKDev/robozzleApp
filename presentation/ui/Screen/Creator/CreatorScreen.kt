package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

//@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared) {

    infoLog("launch", "CreatorScreen()")

    val ctxt = LocalContext.current
    Column() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
                     NavViewModel(navigator).navigateTo(Screens.Donation)
            },
        ) {
            Text(text = "1")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                     LoginViewModel(navigator).navToMain("truc", "machin")
            },
        ) {
            Text(text = "2")
        }
    }
}

//class CreatorVM(context: Context): ViewModel() {
//
//}
class TestShared(): ViewModel() {
    var shared: MutableSharedFlow<String> = MutableSharedFlow()
    var sha: SharedFlow<String> = shared.asSharedFlow()

    suspend fun navig(str: String) {
        shared.emit(str)
    }
}