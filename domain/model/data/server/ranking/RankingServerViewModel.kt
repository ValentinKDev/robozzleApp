package com.mobilegame.robozzle.domain.model.data.server.ranking

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.ranking.RankingService
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class RankingServerViewModel(
    context: Context
): ViewModel() {
    private val service: RankingService = RankingService.create(TokenVM(context).getToken())
    private val userDataStore = UserDataStoreViewModel(context)

    init {
        errorLog("Ranking server VM", "init")

    }
    fun getLevelRanking(levelId: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
            service.getWinnerListJson(levelId).toListPlayerWin()
    }

//    fun postListLevelWin(listLevelWin: List<LevelWin>, user: User) {
//        viewModelScope.launch(Dispatchers.IO) {
//            service.postLevelWinListJson(listLevelWin.toJsonString(), user)
//            service.postLevelWinListJson(listLevelWin.toString(), user)
//        }
//    }

    fun postPlayerWin(levelId: Int, points: Int, winDetails: WinDetails): String = runBlocking(Dispatchers.IO) {
        var ret = "no user"
        userDataStore.getName()?.let { _playerName ->
            val playerWin = PlayerWin(
                playerName = _playerName,
                points = points,
                winDetails = winDetails
            )
                infoLog("postPlayerWin", "to server")
                ret = service.postPlayerWinJson(playerWin = playerWin, levelId = levelId)
        }
        ret
    }

    fun getLevelWins(): List<LevelWin> = runBlocking(Dispatchers.IO) {
        val json =
            service.getPlayerWinJson(userDataStore.getUser())
        errorLog("RankingServerVM::getLevelWins", "Json : ${json}}")
        val list = json.toListLevelWin()
        errorLog("RankingServerVM::getLevelWins", "list : ${list}}")
        list
    }
}