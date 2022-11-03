package com.mobilegame.robozzle.data.room.Config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConfigData::class], version = 1, exportSchema = false)
abstract class ConfigDataBase: RoomDatabase() {
    abstract fun configDao(): ConfigDao
    companion object {
        @Volatile
        private var INSTANCE: ConfigDataBase? = null

        fun getInstance(context: Context): ConfigDataBase {
            var tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ConfigDataBase::class.java,
                    "config_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
