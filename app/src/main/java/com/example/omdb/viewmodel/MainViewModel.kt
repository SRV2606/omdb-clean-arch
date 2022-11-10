package com.example.omdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean
import com.example.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val moviesUseCase: GetMoviesUseCase) : ViewModel() {


    private val _moviesList: MutableStateFlow<ClientResult<MovieBean>> =
        MutableStateFlow(ClientResult.InProgress)
    val moviesList = _moviesList.asStateFlow()


    fun getInitialMoviesWithPagination(page: Int) {
        viewModelScope.launch {
            _moviesList.emit(ClientResult.InProgress)
            val response = moviesUseCase.getInitialMoviesWithPaginationCalls(page)
            _moviesList.emit(response)
        }
    }

    fun getMoviesFromSearch(query: String, page: Int) {
        viewModelScope.launch {
            _moviesList.emit(ClientResult.InProgress)
            val response = moviesUseCase.getMoviesListFromSearch(query, page)
            _moviesList.emit(response)
        }
    }


}