package com.example.omdb.di

import com.example.data.repositoryImpl.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindMoviesRepo(moviesRepositoryImpl: MovieRepositoryImpl): MovieRepository
}