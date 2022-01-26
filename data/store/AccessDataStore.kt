package com.mobilegame.robozzle.data.store

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mobilegame.robozzle.presentation.res.NONE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

//todo: check how to limit the calls of these function / use a repo ?
suspend fun getStringFromDatastore(infoKey: String, dataStore: DataStore<Preferences>, default: String = NONE): String = withContext(
    Dispatchers.IO) {
    val wrappedKey = stringPreferencesKey(infoKey)
    val valueFlow: Flow<String> = dataStore.data.map { it[wrappedKey] ?: default }
    return@withContext valueFlow.first()
}

suspend fun saveStringInDatastore(infoKey: String, value: String, dataStore: DataStore<Preferences>) = withContext(Dispatchers.IO) {
    val wrappedKey = stringPreferencesKey(infoKey)
    dataStore.edit { it[wrappedKey] = value }
}
