package com.example.data.service

import com.example.data.serverModels.ServerMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("https://www.omdbapi.com/")
    suspend fun getMovies(
        @Query("s") searchTerm: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int
    ): Response<ServerMovie>

}