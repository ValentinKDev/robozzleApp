package com.mobilegame.robozzle.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.flow.first


//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.User.pref)

//class UserDataStoreImplementation(private val context: Context, private val dataStore: DataStore<Preferences>) : DataStoreService {
class DataStoreImplementation(private val dataStore: DataStore<Preferences>) : DataStoreService {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences -> preferences[preferencesKey] = value }
//        context.dataStore.edit { preferences -> preferences[preferencesKey] = value }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
//        context.dataStore.edit { preferences ->
//            preferences[preferencesKey] = value
//        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
//            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = dataStore.data.first()
//            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}