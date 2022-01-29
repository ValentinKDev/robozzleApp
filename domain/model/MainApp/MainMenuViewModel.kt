package com.mobilegame.robozzle.domain.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.Extensions.toRobuzzleLevelList
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.repository.LevelRepository
import com.mobilegame.robozzle.data.base.Level.Level
import com.mobilegame.robozzle.data.base.Level.LevelDataBase
import com.mobilegame.robozzle.data.remote.Level.LevelService
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.MainApp.AppConfigViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.res.ERROR
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
class MainMenuViewModel(application: Application): AndroidViewModel(application) {
//class MainMenuViewModel(): ViewModel() {


//    val dataStore: DataStore<Preferences> by lazy { getApplication<Application>().dataStore }
//    val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "version")
//    val dataStore: DataStore<Preferences> = application.Data

//    val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "version")

    private val _cannotPlayCuzServerDown = MutableStateFlow<Boolean>(false)
    val cannotPlayCuzServerDown: StateFlow<Boolean> = _cannotPlayCuzServerDown

    private val _rbAllLevelsList = MutableLiveData<List<RobuzzleLevel>>(emptyList())
    val rbAllLevelList: MutableLiveData<List<RobuzzleLevel>> = _rbAllLevelsList
    fun SetRbAllLevelList(mList: List<RobuzzleLevel>) {
        infoLog("..Set Rb All Level List()", "list size${mList.size}")
        _rbAllLevelsList.postValue(mList)
    }

    private val _rbAllLevelListFromRoom = MutableLiveData<List<Level>>(emptyList())
    val rbAllLevelListFromRoom : MutableLiveData<List<Level>> = _rbAllLevelListFromRoom


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

//    val datastoreRepo = DataStoreService.createUserService(getApplication())
//    val datastoreVM = DataViewModel(datastoreRepo)

    init {
//        infoLog("test", "main menu viewModel")
//        infoLog("dataStored name", "${datastoreVM.getName()}")
//        datastoreVM.getName()


        Log.e("init", "MainMenuViewModel()")
        val levelDao = LevelDataBase.getInstance(application).levelDao()
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
//
        DeleteDeprecatedData()
//        UpDateLocalVersionFromServer()
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

//    private suspend fun UpDateLocalVersionFromServer() {
//        withContext(Dispatchers.IO) {
//            infoLog("Update Local Version From Server", "server ${_serverLevelsListVersion.value}")
//            infoLog("Update Local Version From Local", "local ${_localLevelsListVersion.value}")
//            _serverLevelsListVersion.value?.let {
//                saveLocalVersionValue(VERSION_KEY, _serverLevelsListVersion.value!!)
//                LoadVersionFromLocal()
//            }
//        }
//    }

    suspend fun AddLevelRequestsAndUpdateVM() {
        val service = LevelService.create()
        var levelRequestsList: List<LevelRequest> = emptyList()

        levelRequestsList = service.getLevels()
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLevelRequests(levelRequestsList)
            SetRbAllLevelList(repository.getAllLevelsFromRoom().toRobuzzleLevelList())
        }
    }

//    suspend fun saveRegisterTokenInMemory(key: String, registerToken: String) = withContext(Dispatchers.IO) {
//        val wrappedKey = stringPreferencesKey(key)
//        getApplication<Application>().dataStore.edit {
//            it[wrappedKey] = registerToken
//        }
//    }
//
//    suspend fun getRegisterTokenInMemory(key: String, default: String = NONE): String = withContext(Dispatchers.IO) {
//        val wrappedKey = stringPreferencesKey(key)
//        val valueFlow: Flow<String> = getApplication<Application>().dataStore.data.map {it[wrappedKey] ?: default}
//        return@withContext valueFlow.first()
//    }


//    private suspend fun LoadLevelListVersions() {
//        infoLog("Load Level List Version ()", "in Scope")
//        todo: remove if double

//        withContext(Dispatchers.Default) {
//            LoadVersionFromServer()
//        }
//        LoadVersionFromLocal()
//        infoLog("Load Level List Version ()", "End")
//    }

//    private suspend fun LoadVersionFromLocal() {
//        val local_data = getLocalVersionValue(VERSION_KEY)
//        withContext(Dispatchers.Main) {
//            Set_localLevelListVersion(local_data)
//        }
//        errorLog("Load Version From Local", "${local_data}")
//        errorLog("_localLevelListVersion", "${_localLevelsListVersion.value}")
//    }

//    private suspend fun LoadVersionFromServer() {
//        val serverVersion = versionService.getVersion()
//        val serverVersion: AppConfig? = appConfigService.getAppConfig()
//        appConfig = appConfigService.getAppConfig()

//        serverVersion?.let { Set_serverLevelsListVersion(serverVersion) }
//        infoLog("Load Version From Server", "${serverVersion}")
//    }

}

class MainMenurViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return MainMenuViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
