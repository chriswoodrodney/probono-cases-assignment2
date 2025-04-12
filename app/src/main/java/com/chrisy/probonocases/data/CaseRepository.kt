package com.chrisy.probonocases.data

import com.chrisy.probonocases.network.LegalReferenceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CaseRepository(
    private val caseDao: CaseDao,
    private val legalReferenceApi: LegalReferenceApi
) {
    val allCases: Flow<List<Case>> = caseDao.getCasesByStatus("ALL") // Or use a separate `getAllCases()` if needed

    fun getCasesByStatus(status: String): Flow<List<Case>> {
        return caseDao.getCasesByStatus(status)
    }

    fun getCaseById(id: Long): Flow<Case?> {
        return caseDao.getCaseById(id)
    }

    suspend fun insertCase(caseItem: Case) {
        caseDao.insertCase(caseItem)
    }

    suspend fun updateCase(caseItem: Case) {
        caseDao.updateCase(caseItem)
    }

    fun searchLegalReferences(query: String): Flow<List<LegalReference>> = flow {
        try {
            val response = legalReferenceApi.searchReferences(query)
            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: IOException) {
            emit(emptyList())
        } catch (e: HttpException) {
            emit(emptyList())
        }
    }
}
