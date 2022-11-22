package com.mobilegame.robozzle.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.User.pref)
val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.Token.pref)
val Context.appPrefDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.App.pref)
val Context.screenDimensionsDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.Screen.pref)
val Context.argumentsDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.Arg.pref)
val Context.lazyListState1: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.LazyListState1.pref)
val Context.lazyListState2: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.LazyListState2.pref)
val Context.lazyListState3: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.LazyListState3.pref)
val Context.lazyListState4: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.LazyListState4.pref)
val Context.lazyListState5: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.LazyListState5.pref)
val Context.tuto: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameProvider.TutoDataState.pref)

interface DataStoreService {
    suspend fun getBoolean(key: String): Boolean?
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun delBoolean(key: String)

    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)
    suspend fun delString(key: String)

    suspend fun getInt(key: String): Int?
    suspend fun putInt(key: String, value: Int)
    suspend fun delInt(key: String)

    suspend fun getFloat(key: String): Float?
    suspend fun putFloat(key: String, value: Float)
    suspend fun delFloat(key: String)

    companion object {
        fun createUserService(context: Context): DataStoreService {
            return DataStoreImplementation(context.userDataStore)
        }

        fun createTokenService(context: Context): DataStoreService {
            return DataStoreImplementation(context.tokenDataStore)
        }

        fun createAppData(context: Context): DataStoreService {
            return DataStoreImplementation(context.appPrefDataStore)
        }

        fun createScreenDataService(context: Context): DataStoreService {
            return DataStoreImplementation(context.screenDimensionsDataStore)
        }

        fun createArgumentsService(context: Context): DataStoreService {
            return DataStoreImplementation(context.argumentsDataStore)
        }

        fun createLazyListStateService(context: Context, id: Int): DataStoreService {
            return when (id) {
                1 -> DataStoreImplementation(context.lazyListState1)
                2 -> DataStoreImplementation(context.lazyListState2)
                3 -> DataStoreImplementation(context.lazyListState3)
                4 -> DataStoreImplementation(context.lazyListState4)
                else -> DataStoreImplementation(context.lazyListState5)
            }
        }

        fun createTutoStateService(context: Context): DataStoreService {
            return DataStoreImplementation(context.tuto)
        }
    }
}