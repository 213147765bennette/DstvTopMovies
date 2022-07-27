package com.dstv.movie.data.datasource.local.movies

import com.dstv.movie.data.entity.FavouriteMovieEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
interface MovieItemLocalDataSource {
    fun insertMovieItem(favouriteMovieEntity: FavouriteMovieEntity)
    fun deleteMovieItem(favouriteMovieEntity: FavouriteMovieEntity): Single<Int>
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<FavouriteMovieEntity>
    fun getMovieItems(): Observable<List<FavouriteMovieEntity>>
    suspend fun updateMovieItem(favouriteMovieEntity: FavouriteMovieEntity)

}