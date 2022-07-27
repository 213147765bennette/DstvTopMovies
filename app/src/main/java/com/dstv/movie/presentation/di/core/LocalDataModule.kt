package com.dstv.movie.presentation.di.core


import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSource
import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSourceImpl
import com.dstv.movie.data.localstorage.dao.MovieDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideMovieItemLocalDatSource(movieDAO: MovieDAO) : MovieItemLocalDataSource {
        return MovieItemLocalDataSourceImpl(movieDAO)
    }

}