package com.mobilegame.robozzle.data.base.ResolvedLevel

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ResolvedLevelDao {
    @Query("SELECT * FROM resolved_level_table")
    fun getAll(): List<ResolvedLevelData>

    @Query("SELECT * FROM resolved_level_table WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<ResolvedLevelData>

    @Query("SELECT * FROM resolved_level_table WHERE id = :id")
    fun getResolvedLevel(id: Int): ResolvedLevelData?

    @Query("SELECT * FROM resolved_level_table WHERE difficulty IN (:diff)")
    fun loadAllByDifficutly(diff: IntArray): List<ResolvedLevelData>

    @Insert
    fun insertAll(vararg resoledLevel: ResolvedLevelData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResolvedLevel(lvl: ResolvedLevelData)

    @Delete
    fun delete(level: ResolvedLevelData)

    @Update
    fun updateLevels(vararg levels: ResolvedLevelData)

}
