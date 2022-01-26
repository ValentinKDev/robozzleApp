package com.mobilegame.robozzle.domain.model.MainApp

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.remote.AppConfig.AppConfigService
import com.mobilegame.robozzle.domain.model.MainMenuViewModel
import com.mobilegame.robozzle.domain.res.ERROR
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.domain.res.UNKNOWN
import com.mobilegame.robozzle.presentation.res.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@InternalCoroutinesApi
class AppConfigViewModel (application: Application): AndroidViewModel(application) {
//class AppConfigViewModel (val application: Application): ViewModel {

    private val _versionLocal = MutableStateFlow<String?>(null)
    val versionLocal: StateFlow<String?> = _versionLocal
    private fun SetVersionLocal(str: String) {_versionLocal.value = str}

    private val _versionServer = MutableStateFlow<String?>(null)
    val versionServer: StateFlow<String?> = _versionServer
    private fun SetVersionServer(str: String) {_versionServer.value = str}

    private val _versionDeprecated = MutableStateFlow<Int>(UNKNOWN)
    val versionDeprecated: StateFlow<Int> = _versionDeprecated
    private fun SetVersionDepracted(isDeprecated: Int) {_versionDeprecated.value = isDeprecated}

    private val _appUpdateAvailable = MutableStateFlow<Boolean>(false)
    val appUpdateAvailable: StateFlow<Boolean> = _appUpdateAvailable
    private fun SetAppUpdateAvailable(update: Boolean) {_appUpdateAvailable.value = update}


    val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "version")

    init {
        errorLog("init", "AppConfigViewModel")

        viewModelScope.launch {
            //get update value from server(have to be listened as a stateobserver thing)
//        LoadAppConfigServer()
            //get version server
            LoadVersionFromLocal()
            LoadAppConfigServer()
            CompareLocalServerVersions()

            //get version room
            //compare version server and room
            //then upload in room levelList
        }
    }


    private suspend fun LoadVersionFromLocal() {
        infoLog(".. Load Version From Local ()", "start")
        val local_data = getLocalVersionValue(VERSION_KEY)
        infoLog("local_data", "${local_data}")
        withContext(Dispatchers.Main) { SetVersionLocal(local_data) }
        infoLog("versionLocal", "${versionLocal.value}")
    }

    suspend fun LoadAppConfigServer() {
        infoLog(".. Load App Config From Server ()", "start")
        val appConfigService = AppConfigService.create()
        val appConfig = appConfigService.getAppConfig()
        infoLog("appConfig", "${appConfig}")

        appConfig?.let {
            SetAppUpdateAvailable(appConfig.updateAvailable)
            SetVersionServer(appConfig.version)
        }
    }

    private suspend fun CompareLocalServerVersions() {
        infoLog(".. Compare Local Server Versions ()", "start")
        infoLog(".. local verion", "${versionLocal.value}")
        infoLog(".. server verion", "${versionServer.value}")
        when (versionServer.value) {
            null -> SetVersionDepracted(ERROR)
            else -> when {
                isSameVersion() -> SetVersionDepracted(FALSE)
                isVersionDeprecated() -> {
                    SetVersionDepracted(TRUE)
                    //todo: update local version only if the levels are correctly load to room
                    UpDateLocalVersion()
                }
                else -> errorLog("Some weird shit", "is happening here CompareLocalServerVersions()")
            }
        }
        infoLog(".. Deprecated", "${versionDeprecated.value}")
    }


    private suspend fun UpDateLocalVersion() {
        infoLog(".. Update Local Version ()", "start")
        withContext(Dispatchers.IO) {
            _versionServer.value?.let {
                saveLocalVersionValue(VERSION_KEY, _versionServer.value!!)
                LoadVersionFromLocal()
            }
        }
    }

    //todo : get from datastore ? ??? wtf room or not room
    suspend fun getLocalVersionValue(key: String, default: String = NONE): String = withContext(Dispatchers.IO) {
        val wrappedKey = stringPreferencesKey(key)
        val valueFlow: Flow<String> = getApplication<Application>().dataStore.data.map { it[wrappedKey] ?: default }
        return@withContext valueFlow.first()
    }

    suspend fun saveLocalVersionValue(key: String, value: String) = withContext(Dispatchers.IO) {
        val wrappedKey = stringPreferencesKey(key)
        getApplication<Application>().dataStore.edit {
            it[wrappedKey] = value
        }
    }

    private fun isVersionDeprecated(): Boolean = _versionLocal.value.equals(NONE) || !_versionLocal.value.equals(_versionServer.value)

    private fun isSameVersion(): Boolean = _versionLocal.value.equals(_versionServer.value)
}

class AppConfigFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return AppConfigViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
