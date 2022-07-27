package com.dstv.movie.presentation.di.core


import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSource
import com.dstv.movie.data.datasource.remote.movies.MovieItemDataSource
import com.dstv.movie.data.repository.local.MovieItemLocalRepositoryImpl
import com.dstv.movie.data.repository.remote.MovieItemRepositoryImpl
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.dstv.movie.domain.repository.remote.MovieItemsDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /*internal means that the declarations are visible inside a module.
    A module in kotlin is a set of Kotlin files compiled together.*/

    @Singleton
    @Provides
    fun provideMovieItemRepository(
        movieItemDataSource: MovieItemDataSource
    ): MovieItemsDataRepository {
        return MovieItemRepositoryImpl(movieItemDataSource)
    }

    @Singleton
    @Provides
    fun provideMovieItemLocalRepository(
        movieItemLocalDataSource: MovieItemLocalDataSource
    ): MovieItemsLocalDataRepository {
        return MovieItemLocalRepositoryImpl(movieItemLocalDataSource)
    }

}