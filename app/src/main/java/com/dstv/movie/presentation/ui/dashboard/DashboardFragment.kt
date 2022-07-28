package com.dstv.movie.presentation.ui.dashboard

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dstv.movie.R
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.dstv.movie.databinding.FragmentDashboardBinding
import com.dstv.movie.presentation.adapter.FaouriteMoviesAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    companion object{
        private var TAG = "DashboardFragment"
    }

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: DashboardViewModelFactory
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var itemAdapter: FaouriteMoviesAdapter
    private lateinit var itemList: List<UserFavouriteMovieEntity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dashboardViewModel =
            ViewModelProvider(this, factory)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        _binding!!.myToolbar.setNavigationOnClickListener { view ->
            Navigation.findNavController(
                view
            ).navigate(R.id.navigation_home);
        }
        init()

    }

    fun init() {

        itemList = ArrayList()
        itemAdapter = FaouriteMoviesAdapter(itemList)

        _binding?.favouritesRecyclerView?.adapter = itemAdapter

        dashboardViewModel.getFavouritesMovies().observe(viewLifecycleOwner) {
            if (it == null || it.equals("null") || it.equals("")) {
                Toast.makeText(requireContext(), "You have no added favourites movies.", Toast.LENGTH_LONG).show()
            } else {
                Log.d( TAG,"========= FAVOURITES DATA: ${Gson().toJson(it)}")
                itemAdapter = FaouriteMoviesAdapter(it)
                itemAdapter.setList(it)

                //inflating the adapter
                _binding?.favouritesRecyclerView?.apply {

                    layoutManager = LinearLayoutManager(activity)

                    adapter = itemAdapter

                }

                //here making sure that the found record is displayed at the very first top
                _binding?.favouritesRecyclerView?.post {
                    _binding?.favouritesRecyclerView?.scrollToPosition(0)

                }
            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}