package com.example.data.mappers

import com.example.data.serverModels.ServerMovie
import com.example.data.serverModels.ServerSearch
import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean
import com.example.domain.models.SearchBean
import javax.inject.Inject

class MoviesMapper @Inject constructor() {


    fun toMovieBean(serverResponse: ClientResult<ServerMovie>): ClientResult<MovieBean> {
        return serverResponse.let { clientRes ->
            when (clientRes) {
                is ClientResult.Success -> {
                    val result = clientRes.data.let {
                        MovieBean(
                            response = it.response,
                            search = tranformSearch(it.search),
                            totalResults = it.totalResults
                        )
                    }
                    return ClientResult.Success(result)
                }
                is ClientResult.Error -> {
                    return ClientResult.Error(clientRes.error)

                }
                else -> {
                    ClientResult.InProgress
                }
            }
        }
    }

    private fun tranformSearch(search: List<ServerSearch>?): List<SearchBean>? {
        return search?.map {
            SearchBean(
                imdbID = it.imdbID,
                poster = it.poster,
                title = it.title,
                year = it.year,
                type = it.type
            )
        }
    }
}




