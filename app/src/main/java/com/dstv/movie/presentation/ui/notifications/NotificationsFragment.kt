package com.dstv.movie.presentation.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.dstv.movie.MainActivity
import com.dstv.movie.R
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.dstv.movie.data.util.Resource
import com.dstv.movie.databinding.FragmentNotificationsBinding
import com.dstv.movie.domain.repository.local.MovieItemsLocalDataRepository
import com.dstv.movie.presentation.adapter.MovieItemAdapter
import com.dstv.movie.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : Fragment(),  MovieItemAdapter.RecycleViewItemClickInterface{

    companion object{
        private var TAG = "HomeFragment"
    }

    private var _binding: FragmentNotificationsBinding? = null
    @Inject
    lateinit var movieItemsLocalDataRepository: MovieItemsLocalDataRepository
    private  lateinit var viewModel: MoviesViewModel
    private lateinit var movieItemAdapter: MovieItemAdapter
    private var isLoading = false
    val isMovieDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val isMovieAdded: MutableLiveData<Boolean> = MutableLiveData()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)
        _binding?.myToolbar?.inflateMenu(R.menu.main)
        _binding?.myToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.see_favourites -> {
                    Navigation.findNavController(view).navigate(R.id.navigation_dashboard);
                    true
                }
                else -> false
            }
        }

        viewModel= (activity as MainActivity).viewModel
        //movieItemAdapter = (activity as MainActivity).moviesAdapter
        movieItemAdapter = MovieItemAdapter(this)

        initRecyclerView()
        viewMoviesList()
    }

    private fun initRecyclerView() {
        _binding?.rvMovies?.apply {
            adapter = movieItemAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        isMovieDeleted.value = false
        isMovieAdded.value = false
    }

    private fun viewMoviesList() {

        viewModel.getTopFiveMovies()
        viewModel.topFiveMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { it ->

                        //sort this by release date
                        val myScndNormalList: List<Item> = it.components[1].items
                        val scndSortedList: List<Item> = myScndNormalList.sortedBy { it.rank }

                        it.components[1].items.sortedByDescending { it.rank }
                        Log.i(TAG, "came here ${it.components.toList().size}")
                        Log.i(TAG, "======came here======== ${it.components[1].items[1].synopsis}")
                        movieItemAdapter.differ.submitList(myScndNormalList)


                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                else -> {
                    Toast.makeText(activity, "Loading...", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showProgressBar(){
        isLoading = true
        _binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        _binding?.progressBar?.visibility = View.INVISIBLE
    }

    private fun showAddDialog(data: Item){

        val dialog = MaterialDialog (requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_add)
            .message(R.string.dialog_add_title)

        dialog.positiveButton(R.string.btn_add) {
            //Add the Movie Item record to RoomDB
            //check if this item exist first before adding
            lifecycleScope.launch {
                val cartList:List<UserFavouriteMovieEntity> = movieItemsLocalDataRepository.getAllMovies()
                if(cartList.isNotEmpty()){
                    for (response in cartList){
                        if (response.id == data.id){
                            Log.d(TAG,"Response ID: ${response.id}: Data ID: ${data.id}")
                            viewModel.deleteMovieItem(data)
                            isMovieDeleted.value = true
                            isMovieAdded.value = false
                            Toast.makeText(context,"Movie Item Deleted from favourite.", Toast.LENGTH_LONG).show()
                            break
                        }else{
                            Log.d(TAG,"New Item TO BE ADDED")
                            viewModel.saveMovieItem(data)
                            isMovieDeleted.value = false
                            isMovieAdded.value = true
                            Toast.makeText(context,"Movie Item added to favourite.", Toast.LENGTH_LONG).show()
                            break
                        }

                    }
                }else{
                    Log.d(TAG,"========LIST IS EMPTY, JUST ADD THIS ITEM")
                    viewModel.saveMovieItem(data)
                    isMovieDeleted.value = false
                    isMovieAdded.value = true
                    Toast.makeText(requireContext(),"Your movie was saved successful", Toast.LENGTH_LONG).show()

                }
            }


        }

        dialog.negativeButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onItemClicked(data: Item, position: Int) {
        when (isMovieAdded.value) {
            true -> {
                viewModel.deleteMovieItem(data)
            }
            false -> {
                showAddDialog(data)
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}