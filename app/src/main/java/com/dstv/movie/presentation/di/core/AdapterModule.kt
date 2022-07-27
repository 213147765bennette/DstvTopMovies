package com.dstv.movie.presentation.di.core

import com.dstv.movie.presentation.adapter.MovieItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

   @Singleton
   @Provides
   fun provideMovieItemAdapter():MovieItemAdapter{
       return MovieItemAdapter()
   }
}