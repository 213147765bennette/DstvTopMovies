package com.dstv.movie.domain.repository.local

import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
interface MovieItemsLocalDataRepository {
    fun insertMovieItem(favouriteMovieItem: UserFavouriteMovieEntity)
    suspend fun deleteMovie(favouriteMovieItem: UserFavouriteMovieEntity)
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<UserFavouriteMovieEntity>
    suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

}