package com.dstv.movie.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.dstv.movie.MainActivity
import com.dstv.movie.R
import com.dstv.movie.data.model.Item
import com.dstv.movie.data.util.Resource
import com.dstv.movie.databinding.FragmentHomeBinding
import com.dstv.movie.presentation.adapter.MovieItemAdapter
import com.dstv.movie.presentation.viewmodel.MoviesViewModel

class HomeFragment : Fragment(),  MovieItemAdapter.RecycleViewItemClickInterface {

    companion object{
        private var TAG = "HomeFragment"
    }

    private  lateinit var viewModel: MoviesViewModel
    private lateinit var movieItemAdapter: MovieItemAdapter
    private var _binding: FragmentHomeBinding? = null
    private var isLoading = false
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        movieItemAdapter = MovieItemAdapter(this)
        return root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
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

        //calling all my dialogs to be activated
        observers()
    }


    private fun viewMoviesList() {

        viewModel.getTopFiveMovies()
        viewModel.topFiveMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        Log.i(TAG, "came here ${it.components.toList().size}")
                        Log.i(TAG, "======came here======== ${it.components[1].items[1].synopsis}")
                        movieItemAdapter.differ.submitList(it.components[1].items)
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

            }
        }
    }

    //in here observe live data from my model
    private fun observers(){

        viewModel.isDeleted.observe(viewLifecycleOwner, Observer {
            if(it){
                showDeleteDialog()
            }
        })

    }

    private fun showAddDialog(data: Item){

        val dialog = MaterialDialog (requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_add)
            .message(R.string.dialog_add_title)


        dialog.positiveButton(R.string.dialog_add) {
            //Add the Movie Item record to RoomDB
            viewModel.saveMovieItem(data)
            Toast.makeText(requireContext(),"Your data was saved successful", Toast.LENGTH_LONG).show()
            Log.d(TAG, "========= ADDING ==========: ${data.synopsis}")

        }

        dialog.negativeButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    //show the deleted dialog
    private fun showDeleteDialog(){

        val dialog = MaterialDialog(requireContext())
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_deleted_title)
            .message(R.string.dialog_deleted_msg)

        dialog.positiveButton {
            dialog.dismiss()
        }

        dialog.show()

    }


    private fun showProgressBar(){
        isLoading = true
        _binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        _binding?.progressBar?.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(data: Item, position: Int) {
        showAddDialog(data)
    }
}