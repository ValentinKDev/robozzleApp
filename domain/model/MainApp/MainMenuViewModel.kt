package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.server.Level.LevelService
import com.mobilegame.robozzle.data.server.dto.LevelRequest
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.store.AppConfigDataStoreViewModel
import com.mobilegame.robozzle.domain.model.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.server.appConfig.AppConfigServerViewModel
import com.mobilegame.robozzle.domain.model.server.level.LevelServerViewModel
import com.mobilegame.robozzle.domain.res.ERROR
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
class MainMenuViewModel(application: Application): AndroidViewModel(application) {

    val userDataVM = UserViewModel(getApplication())

    private val levelRoomVM = LevelRoomViewModel(getApplication())
    private val levelServerVM = LevelServerViewModel()
    private val appConfigDataStoreVM = AppConfigDataStoreViewModel(getApplication())
    private val appConfigServerVM = AppConfigServerViewModel()

    init {
        viewModelScope.launch {
            Log.e("init", "MainMenuViewModel()")

            //load version
            infoLog("get version", "local")
            var localVersion: String? = appConfigDataStoreVM.getVersion()
            infoLog("-> local version", "$localVersion")

            if (localVersion == null) {
                //Start all the dl process
                infoLog("start", "all dl process")
                infoLog("get version", "server")
                val serverVersion: String? = appConfigServerVM.getVersion()
                infoLog("-> sever version", "$serverVersion")
                serverVersion?.let {
                    infoLog("add to app config dataStore", "version")
                    appConfigDataStoreVM.setVersion(it)
                    infoLog("get list of ALL LevelRequest", "server")
                    val listOfAllLevels: List<String> = levelServerVM.getAllLevelList()
                    infoLog("add to level Room", "list of LevelsRequest size ${listOfAllLevels.size}")
                    levelRoomVM.addLevels(listOfAllLevels)
                }
            } else {
                //load list of levels id from room
                infoLog("get list level Id", "local")
                val localListLevelsId: List<Int> = levelRoomVM.getLevelIds()
                infoLog("-> list level Id", "$localListLevelsId")
                if (localListLevelsId.isEmpty()) {
                    //Start all the dl process
                    infoLog("start", "all dl process")
                    infoLog("get list of ALL LevelRequest", "server")
                    val listOfAllLevels = levelServerVM.getAllLevelList()
                    infoLog("add to level Room", "list of LevelsRequest size ${listOfAllLevels.size}")
                    levelRoomVM.addLevels(listOfAllLevels)
                } else {
                    //get list of level s id from server
                    infoLog("get list level id", "server")
                    val serverListLevelsId: List<Int> = levelServerVM.getLevelIdList()
                    infoLog("-> list level id", "$serverListLevelsId")
                    if (serverListLevelsId.isEmpty()) errorLog("error", "server list level's id is empty")
                    else {
                        //compare list
                        if (!localListLevelsId.containsAll(serverListLevelsId)) {
                            //get level list to add to local data from server
                            val listOfLevelIdMissing: List<Int> = serverListLevelsId.filterNot { localListLevelsId.contains(it) }
                            infoLog("list of level Id Missing", "$listOfLevelIdMissing")
                            infoLog("get list of level Id Missing", "server")
                            val listOfLevelMissingJson: List<String> = levelServerVM.getLevelListById(listOfLevelIdMissing)
                            infoLog("-> list of level Id Missing size", "${listOfLevelMissingJson.size}")
                            //update levels needing to be updated
//                            infoLog("add list of level Id Missing", "$listOfLevelIdMissing")
                            levelRoomVM.addLevels(listOfLevelMissingJson)
                        }
                    }
                }
            }
            //load list from room ? or use the differents difficulty as only UI
        }
        infoLog("Main Menu View Model", "init end")
    }
}
