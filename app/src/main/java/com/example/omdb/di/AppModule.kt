package com.example.omdb.di

import com.example.data.mappers.MoviesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideMoviesMapper(): MoviesMapper {
        return MoviesMapper()
    }

}