package com.dstv.movie.data.localstorage.dao

import androidx.room.*
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

    @Query("DELETE FROM favourites")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteMovie(favouriteMovieEntity: UserFavouriteMovieEntity)

    @Query("SELECT * FROM favourites")
    suspend fun getMovieItems(): List<UserFavouriteMovieEntity>

    @Update
    suspend fun updateMovieItem(favouriteMovieEntity: UserFavouriteMovieEntity)

}