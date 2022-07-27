package com.dstv.movie.data.datasource.remote.movies

import com.dstv.movie.data.api.APIService
import com.dstv.movie.data.entity.FavouriteMovieEntity
import com.dstv.movie.data.localstorage.dao.MovieDAO
import com.dstv.movie.data.model.MovieAPIResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by Bennette Molepo on 04/06/2022.
 */
class MovieItemDataSourceImpl(
    private val apiService: APIService
    ): MovieItemDataSource {
    override suspend fun getMovies(): Response<MovieAPIResponse> {
        return apiService.getMovies()
    }


}