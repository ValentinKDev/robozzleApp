package com.mobilegame.robozzle.domain.model.room.playerRanks

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.base.PlayerRanks.LevelResolvedData
import com.mobilegame.robozzle.data.base.PlayerRanks.PlayerRanksDao
import com.mobilegame.robozzle.data.base.PlayerRanks.PlayerRanksDataBase
import com.mobilegame.robozzle.domain.LevelResolved.LevelResolved
import com.mobilegame.robozzle.domain.LevelResolved.PlayerRanks
import com.mobilegame.robozzle.domain.model.room.level.toLevelResolvedData
import com.mobilegame.robozzle.domain.model.room.level.toLevelResolvedDataList
import com.mobilegame.robozzle.domain.model.room.level.toLevelResolvedType
import com.mobilegame.robozzle.domain.repository.PlayerRanksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class PlayerRanksRoomViewModel(context: Context): ViewModel() {
    private val dao: PlayerRanksDao = PlayerRanksDataBase.getInstance(context).playerRanksDao()
    private val repo: PlayerRanksRepository = PlayerRanksRepository(dao)

    fun getLevelResolved(idLevel: Int): LevelResolved? = runBlocking(Dispatchers.IO) {
        var levelResolved: LevelResolved? = null
        repo.getLevelResolved(idLevel)?.let {
            levelResolved = it.toLevelResolvedType()
        }
        levelResolved
    }

    fun getAllLevelResolved(): PlayerRanks = runBlocking(Dispatchers.IO) {
        val listLevelResolvedData: List<LevelResolvedData> = repo.getPlayerRanksFromRoom()
        val listLevelResolved : List<LevelResolved> = listLevelResolvedData.toLevelResolvedType()
        PlayerRanks(resolved = listLevelResolved)
    }

    fun addALevelResolved(lvlResolved: LevelResolved) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addLevelResolved(lvlResolved.toLevelResolvedData())
        }
    }

    fun addPlayerRanks(playerRanks: PlayerRanks) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addListLevelResolved(playerRanks.resolved.toLevelResolvedDataList())
        }
    }
}



