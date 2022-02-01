package com.mobilegame.robozzle.data.base.Level

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelDao {
    @Query("SELECT * FROM level_table")
    fun getAll(): List<LevelData>

    @Query("SELECT * FROM level_table WHERE id")
    fun getAllIds(): List<Int>

    @Query("SELECT * FROM level_table WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<LevelData>

    @Query("SELECT * FROM level_table WHERE id = :id")
    fun getALevel(id: Int): LevelData?

    @Query("SELECT * FROM level_table WHERE difficulty IN (:diff)")
    fun loadAllByDifficutly(diff: IntArray): List<LevelData>

    @Insert
    fun insertAll(vararg levelData: LevelData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevel(lvl: LevelData)

    @Query("DELETE FROM level_table")
    fun deleteAll()

    @Delete
    fun delete(levelData: LevelData)

    @Update
    fun updateLevels(vararg levelData: LevelData)

}
