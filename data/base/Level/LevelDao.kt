package com.mobilegame.robozzle.data.base.Level

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelDao {
//    @Query("SELECT * FROM level_table")
//    fun getAllMutableLive(): MutableLiveData<List<Level>>

    @Query("SELECT * FROM level_table")
    fun getAllLive(): LiveData<List<Level>>

    @Query("SELECT * FROM level_table")
    fun getAllFlow(): Flow<List<Level>>

    @Query("SELECT * FROM level_table")
    fun getAll(): List<Level>

    @Query("SELECT * FROM level_table WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<Level>

    @Query("SELECT * FROM level_table WHERE id = :id")
    fun getALevel(id: Int): Level?

    @Query("SELECT * FROM level_table WHERE difficulty IN (:diff)")
    fun loadAllByDifficutly(diff: IntArray): List<Level>

    @Insert
    fun insertAll(vararg level: Level)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevel(lvl: Level)

    @Query("DELETE FROM level_table")
    fun deleteAll()

    @Delete
    fun delete(level: Level)

    @Update
    fun updateLevels(vararg levels: Level)

}
