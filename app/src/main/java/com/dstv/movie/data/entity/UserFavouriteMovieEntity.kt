package com.dstv.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
@Entity(tableName = "favourites")
data class UserFavouriteMovieEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "releaseDate")
    var releaseDate: Int,
    @ColumnInfo(name = "synopsis")
    var synopsis: String,
    @ColumnInfo(name = "title")
    var title: String
 )