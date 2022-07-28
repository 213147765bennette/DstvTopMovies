package com.dstv.movie.domain.repository.local

import androidx.room.Delete
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
    suspend fun deleteMovie(favouriteMovieItem: Item)
    suspend fun deleteAll():Int
    suspend fun getAllMovies(): List<Item>
    suspend fun updateMovieItem(favouriteMovieEntity: Item)

}