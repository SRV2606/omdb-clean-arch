package com.example.domain.usecases

import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {


    suspend fun getInitialMoviesWithPaginationCalls(
        page: Int
    ): ClientResult<MovieBean> {
        return repository.getMovies(page = page)
    }

    suspend fun getMoviesListFromSearch(
        query: String,
        page: Int
    ): ClientResult<MovieBean> {
        return repository.getMovies(page = page, query = query, callForHomePage = false)
    }
}