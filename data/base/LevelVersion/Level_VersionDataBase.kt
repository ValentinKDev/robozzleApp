package com.mobilegame.robozzle.data.base.LevelVersion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Level_Version::class], version = 1, exportSchema = false)
abstract class Level_VersionDataBase: RoomDatabase() {
    abstract fun levelversionDao(): Level_VersionDao
    companion object{
        @Volatile
        private var INSTANCE: Level_VersionDataBase? = null
        fun getInstance(context: Context): Level_VersionDataBase {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    Level_VersionDataBase::class.java,
                    "level_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
