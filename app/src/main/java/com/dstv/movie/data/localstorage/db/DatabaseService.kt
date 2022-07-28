package com.dstv.movie.data.localstorage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.localstorage.dao.MovieDAO
import com.dstv.movie.data.model.Item

@Database(
    entities = [Item::class, UserFavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseService : RoomDatabase(){
    abstract fun movieDao(): MovieDAO
}