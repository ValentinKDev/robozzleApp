package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.PopUpState
import com.mobilegame.robozzle.data.configuration.RobuzzleConfiguration
import com.mobilegame.robozzle.data.configuration.ScreenConfig
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.store.AppConfigDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.appConfig.AppConfigServerViewModel
import com.mobilegame.robozzle.domain.model.data.server.level.LevelServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.ScreenDimensionsDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.ScreenData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

//class LaunchingViewModel(application: Application): AndroidViewModel(application) {
class LaunchingViewModel(context: Context): ViewModel() {

    private val levelRoomVM = LevelRoomViewModel(context)
    private val levelServerVM = LevelServerViewModel()
    private val appConfigDataStoreVM = AppConfigDataStoreViewModel(context)
    private val appConfigServerVM = AppConfigServerViewModel()
    private val screenDimensionDataStore = ScreenDimensionsDataStoreViewModel(context)
    private val rankVM = RankVM(context)

        fun launch(screenConfig: ScreenConfig) {

            viewModelScope.launch {
                Log.e("init", "MainMenuViewModel()")

                //store screenDimension
                screenDimensionDataStore.storeDimensionDensity()

                //load version
                infoLog("get version", "local")
//                var localVersion: String? = appConfigDataStoreVM.getVersion()
                val localVersion: String = RobuzzleConfiguration.version
                infoLog("-> local version", "$localVersion")
                infoLog("get version", "server")
                val serverVersion: String? = appConfigServerVM.getVersion()
                infoLog("-> sever version", "$serverVersion")

                screenConfig.popUp = serverVersion?.let {
                    if (localVersion == serverVersion)
                        PopUpState.None
                    else PopUpState.Update
                } ?: PopUpState.UnreachableServer

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
                        //check if user in data store exist
                            //if it does check if levelWins are stored in room
                        rankVM.compareLocalAndServerLevelWin()
                            //if it does load the wins from the server for this player
                                //compare them
                                //add the ones from room to server
                    }
                }
            }
            //todo: check if all the wins are well uploaded to the server
                //check if user in data store exist
                    //if it does check if levelWins are stored in room
                        //if it does load the wins from the server for this player
                            //compare them
                                //add the ones from room to server
        infoLog("Main Menu View Model", "init end")
    }
}
