package com.chrisy.probonocases.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CourtApiService {
    @GET("legal-references")
    suspend fun searchLegalReferences(
        @Query("query") query: String
    ): Response<List<LegalReference>>
}