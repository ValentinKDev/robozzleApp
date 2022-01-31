package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.repository.LevelRepository
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.Level.LevelDao
import com.mobilegame.robozzle.data.base.Level.LevelDataBase
import com.mobilegame.robozzle.data.remote.Level.LevelService
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.MainApp.AppConfigViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.ERROR
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
class MainMenuViewModel(application: Application): AndroidViewModel(application) {
    val userDataViewModel = UserDataStoreViewModel(
    service = DataStoreService.createUserService(getApplication())
    )

    private val _cannotPlayCuzServerDown = MutableStateFlow<Boolean>(false)
    val cannotPlayCuzServerDown: StateFlow<Boolean> = _cannotPlayCuzServerDown

    private val _rbAllLevelsList = MutableLiveData<List<RobuzzleLevel>>(emptyList())
    val rbAllLevelList: MutableLiveData<List<RobuzzleLevel>> = _rbAllLevelsList
    fun SetRbAllLevelList(mList: List<RobuzzleLevel>) {
        infoLog("..Set Rb All Level List()", "list size${mList.size}")
        _rbAllLevelsList.postValue(mList)
    }

    private val _rbAllLevelListFromRoom = MutableLiveData<List<LevelData>>(emptyList())
    val rbAllLevelDataListFromRoom : MutableLiveData<List<LevelData>> = _rbAllLevelListFromRoom


    private val repository: LevelRepository

    val _localLevelsListVersion = MutableLiveData<String?>(null)
    private fun Set_localLevelListVersion(version: String) {
        verbalLog("set", "$version")
        _localLevelsListVersion.value = version
        verbalLog("isSet", "${_localLevelsListVersion.value}")
    }

    private val _serverLevelsListVersion = MutableLiveData<String?>(null)
    fun Set_serverLevelsListVersion(version: String) {
        _serverLevelsListVersion.postValue(version)
    }


    var appConfigVM: AppConfigViewModel = AppConfigViewModel(application)

    init {
        Log.e("init", "MainMenuViewModel()")
        val levelDao: LevelDao = LevelDataBase.getInstance(application).levelDao()
        repository = LevelRepository(levelDao)

        viewModelScope.launch {

            while (versionDeprecated() == UNKNOWN) {
                delay(200)
                infoLog("delay",".")
                if (CannotReachServerVerion()) break
            }

            when (versionDeprecated()) {
                com.mobilegame.robozzle.domain.res.TRUE -> { errorLog("", "TRUE")
                    HandleDeprecatedVersion()
                }
                com.mobilegame.robozzle.domain.res.FALSE -> { errorLog("", "FALSE")
                }
                ERROR -> { errorLog("", "ERROR")
                    _cannotPlayCuzServerDown.value = true
                }
                else -> {errorLog("some weird shit happening", " in MainMenuViewModel()")
                    _cannotPlayCuzServerDown.value = true
                }
            }
            LoadRbLevels()
        }
        infoLog("xx Main Menu View Model", "init end")
    }

    private fun CannotReachServerVerion(): Boolean = appConfigVM.versionDeprecated.value == ERROR

    private fun versionDeprecated(): Int = appConfigVM.versionDeprecated.value

    private suspend fun HandleDeprecatedVersion() {
//            todo : if not equal check which are the Levels to add so the past one which does save half solution and winsolutions are not ereased
        DeleteDeprecatedData()
        AddLevelRequestsAndUpdateVM()
    }

    private fun DeleteDeprecatedData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delAll()
        }
    }

    suspend fun LoadRbLevels() {
        infoLog(".. Load Robuzzle Levels ()", "start")
        withContext(Dispatchers.IO){
            SetRbAllLevelList(repository.getAllLevelsFromRoom().toRobuzzleLevelList())
        }
    }

    suspend fun AddLevelRequestsAndUpdateVM() {
        val service = LevelService.create()
        var levelRequestsList: List<LevelRequest> = emptyList()

        levelRequestsList = service.getLevels()
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLevelRequests(levelRequestsList)
            SetRbAllLevelList(repository.getAllLevelsFromRoom().toRobuzzleLevelList())
        }
    }
}
