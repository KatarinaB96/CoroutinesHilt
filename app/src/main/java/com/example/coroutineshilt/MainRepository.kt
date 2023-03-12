package com.example.coroutineshilt


import com.example.coroutineshilt.data.CatResponse
import com.example.coroutineshilt.util.Resource

interface MainRepository {

    suspend fun getCats(): Resource<List<CatResponse>>

}