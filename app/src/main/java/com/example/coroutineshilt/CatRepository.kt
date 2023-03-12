package com.example.coroutineshilt

import com.example.coroutineshilt.data.CatResponse
import com.example.coroutineshilt.data.CatsApi
import com.example.coroutineshilt.util.Resource
import javax.inject.Inject

class CatRepository @Inject constructor(private val api: CatsApi) : MainRepository {

    override suspend fun getCats(): Resource<List<CatResponse>> {
        return try {
            val response = api.getCats()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}