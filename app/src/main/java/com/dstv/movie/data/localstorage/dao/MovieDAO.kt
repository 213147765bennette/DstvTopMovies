package com.dstv.movie.data.localstorage.dao

import androidx.room.*
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

    @Delete
    fun deleteMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity): Single<Int>

    @Query("DELETE FROM favourites")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteMovie(favouriteMovieEntity: UserFavouriteMovieEntity)

    @Query("SELECT * from favourites ORDER BY id DESC")
    fun getAllMovies(): Observable<List<UserFavouriteMovieEntity>>


    @Query("SELECT * FROM favourites")
    suspend fun getMovieItems(): List<UserFavouriteMovieEntity>

    @Update
    suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

}