package com.chrisy.probonocases.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LawyerDao {
    @Query("SELECT * FROM lawyers ORDER BY name ASC")
    fun getAllLawyers(): LiveData<List<Lawyer>>

    @Query("SELECT * FROM lawyers WHERE id = :id")
    fun getLawyerById(id: Long): LiveData<Lawyer?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lawyer: Lawyer): Long

    @Update
    suspend fun update(lawyer: Lawyer)

    @Delete
    suspend fun delete(lawyer: Lawyer)
}