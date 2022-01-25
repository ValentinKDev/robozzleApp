package com.mobilegame.robozzle.data.base.LevelVersion

import androidx.room.*

@Dao
interface Level_VersionDao {
    @Query("SELECT * FROM level_version WHERE id = :id")
    fun getLevelVersion(id: Int): Level_Version?
//    @Query("SELECT level_version WHERE id = :id")


    @Insert
    fun insertAll(vararg level_version: Level_Version)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevelVersion(lvl_version: Level_Version)

    @Delete
    fun delete(version: Level_Version)

    @Update
    fun upDateVersion(vararg lvl_version: Level_Version)

}
