package com.mobilegame.robozzle.domain.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mobilegame.robozzle.domain.res.PREFERENCES_NAME

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)

    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?

    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

        fun create(context: Context): DataStoreRepository {
            return DataStoreRepositoryImplementation(context)
        }
    }
}