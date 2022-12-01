package com.mobilegame.robozzle.data.room.Level

import androidx.room.*
import com.mobilegame.robozzle.domain.model.level.LevelState

@Dao
interface LevelDao {
    @Query("SELECT * FROM level_table")
    fun getAll(): List<LevelData>

    @Query("SELECT id FROM level_table")
    fun getAllIds(): List<Int>

    @Query("SELECT * FROM level_table WHERE difficulty = :diff")
    fun getAllFromDifficulty(diff: Int): List<LevelData>

    @Query("SELECT id FROM level_table WHERE difficulty = :diff")
    fun getAllIdFromDifficulty(diff: Int): List<Int>

    @Query("SELECT name FROM level_table WHERE difficulty = :diff")
    fun getAllNameFromDifficulty(diff: Int): List<String>

    @Query("SELECT map_json FROM level_table WHERE difficulty = :diff")
    fun getAllMapJsonFromDifficulty(diff: Int): List<String>

    @Query("SELECT * FROM level_table WHERE id = :id")
    fun getALevel(id: Int): LevelData?

    @Query("SELECT * FROM level_table WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<LevelData>

    @Query("SELECT * FROM level_table WHERE difficulty IN (:diff)")
    fun loadAllByDifficutly(diff: IntArray): List<LevelData>

    @Insert
    fun insertAll(vararg levelData: LevelData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLevel(lvl: LevelData)

//    @Insert(""onConflict = OnConflictStrategy.REPLACE)
//@Query("SELECT * FROM level_table UPDATE level_table  WHERE id = :id")
@Query("UPDATE level_table SET fun_instruction_json = :newSolutionJson WHERE id = :id")
    suspend fun updateSolution(id: Int, newSolutionJson: String)
//    @Query("UPDATE level_table SET fun_instruction_json = :newSolutionJson WHERE id IN :ids")
//    suspend fun updateSolutions(ids: List<Int>, newSolutionJson: String)
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addLevel(lvl: LevelData)

    @Query("DELETE FROM level_table")
    fun deleteAll()

    @Delete
    fun delete(levelData: LevelData)

    @Update
    fun updateLevels(vararg levelData: LevelData)

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateFunctionInstructions(id: Int, funInt: String)
}
