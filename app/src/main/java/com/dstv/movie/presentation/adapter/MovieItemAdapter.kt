package com.dstv.movie.presentation.adapter


/**
 * created by {Bennette Molepo} on {10/30/2021}.
 */

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dstv.movie.data.model.Component
import com.dstv.movie.data.model.Item
import com.dstv.movie.databinding.MovieListviewBinding

class MovieItemAdapter:RecyclerView.Adapter<MovieItemAdapter.MoviesViewHolder>() {

    companion object{
        private var TAG = "MovieItemAdapter"
    }

    private val callback = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = MovieListviewBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie, position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MoviesViewHolder(
        val binding:MovieListviewBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(movieItem: Item, position: Int){
            Log.i(TAG,"came here ${movieItem.title}")
            var count: Int = position.plus(1)

            binding.movieTitle.text = count.toString()+". "+movieItem.title
            binding.movieDescription.text = movieItem.synopsis
            binding.movieReleaseDate.text = "Release Date: "+movieItem.releaseDate

            Glide.with(binding.movieImage.context).
            load(movieItem.imageUrl).
            into(binding.movieImage)

        }
    }


}