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

    @Query("DELETE FROM movie")
    suspend fun deleteAll(): Int

    @Query("SELECT * from movie ORDER BY id DESC")
    fun getAllMovies(): Observable<List<Item>>

    @Query("SELECT * FROM movie")
    suspend fun getMovieItems(): List<Item>

    @Update
    suspend fun updateMovieItem(favouriteMovieEntity: Item)

}