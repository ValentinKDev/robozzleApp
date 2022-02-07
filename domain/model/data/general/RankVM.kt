package com.mobilegame.robozzle.domain.model.data.general

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.User.ServerRet
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class RankVM(
    context: Context
//): AndroidViewModel(context as Application) {
): ViewModel() {
//    private val context = getApplication<Application>()
    val levelWinRoomVM = LevelWinRoomViewModel(context)
    val rankingServerVM = RankingServerViewModel(context)

    //function to post my win
    fun postPlayerWin(levelId: Int, levelName: String, levelDifficulty: Int, winDetails: WinDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            errorLog("PostPlayerWin", "start")
            val points: Int = ( (1000 / winDetails.instructionsNumber) * levelDifficulty) - winDetails.actionsNumber
            errorLog("points", "$points")
            if (levelWinRoomVM.noBetterInStock(levelId, points)) {
                rankingServerVM.postPlayerWin(
                    levelId = levelId,
                    points = points,
                    winDetails = winDetails
                )
                //to my room of LevelWin
                levelWinRoomVM.addLevelWinData(
                    levelId = levelId,
                    levelName = levelName,
                    points = points,
                    winDetails = winDetails
                )
            }
            else errorLog("better win in stock", "already")

        }
    }

    //function to get the ranking of a Level from server
    fun getLevelRanking(levelId: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
        rankingServerVM.getLevelRanking(levelId)
    }
}