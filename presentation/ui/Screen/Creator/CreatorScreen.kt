package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
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
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

//@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun CreatorScreen(navigator: Navigator) {

    infoLog("launch", "CreatorScreen()")

    val ctxt = LocalContext.current
    Column() {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick= {
//                RankVM(ctxt).postPlayerWin(
                CreatorVM(ctxt).postTest(
                    levelId = 3,
                    levelName = "machin",
                    levelDifficutlty = 1,
                    winDetails = WinDetails(
                        instructionsNumber = 4,
                        actionsNumber = 25,
                        solutionFound = listOf(
                            FunctionInstructions(
                                instructions = "U0r0",
                                colors = "gRgg",
                            )
                        )
                    )
                )
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

class CreatorVM(context: Context): ViewModel() {

    val rankingServerVM = RankingServerViewModel(context)
    fun postTest(levelId: Int, levelName: String, levelDifficutlty: Int, winDetails: WinDetails) {
        val points: Int = ( (200 / winDetails.instructionsNumber) * levelDifficutlty) - winDetails.actionsNumber
        rankingServerVM.postPlayerWin(
            levelId = levelId,
            points = points,
            winDetails = winDetails
        )
    }
}