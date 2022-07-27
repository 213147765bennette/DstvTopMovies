package com.dstv.movie.data.repository.local

import com.dstv.movie.data.datasource.local.movies.MovieItemLocalDataSource
import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
class MovieItemLocalRepositoryImpl(
    private val itemLocalDataSource: MovieItemLocalDataSource
) : MovieItemsLocalDataRepository {
    override  fun insertMovieItem(favouriteMovieEntity: FavouriteMovieEntity) {
        itemLocalDataSource.insertMovieItem(favouriteMovieEntity)
    }

    override fun deleteMovieItem(favouriteMovieEntity: FavouriteMovieEntity) :Single<Int>{
        return itemLocalDataSource.deleteMovieItem(favouriteMovieEntity)
    }

    override suspend fun deleteAll() :Int{
        return itemLocalDataSource.deleteAll()
    }

    override suspend fun getAllMovies(): List<FavouriteMovieEntity> {
        return itemLocalDataSource.getAllMovies()
    }

    override fun getMovieItems(): Observable<List<FavouriteMovieEntity>> {
        return itemLocalDataSource.getMovieItems()
    }

    override suspend fun updateMovieItem(favouriteMovieEntity: FavouriteMovieEntity) {
        return itemLocalDataSource.updateMovieItem(favouriteMovieEntity)
    }

}