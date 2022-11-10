package com.example.domain.repository

import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean

interface MovieRepository {


    suspend fun getMovies(
        query: String? = "",
        page: Int,
        callForHomePage: Boolean = true,
    ): ClientResult<MovieBean>

}