package com.dstv.movie.data.datasource.local.movies

import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.localstorage.dao.MovieDAO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
class MovieItemLocalDataSourceImpl(
    private val movieDAO: MovieDAO
    ): MovieItemLocalDataSource {

    override fun insertMovieItem(favouriteMovieEntity: FavouriteMovieEntity) {
        //To run this in a background worker thread am using :Dispatchers.IO
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.insertMovieItem(favouriteMovieEntity)
        }
    }

    override fun deleteMovieItem(favouriteMovieEntity: FavouriteMovieEntity): Single<Int> {
        return movieDAO.deleteMovieItem(favouriteMovieEntity)
    }

    override suspend fun deleteAll(): Int {
        return movieDAO.deleteAll()
    }

    override suspend fun getAllMovies(): List<FavouriteMovieEntity> {
        return movieDAO.getMovieItems()
    }

    override fun getMovieItems(): Observable<List<FavouriteMovieEntity>> {
        return movieDAO.getAllMovies()
    }

    override suspend fun updateMovieItem(favouriteMovieEntity: FavouriteMovieEntity) {
        //To run this in a background worker thread am using :Dispatchers.IO
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.updateMovieItem(favouriteMovieEntity)
        }
    }
}