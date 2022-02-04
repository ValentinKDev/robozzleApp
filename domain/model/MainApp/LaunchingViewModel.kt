package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.store.AppConfigDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.appConfig.AppConfigServerViewModel
import com.mobilegame.robozzle.domain.model.data.server.level.LevelServerViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@InternalCoroutinesApi
//class LaunchingViewModel(application: Application): AndroidViewModel(application) {
class LaunchingViewModel(context: Context): ViewModel() {
//    val userDataVM = UserViewModel(context as Application)

//    val levelRoomVM = LevelRoomViewModel(getApplication())
    val levelRoomVM = LevelRoomViewModel(context)
    private val levelServerVM = LevelServerViewModel()
    private val appConfigDataStoreVM = AppConfigDataStoreViewModel(context)
    private val appConfigServerVM = AppConfigServerViewModel()

    init {
        }

        fun launch() {

            viewModelScope.launch {
                Log.e("init", "MainMenuViewModel()")

                //load version
                infoLog("get version", "local")
                var localVersion: String? = appConfigDataStoreVM.getVersion()
                infoLog("-> local version", "$localVersion")
                infoLog("get version", "server")
                val serverVersion: String? = appConfigServerVM.getVersion()
                infoLog("-> sever version", "$serverVersion")

                if (localVersion == null || serverVersion != localVersion) {
                    if (localVersion == null)
                        errorLog("first connection", "Welcome to Robuzzle")
                    //Start all the dl process
                    infoLog("start", "all dl process")
                    serverVersion?.let {
                        infoLog("add to app config dataStore", "server version")
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
                    infoLog("-> local list level Id", "$localListLevelsId")
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
                        infoLog("->server list level id", "$serverListLevelsId")
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
