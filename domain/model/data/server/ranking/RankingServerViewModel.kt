package com.mobilegame.robozzle.domain.model.data.server.ranking

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.data.server.ranking.LevelRankingService
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RankingServerViewModel(
    context: Context
): ViewModel() {
    lateinit var service: LevelRankingService

    init {
        viewModelScope.launch(Dispatchers.IO) {
            TokenVM(context).getToken().let { token ->
                service = LevelRankingService.create(token!!)
            }
        }
    }

    fun getLevelRanking(levelId: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
        service.getWinnerListJson(levelId).toListPlayerWin()
    }
}