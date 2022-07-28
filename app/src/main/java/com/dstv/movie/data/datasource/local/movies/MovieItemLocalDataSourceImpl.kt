package com.dstv.movie.data.datasource.local.movies

import android.util.Log
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.localstorage.dao.MovieDAO
import com.dstv.movie.data.model.Item
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

    companion object{
        private var TAG = "MovieItemLocalDataSourceImpl"
    }

    override fun insertMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity) {
        //To run this in a background worker thread am using :Dispatchers.IO
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.insertMovieItem(favouriteMovieEntity)
        }


    }

    override suspend fun deleteMovie(favouriteMovieItem: UserFavouriteMovieEntity) {
        return movieDAO.deleteMovie(favouriteMovieItem)
    }

    override fun deleteMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity): Single<Int> {
        return movieDAO.deleteMovieItem(favouriteMovieEntity)
    }

    override suspend fun deleteAll(): Int {
        return movieDAO.deleteAll()
    }

    override suspend fun getAllMovies(): List<UserFavouriteMovieEntity> {
        Log.d(TAG,"GET_ALL_MOVIE_ITEM_SAVED: ***********")
        return movieDAO.getMovieItems()
    }

    override fun getMovieItems(): Observable<List<UserFavouriteMovieEntity>> {
        return movieDAO.getAllMovies()
    }

    override suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity) {
        //To run this in a background worker thread am using :Dispatchers.IO
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.updateMovieItem(favouriteMovieEntity)
        }
    }
}