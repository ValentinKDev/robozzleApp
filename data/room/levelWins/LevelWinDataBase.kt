package com.mobilegame.robozzle.data.room.levelWins

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//import com.mobilegame.robozzle.data.base.ResolvedLevel.ResolvedLevelDao
//import com.mobilegame.robozzle.data.base.ResolvedLevel.ResolvedLevelData

@Database(entities = [LevelWinData::class], version = 7, exportSchema = false)
abstract class LevelWinDataBase: RoomDatabase() {
    abstract fun playerRanksDao(): LevelWinDao
    companion object{
        @Volatile
        private var INSTANCE: LevelWinDataBase? = null
        fun getInstance(context: Context): LevelWinDataBase {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LevelWinDataBase::class.java,
                    "level_win_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
