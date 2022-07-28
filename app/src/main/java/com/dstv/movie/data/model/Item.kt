package com.dstv.movie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import javax.inject.Inject

@Entity(
    tableName = "movies"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "label")
    val label: String,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: Int,
    @ColumnInfo(name = "synopsis")
    val synopsis: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "valueToOrderBy")
    val valueToOrderBy: String
):Serializable