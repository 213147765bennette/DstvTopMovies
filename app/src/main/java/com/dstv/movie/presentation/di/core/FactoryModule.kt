package com.dstv.movie.presentation.di.core

import android.app.Application
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.dstv.movie.domain.repository.remote.MovieItemsDataRepository
import com.dstv.movie.presentation.ui.dashboard.DashboardViewModelFactory
import com.dstv.movie.presentation.viewmodel.MoviesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
  fun provideMoviesViewModelFactory(
     application: Application,
     movieItemsDataRepository: MovieItemsDataRepository,
     movieItemsLocalDataRepository: MovieItemsLocalDataRepository
    ):MoviesViewModelFactory{
      return MoviesViewModelFactory(
          application,
          movieItemsDataRepository,
          movieItemsLocalDataRepository
      )
  }


    @Singleton
    @Provides
    fun provideDashboardViewModelFactory(
        movieItemsLocalDataRepository: MovieItemsLocalDataRepository
    ):DashboardViewModelFactory{
        return DashboardViewModelFactory(
            movieItemsLocalDataRepository
        )
    }



}








