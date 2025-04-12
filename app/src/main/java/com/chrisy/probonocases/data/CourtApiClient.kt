package com.chrisy.probonocases.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//@Singleton
class CourtApiClient(
    private val apiService: CourtApiService,
    private val connectivityManager: NetworkConnectivityManager
) {
    suspend fun searchLegalReferences(query: String): NetworkResult<List<LegalReference>> {
        return withContext(Dispatchers.IO) {
            if (!connectivityManager.isNetworkAvailable()) {
                return@withContext NetworkResult.Error("No internet connection")
            }

            try {
                val response = apiService.searchLegalReferences(query)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body() ?: emptyList())
                } else {
                    NetworkResult.Error("API error: ${response.code()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error("Network error: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    fun getCourtInfo(caseId: Int) {
        // TODO: implement
    }
}
