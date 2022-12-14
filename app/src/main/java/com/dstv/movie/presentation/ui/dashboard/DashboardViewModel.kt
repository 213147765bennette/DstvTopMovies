package com.dstv.movie.presentation.ui.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(
    private  val movieItemsLocalDataRepository: MovieItemsLocalDataRepository
) : ViewModel() {

    companion object{
        private var TAG = "DashboardViewModel"
    }


    private lateinit var favouretsItems: List<UserFavouriteMovieEntity>
    var favouriteMovieData = MutableLiveData<List<UserFavouriteMovieEntity>>()
    var isDataFound = MutableLiveData<Boolean>()
    var isFavouriteMovieAvailable = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    init{
        isFavouriteMovieAvailable.value = false
        isDataFound.value = false
        favouriteMovieData = MutableLiveData()

        getFavouritesMovies()
    }



    fun getFavouritesMovies(): LiveData<List<UserFavouriteMovieEntity>> {
        Log.d(TAG,"========GETTING FAVOURITES MOVIES===========")

        viewModelScope.launch {
            favouretsItems =
                withContext(Dispatchers.Default) { movieItemsLocalDataRepository.getAllMovies() }
            Log.d(TAG,"=======Getting data from Local DB ============: ${Gson().toJson(favouretsItems)}")

            if(favouretsItems.isNotEmpty()){
                favouriteMovieData.value = favouretsItems
                Log.d(TAG,"=======Getting data from Local DB ============: ${Gson().toJson(favouriteMovieData.value)}")
                isDataFound.value = true
            }

        }
        return favouriteMovieData
    }

}
class DashboardViewModelFactory(
    private  val movieItemsLocalDataRepository: MovieItemsLocalDataRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(
            movieItemsLocalDataRepository
        ) as T

    }
}