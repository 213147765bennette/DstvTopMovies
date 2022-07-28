package com.dstv.movie.presentation.di.core

import android.app.Application
import androidx.room.Room
import com.dstv.movie.data.localstorage.dao.MovieDAO
import com.dstv.movie.data.localstorage.db.DatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    /*internal means that the declarations are visible inside a module.
     A module in kotlin is a set of Kotlin files compiled together.*/

    @Singleton
    @Provides
    fun provideMovieDatabase(app: Application): DatabaseService {
        return Room.databaseBuilder(app, DatabaseService::class.java,"movies_db")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Singleton
    @Provides
    fun provideMovieDao(databaseService: DatabaseService): MovieDAO {
        return databaseService.movieDao()
    }


}