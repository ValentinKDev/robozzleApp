package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.BuildConfig
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.room.Config.ConfigRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.AppConfigDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.appConfig.AppConfigServerViewModel
import com.mobilegame.robozzle.domain.model.data.server.level.LevelServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import kotlinx.coroutines.*

class LaunchingViewModel(application: Application): AndroidViewModel(application) {

    private val levelRoomVM = LevelRoomViewModel(getApplication())
    private val levelServerVM = LevelServerViewModel()
    private val appConfigServerVM = AppConfigServerViewModel()
    private val screenDataStore = ScreenDataStoreViewModel(getApplication())
    private val appConfigDataStoreVM = AppConfigDataStoreViewModel(getApplication())
    private val rankVM = RankVM(getApplication())
    val configData = ConfigRoomViewModel(getApplication())

//    fun launch(layoutCoordinates: LayoutCoordinates, navigator: Navigator) {
//    fun launch(layoutCoordinates: LayoutCoordinates) {
    fun launch() {

        Log.w("LaunchingVM::launch", "********* START **********")
        viewModelScope.launch {
            //load and check versions

            Log.w("LaunchingVM::launch", "version code ${BuildConfig.VERSION_CODE}")
            Log.w("LaunchingVM::launch", "version name ${BuildConfig.VERSION_NAME}")

            loadAndCheckAppVersion()

//            configData.instantiateConfig()
            infoLog("get list level Id", "local")
            val localListLevelsId: List<Int> = levelRoomVM.getLevelIds()
            infoLog("-> local list level Id", "$localListLevelsId")
            if (localListLevelsId.isEmpty()) {
                //Start all the dl process
                infoLog("start", "all dl process")
                infoLog("get list of ALL LevelRequest", "server")
                val listOfAllLevels = levelServerVM.getAllLevelList()
                infoLog("add to level Room", "list of LevelsRequest size ${listOfAllLevels.size}")
                errorLog("add to level Room", "list of LevelsRequest size ${listOfAllLevels.size}")
                levelRoomVM.addLevels(listOfAllLevels)
            } else {
                //get list of level s id from server
                infoLog("get list level id", "server")
                val serverListLevelsId: List<Int> = levelServerVM.getLevelIdList()
                infoLog("->server list level id", "$serverListLevelsId")
                if (serverListLevelsId.isEmpty()) errorLog(
                    "error",
                    "server list level's id is empty"
                )
                else {
                    //compare list
                    if (!localListLevelsId.containsAll(serverListLevelsId)) {
                        //get level list to add to local data from server
                        val listOfLevelIdMissing: List<Int> =
                            serverListLevelsId.filterNot { localListLevelsId.contains(it) }
                        infoLog("list of level Id Missing", "$listOfLevelIdMissing")
                        infoLog("get list of level Id Missing", "server")
                        val listOfLevelMissingJson: List<String> =
                            levelServerVM.getLevelListById(listOfLevelIdMissing)
                        infoLog(
                            "-> list of level Id Missing size",
                            "${listOfLevelMissingJson.size}"
                        )
                        //update levels needing to be updated
//                            infoLog("add list of level Id Missing", "$listOfLevelIdMissing")
                        levelRoomVM.addLevels(listOfLevelMissingJson)
                    }
                    //check if user in data store exist
                    //if it does check if levelWins are stored in room
                    rankVM.compareLocalToServerLevelWin()
                    //if it does load the wins from the server for this player
                    //compare them
                    //add the ones from room to server
                }
//                navigator.reload_launcher(false)
            }
        }
        //todo: check if all the wins are well uploaded to the server
        //check if user in data store exist
        //if it does check if levelWins are stored in room
        //if it does load the wins from the server for this player
        //compare them
        //add the ones from room to server
        Log.e("END init", "LaunchingViewModel::launch")
//            infoLog("Main Menu View Model", "init end")
    }

    fun loadAndCheckAppVersion() = runBlocking(Dispatchers.IO) {
        val localVersion: String = BuildConfig.VERSION_NAME
//        val localVersion: String = "0.12"
        Log.w("LaunchingVM::loadAndCheckAppVersion", "local version ${localVersion}")
        val serverVersion: String? = appConfigServerVM.getVersion()
        Log.w("LaunchingVM::loadAndCheckAppVersion", "server version ${serverVersion}")


        val popupState = serverVersion?.let {
            if (localVersion == serverVersion)
                PopUpState.None
            else PopUpState.Update
        } ?: PopUpState.ServerIssue
        screenDataStore.storePopUpState(popupState)
    }
}