package com.dstv.movie.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.dstv.movie.data.model.MovieAPIResponse
import com.dstv.movie.data.util.Resource
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.dstv.movie.domain.repository.remote.MovieItemsDataRepository
import com.dstv.movie.presentation.ui.dashboard.DashboardViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Bennette Molepo on 27/07/2022.
 */
class MoviesViewModel(
    private val app:Application,
    private val movieItemsDataRepository: MovieItemsDataRepository,
    private  val movieItemsLocalDataRepository: MovieItemsLocalDataRepository

) : AndroidViewModel(app) {

    companion object{
        private var TAG = "MoviesViewModel"
    }

    val topFiveMovies: MutableLiveData<Resource<MovieAPIResponse>> = MutableLiveData()
    val addMovieItem: MutableLiveData<Resource<MovieAPIResponse>> = MutableLiveData()

    //using compositeDisposable so that i avoid multiple threads making may calls while others are still active
    private val compositeDisposable = CompositeDisposable()

    //Helpers for removing movie items
    private  var movieItemEntity: Item? = null
    var loading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private val id : MutableLiveData<Int> = MutableLiveData()
    private val imageUrl : MutableLiveData<String> = MutableLiveData()
    private val label : MutableLiveData<String> = MutableLiveData()
    private val rank : MutableLiveData<Int> = MutableLiveData()
    private val releaseDate : MutableLiveData<Int> = MutableLiveData()
    private val synopsis : MutableLiveData<String> = MutableLiveData()
    private val title : MutableLiveData<String> = MutableLiveData()
    private val type : MutableLiveData<String> = MutableLiveData()
    private val valueToOrderBy : MutableLiveData<String> = MutableLiveData()

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

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    //local Storage
    fun saveMovieItem(movieItem: Item) = viewModelScope.launch {
        Log.d(TAG,"MOVIE_ITEM_SAVED: ${movieItem.title}")
        val favouriteEntity = UserFavouriteMovieEntity(0,0,"","",)
        favouriteEntity.id = movieItem.id
        favouriteEntity.releaseDate = movieItem.releaseDate
        favouriteEntity.synopsis = movieItem.synopsis
        favouriteEntity.title = movieItem.title

        Log.d(TAG,"MOVIE_ENTITY: ${favouriteEntity.title}")
        movieItemsLocalDataRepository.insertMovieItem(favouriteEntity)
    }


    fun deleteMovieItem(movieItem: Item) = viewModelScope.launch {
        val favouriteEntity = UserFavouriteMovieEntity(0,0,"","",)
        favouriteEntity.id = movieItem.id
        favouriteEntity.releaseDate = movieItem.releaseDate
        favouriteEntity.synopsis = movieItem.synopsis
        favouriteEntity.title = movieItem.title

        movieItemsLocalDataRepository.deleteMovie(favouriteEntity)
    }

    //this is when i want to use Reactive java
   /* fun delete(){
        //call the progressbar first
        isLoading.value = true

        compositeDisposable.add(
            movieItemsLocalDataRepository.deleteMovieItem(createDeleteMovieItemEntity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG,"Delete is called: $it")
                        //kill progressbar here
                        isLoading.value = false

                        //here call the success respond of delete
                        isDeleted.value = true
                    },
                    {
                        Log.e(TAG,"onError is called: $it")
                        isDeleted.value = false

                        //here show the error message
                        onError(it.message.toString())

                    }
                )
        )
    }*/
    /* private fun createDeleteMovieItemEntity(): Item{
          return Item(
              id = id.value!!,
              imageUrl = imageUrl.value.toString(),
              label =  label.value.toString(),
              rank = rank.value!!,
              releaseDate = releaseDate.value!!,
              synopsis = synopsis.value!!,
              title = title.value!!,
              type = type.value!!,
              valueToOrderBy = valueToOrderBy.value!!
          )
      }*/


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

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}

class MoviesViewModelFactory(
    private val app:Application,
    private val movieItemsDataRepository: MovieItemsDataRepository,
    private  val movieItemsLocalDataRepository: MovieItemsLocalDataRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(
            app,
            movieItemsDataRepository,
            movieItemsLocalDataRepository
        ) as T

    }
}
