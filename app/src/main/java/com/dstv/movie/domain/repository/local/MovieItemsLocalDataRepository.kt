package com.dstv.movie.domain.repository.local

import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.dstv.movie.data.model.MovieAPIResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
interface MovieItemsLocalDataRepository {
    fun insertMovieItem(favouriteMovieItem: Item)
    fun deleteMovieItem(favouriteMovieItem: Item): Single<Int>
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<Item>
    fun getMovieItems(): Observable<List<Item>>
    suspend fun updateMovieItem(favouriteMovieEntity: Item)

}