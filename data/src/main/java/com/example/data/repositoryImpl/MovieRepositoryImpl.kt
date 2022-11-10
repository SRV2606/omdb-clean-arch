package com.example.data.repositoryImpl

import com.example.data.mappers.MoviesMapper
import com.example.data.service.ApiService
import com.example.data.utlils.safeApiCall
import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val mapper: MoviesMapper
) : MovieRepository {

    companion object {
        const val API_KEY = "c0b7f0e7"
    }

    override suspend fun getMovies(
        query: String?,
        page: Int,
        callForHomePage: Boolean
    ): ClientResult<MovieBean> {
        return if (callForHomePage) {
            withContext(Dispatchers.IO) {
                return@withContext mapper.toMovieBean(safeApiCall {
                    service.getMovies("batman", API_KEY, page)
                })
            }
        } else {
            withContext(Dispatchers.IO) {
                return@withContext mapper.toMovieBean(safeApiCall {
                    service.getMovies(query ?: "batman", API_KEY, page)
                })
            }
        }
    }
}