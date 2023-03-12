package com.example.coroutineshilt.data

import retrofit2.Response
import retrofit2.http.GET

interface CatsApi {
    @GET("/v1/images/search")
    suspend fun getCats(): Response<List<CatResponse>>
}


