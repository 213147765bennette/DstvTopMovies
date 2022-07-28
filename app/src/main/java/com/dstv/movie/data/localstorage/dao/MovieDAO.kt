package com.dstv.movie.data.localstorage.dao

import androidx.room.*
import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.model.Item
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieItem(favouriteMovieEntity: Item)

    @Delete
    fun deleteMovieItem(favouriteMovieEntity: Item): Single<Int>

    @Query("DELETE FROM movies")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteMovie(favouriteMovieEntity: Item)

    @Query("SELECT * from movies ORDER BY id DESC")
    fun getAllMovies(): Observable<List<Item>>


    @Query("SELECT * FROM movies")
    suspend fun getMovieItems(): List<Item>

    @Update
    suspend fun updateMovieItem(favouriteMovieEntity: Item)

}