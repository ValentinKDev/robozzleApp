package com.mobilegame.robozzle.domain.model.data.server.ranking

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.ranking.RankingService
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RankingServerViewModel(
    context: Context
): ViewModel() {
    private val service: RankingService? = RankingService.create(TokenVM(context).getToken())
    private val userDataStore = UserDataStoreViewModel(context)

    fun getLevelRanking(levelId: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
        service?.let {
            it.getWinnerListJson(levelId).toListPlayerWin()
        } ?: emptyList()
    }

    fun postPlayerWin(levelId: Int , points: Int, winDetails: WinDetails): String = runBlocking(Dispatchers.IO) {
        var ret = "no user"
//        userDataStore.getId()?.let { playerId ->
        userDataStore.getName()?.let { _playerName ->
            val playerWin = PlayerWin(
//                playerID = playerId,
                playerName = _playerName,
                points = points,
                winDetails = winDetails
            )
            service?.let { ret = it.postPlayerWinJson(playerWin = playerWin, levelId = levelId) }
        }
        ret
    }
}