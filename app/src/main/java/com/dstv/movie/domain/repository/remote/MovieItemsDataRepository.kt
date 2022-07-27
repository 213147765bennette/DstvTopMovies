package com.dstv.movie.domain.repository.remote

import com.dstv.movie.data.model.MovieAPIResponse
import com.dstv.movie.data.util.Resource

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
interface MovieItemsDataRepository {
    suspend fun getMovies(): Resource<MovieAPIResponse>
}