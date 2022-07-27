package com.dstv.movie.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dstv.movie.MainActivity
import com.dstv.movie.R
import com.dstv.movie.data.util.Resource
import com.dstv.movie.databinding.FragmentHomeBinding
import com.dstv.movie.presentation.adapter.MovieItemAdapter
import com.dstv.movie.presentation.viewmodel.MoviesViewModel

class HomeFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        viewModel= (activity as MainActivity).viewModel
        movieItemAdapter = (activity as MainActivity).moviesAdapter

        initRecyclerView()
        viewMoviesList()
    }

    private fun initRecyclerView() {
        _binding?.rvMovies?.apply {
            adapter = movieItemAdapter
            layoutManager = LinearLayoutManager(activity)
        }

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
}