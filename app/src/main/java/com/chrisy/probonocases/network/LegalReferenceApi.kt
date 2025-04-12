package com.chrisy.probonocases.network

import com.chrisy.probonocases.data.LegalReference
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LegalReferenceApi {
    @GET("legal/references") // Replace with the actual endpoint
    suspend fun searchReferences(
        @Query("query") query: String
    ): Response<List<LegalReference>>
}
