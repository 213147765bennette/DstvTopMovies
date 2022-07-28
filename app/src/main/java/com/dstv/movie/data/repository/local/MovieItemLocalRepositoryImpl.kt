package com.dstv.movie.data.repository.local

import android.util.Log
import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSource
import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSourceImpl
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
class MovieItemLocalRepositoryImpl(
    private val itemLocalDataSource: MovieItemLocalDataSource
) : MovieItemsLocalDataRepository {

    companion object{
        private var TAG = "MovieItemLocalRepositoryImpl"
    }

    override fun insertMovieItem(favouriteMovieItem: UserFavouriteMovieEntity) {
        itemLocalDataSource.insertMovieItem(favouriteMovieItem)
    }

    override suspend fun deleteMovie(favouriteMovieItem: UserFavouriteMovieEntity) {
        return itemLocalDataSource.deleteMovie(favouriteMovieItem)
    }


    override suspend fun deleteAll() :Int{
        return itemLocalDataSource.deleteAll()
    }

    override suspend fun getAllMovies(): List<UserFavouriteMovieEntity> {
        Log.d(TAG,"GET_ALL_MOVIE_ITEM_SAVED FROM REPO: ***********")
        return itemLocalDataSource.getAllMovies()
    }

    override suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity) {
        return itemLocalDataSource.updateMovieItem(favouriteMovieEntity)
    }

}