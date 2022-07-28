package com.dstv.movie.data.datasource.remote.movies

import com.dstv.movie.data.model.MovieAPIResponse
import retrofit2.Response

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
interface MovieItemDataSource {
    suspend fun getMovies(): Response<MovieAPIResponse>
}