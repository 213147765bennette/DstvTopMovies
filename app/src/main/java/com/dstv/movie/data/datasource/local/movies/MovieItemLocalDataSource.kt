package com.dstv.movie.data.datasource.local.movies

import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.model.Item
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
interface MovieItemLocalDataSource {
    fun insertMovieItem(favouriteMovieEntity: Item)
    fun deleteMovieItem(favouriteMovieEntity: Item): Single<Int>
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<Item>
    fun getMovieItems(): Observable<List<Item>>
    suspend fun updateMovieItem(favouriteMovieEntity: Item)

}