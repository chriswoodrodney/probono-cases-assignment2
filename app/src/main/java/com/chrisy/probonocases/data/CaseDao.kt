package com.chrisy.probonocases.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Query("SELECT * FROM cases WHERE status = :status")
    fun getCasesByStatus(status: String): Flow<List<Case>>

    @Query("SELECT * FROM cases WHERE id = :id")
    fun getCaseById(id: Long): Flow<Case?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCase(caseItem: Case)

    @Update
    suspend fun updateCase(caseItem: Case)

    // If this column exists in a different table like LegalReference, this needs to be removed.
    @Query("SELECT * FROM cases WHERE legalReference LIKE '%' || :query || '%'")
    fun searchLegalReferences(query: String): Flow<List<Case>> // ⚠️ Only if `legalReference` is a column in Case
}
