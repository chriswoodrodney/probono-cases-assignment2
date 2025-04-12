
package com.chrisy.probonocases.network

object RetrofitInstance {
    val api: LegalReferenceApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(LegalReferenceApi::class.java)
    }
}
