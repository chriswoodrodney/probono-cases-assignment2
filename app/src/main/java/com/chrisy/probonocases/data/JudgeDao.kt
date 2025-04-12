package com.chrisy.probonocases.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JudgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(judge: Judge): Long

    @Update
    suspend fun update(judge: Judge)

    @Delete
    suspend fun delete(judge: Judge)

    @Query("SELECT * FROM judges")
    fun getAllJudges(): Flow<List<Judge>>

    @Query("SELECT * FROM judges WHERE id = :judgeId")
    fun getJudgeById(judgeId: Long): Flow<Judge>
}