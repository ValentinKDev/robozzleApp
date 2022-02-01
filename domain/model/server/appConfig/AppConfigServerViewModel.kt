package com.mobilegame.robozzle.domain.model.server.appConfig

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.server.AppConfig.AppConfigService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AppConfigServerViewModel(
): ViewModel() {
    val service = AppConfigService.create()

    fun getVersion(): String? = runBlocking(Dispatchers.IO) {
        service.getAppConfig()?.version
    }

    fun getUpdateBoolean(): Boolean = runBlocking(Dispatchers.IO) {
        service.getAppConfig()?.updateAvailable ?: false
    }
}