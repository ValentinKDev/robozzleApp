package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.prettyPrint
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.utils.Extensions.containsNotAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//@InternalCoroutinesApi
class RankVM(
    context: Context
//): AndroidViewModel(context as Application) {
): ViewModel() {
//    private val context = getApplication<Application>()
    val levelWinRoomVM = LevelWinRoomViewModel(context)
    val rankingServerVM = RankingServerViewModel(context)
    val userDataStore = UserDataStoreViewModel(context)

    //function to post my win
    fun registerANewWin(levelId: Int, levelName: String, levelDifficulty: Int, winDetails: WinDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            errorLog("registerANewWin", "start")
            var points: Int = ( (1000 / winDetails.instructionsNumber) * levelDifficulty) - winDetails.actionsNumber
            if (points <= 0) points = 10
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
//                add the solution to the level
            }
            else errorLog("better win in stock", "already")
        }
    }

    fun getLevelRanking(levelId: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
        rankingServerVM.getLevelRanking(levelId)
    }

    fun wipeRoomRankinAndDLUsersRanking() {
        viewModelScope.launch(Dispatchers.IO) {
            levelWinRoomVM.deleteAllLevelWinRoom()
            levelWinRoomVM.addLevelWinDataList( rankingServerVM.getLevelWins() )
        }
    }

    fun compareLocalToServerLevelWin() {
        viewModelScope.launch(Dispatchers.IO) {
//            infoLog("RankVM::compareLocalToServerLevelWin", "start")
            val roomList: List<LevelWin> = levelWinRoomVM.getAllLevelWins()
            val serverList: List<LevelWin> = rankingServerVM.getLevelWins()

            if (serverList.containsNotAll(roomList)) {
                if (roomList.containsAll(serverList)) {
                    roomList.forEach { rankingServerVM.postPlayerWin(levelId = it.lvl_id, points = it.points, winDetails = it.details) }
//                rankingServerVM.postListLevelWin(roomList, userDataStore.getUser())
                }
                else {
                    //todo : wipe data process ?
                    errorLog("RankVM::compareLocalToServerLevelWin", "level win data seems to be corrupted")
                }
            }
        }
    }

    fun upDateServerLevelWinFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val roomList: List<LevelWin> = levelWinRoomVM.getAllLevelWins()
            val serverList: List<LevelWin> = rankingServerVM.getLevelWins()

            if (serverList.containsNotAll(roomList)) {
                val listToPost = roomList.filterNot { serverList.contains(it) }
//                prettyPrint( "RankVM::updateServerLevelWinFromRoom", "serverListFiltered ", listToPost )
                listToPost.forEach { rankingServerVM.postPlayerWin(levelId = it.lvl_id, points = it.points, winDetails = it.details) }
            }
        }
    }
    fun upDateLevelWinRoomFromServer() {
        val roomList: List<LevelWin> = levelWinRoomVM.getAllLevelWins()
        val serverList: List<LevelWin> = rankingServerVM.getLevelWins()

        if (roomList.containsNotAll(serverList)) {
            val listToAdd = serverList.filterNot { roomList.contains(it) }
            levelWinRoomVM.addLevelWinDataList(listToAdd)
        }
    }
}