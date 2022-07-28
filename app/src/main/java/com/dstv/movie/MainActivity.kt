package com.dstv.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dstv.movie.databinding.ActivityMainBinding
import com.dstv.movie.presentation.adapter.MovieItemAdapter
import com.dstv.movie.presentation.ui.dashboard.DashboardFragment
import com.dstv.movie.presentation.ui.home.HomeFragment
import com.dstv.movie.presentation.viewmodel.MoviesViewModel
import com.dstv.movie.presentation.viewmodel.MoviesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var factory: MoviesViewModelFactory
    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(
            navController
        )
        viewModel = ViewModelProvider(this,factory)[MoviesViewModel::class.java]


    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}