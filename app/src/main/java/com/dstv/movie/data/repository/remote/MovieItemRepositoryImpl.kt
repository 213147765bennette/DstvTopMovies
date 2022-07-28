package com.dstv.movie.data.repository.remote

import com.dstv.movie.data.datasource.remote.movies.MovieItemDataSource
import com.dstv.movie.data.model.MovieAPIResponse
import com.dstv.movie.data.util.Resource
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.dstv.movie.domain.repository.remote.MovieItemsDataRepository
import retrofit2.Response

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
class MovieItemRepositoryImpl(
    private val movieItemDataSource: MovieItemDataSource
) : MovieItemsDataRepository {

    override suspend fun getMovies(): Resource<MovieAPIResponse> {
        return responseToResource(movieItemDataSource.getMovies())
    }

    private fun responseToResource(response:Response<MovieAPIResponse>):Resource<MovieAPIResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}