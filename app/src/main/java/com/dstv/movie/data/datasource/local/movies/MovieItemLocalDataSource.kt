package com.dstv.movie.data.datasource.local.movies

import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
interface MovieItemLocalDataSource {
    fun insertMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)
    suspend fun deleteMovie(favouriteMovieItem: UserFavouriteMovieEntity)
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<UserFavouriteMovieEntity>
    suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

}