package com.dstv.movie.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dstv.movie.data.model.MovieAPIResponse
import com.dstv.movie.data.util.Resource
import com.dstv.movie.domain.repository.remote.MovieItemsDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
class MoviesViewModel(
    private val app:Application,
    private val movieItemsDataRepository: MovieItemsDataRepository

) : AndroidViewModel(app) {

    val topFiveMovies: MutableLiveData<Resource<MovieAPIResponse>> = MutableLiveData()

    fun getTopFiveMovies() = viewModelScope.launch(Dispatchers.IO) {
        topFiveMovies.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)) {
                val apiResult = movieItemsDataRepository.getMovies()
                topFiveMovies.postValue(apiResult)
            }else{
                topFiveMovies.postValue(Resource.Error("Internet is not available"))
            }

        }catch (e: Exception){
            topFiveMovies.postValue(Resource.Error(e.message.toString()))
        }

    }

    //Here i just want to check if i do have the network
    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

}

class MoviesViewModelFactory(
    private val app:Application,
    private val movieItemsDataRepository: MovieItemsDataRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(
            app,
            movieItemsDataRepository
        ) as T

    }
}
