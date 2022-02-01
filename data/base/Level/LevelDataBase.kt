package com.mobilegame.robozzle.data.base.Level

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [LevelData::class], version = 3, exportSchema = false)
abstract class LevelDataBase: RoomDatabase() {
    abstract fun levelDao(): LevelDao
    companion object{
        @Volatile
        private var INSTANCE: LevelDataBase? = null
        fun getInstance(context: Context): LevelDataBase {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LevelDataBase::class.java,
                    "level_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
