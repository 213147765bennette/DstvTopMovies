package com.dstv.movie.data.model

data class Item(
    val id: Int,
    val imageUrl: String,
    val label: String,
    val rank: Int,
    val releaseDate: Int,
    val synopsis: String,
    val title: String,
    val type: String,
    val valueToOrderBy: String
)