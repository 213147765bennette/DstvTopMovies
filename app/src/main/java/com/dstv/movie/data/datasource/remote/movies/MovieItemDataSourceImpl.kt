package com.dstv.movie.data.datasource.remote.movies

import com.dstv.movie.data.api.APIService
import com.dstv.movie.data.model.MovieAPIResponse
import retrofit2.Response

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
class MovieItemDataSourceImpl(
    private val apiService: APIService
    ): MovieItemDataSource {
    override suspend fun getMovies(): Response<MovieAPIResponse> {
        return apiService.getMovies()
    }


}