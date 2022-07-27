package com.dstv.movie.domain.repository.local

import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.model.MovieAPIResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
interface MovieItemsLocalDataRepository {
    fun insertMovieItem(favouriteMovieEntity: FavouriteMovieEntity)
    fun deleteMovieItem(favouriteMovieEntity: FavouriteMovieEntity): Single<Int>
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<FavouriteMovieEntity>
    fun getMovieItems(): Observable<List<FavouriteMovieEntity>>
    suspend fun updateMovieItem(favouriteMovieEntity: FavouriteMovieEntity)

}