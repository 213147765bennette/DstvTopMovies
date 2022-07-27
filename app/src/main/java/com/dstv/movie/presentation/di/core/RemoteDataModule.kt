package com.dstv.movie.presentation.di.core

import com.dstv.movie.data.api.APIService
import com.dstv.movie.data.datasource.remote.movies.MovieItemDataSource
import com.dstv.movie.data.datasource.remote.movies.MovieItemDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule() {

    /*internal means that the declarations are visible inside a module.
    A module in kotlin is a set of Kotlin files compiled together.*/

    @Provides
    @Singleton
    fun provideMovieRemoteDatSource(apiService: APIService) : MovieItemDataSource {
        return MovieItemDataSourceImpl(apiService)
    }

}