package com.mobilegame.robozzle.data.base.ResolvedLevel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [ResolvedLevelData::class], version = 1, exportSchema = false)
//abstract class ResolvedLevelDataBase: RoomDatabase() {
//    abstract fun resolvedLevelDao(): ResolvedLevelDao
//    companion object{
//        @Volatile
//        private var INSTANCE: ResolvedLevelDataBase? = null
//        fun getInstance(context: Context): ResolvedLevelDataBase {
//            var instance = INSTANCE
//            if (instance == null){
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ResolvedLevelDataBase::class.java,
//                    "level_database"
//                ).fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//            }
//            return instance
//        }
//    }
//}