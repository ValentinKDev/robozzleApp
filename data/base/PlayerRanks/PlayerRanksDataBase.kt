package com.mobilegame.robozzle.data.base.PlayerRanks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//import com.mobilegame.robozzle.data.base.ResolvedLevel.ResolvedLevelDao
//import com.mobilegame.robozzle.data.base.ResolvedLevel.ResolvedLevelData

@Database(entities = [LevelResolvedData::class], version = 1, exportSchema = false)
abstract class PlayerRanksDataBase: RoomDatabase() {
    abstract fun playerRanksDao(): PlayerRanksDao
    companion object{
        @Volatile
        private var INSTANCE: PlayerRanksDataBase? = null
        fun getInstance(context: Context): PlayerRanksDataBase {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerRanksDataBase::class.java,
                    "level_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
