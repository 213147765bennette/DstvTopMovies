package com.dstv.movie.data.localstorage.dao

import androidx.room.*
import com.dstv.movie.data.entity.FavouriteMovieEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieItem(favouriteMovieEntity: FavouriteMovieEntity)

    @Delete
    fun deleteMovieItem(favouriteMovieEntity: FavouriteMovieEntity): Single<Int>

    @Query("DELETE FROM movie")
    suspend fun deleteAll(): Int

    @Query("SELECT * from movie ORDER BY id DESC")
    fun getAllMovies(): Observable<List<FavouriteMovieEntity>>

    @Query("SELECT * FROM movie")
    suspend fun getMovieItems(): List<FavouriteMovieEntity>

    @Update
    suspend fun updateMovieItem(favouriteMovieEntity: FavouriteMovieEntity)

}