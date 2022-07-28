package com.dstv.movie.data.repository.local

import android.util.Log
import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSource
import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSourceImpl
import com.dstv.movie.data.model.Item
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
class MovieItemLocalRepositoryImpl(
    private val itemLocalDataSource: MovieItemLocalDataSource
) : MovieItemsLocalDataRepository {

    companion object{
        private var TAG = "MovieItemLocalRepositoryImpl"
    }

    override fun insertMovieItem(favouriteMovieItem: Item) {
        itemLocalDataSource.insertMovieItem(favouriteMovieItem)
    }

    override suspend fun deleteMovie(favouriteMovieItem: Item) {
        return itemLocalDataSource.deleteMovie(favouriteMovieItem)
    }


    override suspend fun deleteAll() :Int{
        return itemLocalDataSource.deleteAll()
    }

    override suspend fun getAllMovies(): List<Item> {
        Log.d(TAG,"GET_ALL_MOVIE_ITEM_SAVED FROM REPO: ***********")
        return itemLocalDataSource.getAllMovies()
    }

    override suspend fun updateMovieItem(favouriteMovieEntity: Item) {
        return itemLocalDataSource.updateMovieItem(favouriteMovieEntity)
    }

}