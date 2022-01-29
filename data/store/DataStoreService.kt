package com.mobilegame.robozzle.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.User.pref)
val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.Token.pref)

interface DataStoreService {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    suspend fun putInt(key: String, value: Int)

    companion object {
        fun createUserService(context: Context): DataStoreService {
            return DataStoreImplementation(context.userDataStore)
        }

        fun createTokenService(context: Context): DataStoreService {
            return DataStoreImplementation(context.tokenDataStore)
        }
    }
}