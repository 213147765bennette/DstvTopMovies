package com.dstv.movie.data.localstorage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.localstorage.dao.MovieDAO

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseService : RoomDatabase(){
    abstract fun movieDao(): MovieDAO
}