package com.mobilegame.robozzle.data.base.User

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [User::class], version = 2, exportSchema = false)
//abstract class UserDataBase: RoomDatabase() {
//    abstract fun userDao(): UserDao
//    companion object{
//        @Volatile
//        private var INSTANCE: UserDataBase? = null
//        fun getInstance(context: Context): UserDataBase {
//            var instance = INSTANCE
//            if (instance == null){
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDataBase::class.java,
//                    "user_database"
//                ).fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//            }
//            return instance
//        }
//    }
//}
