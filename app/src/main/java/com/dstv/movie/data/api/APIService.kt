package com.dstv.movie.data.api

import com.dstv.movie.data.model.MovieAPIResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
interface APIService {
    @GET("https://beta-now.dstv.com/top5MoviesAssessement.json")
    suspend fun getMovies(): Response<MovieAPIResponse>
}