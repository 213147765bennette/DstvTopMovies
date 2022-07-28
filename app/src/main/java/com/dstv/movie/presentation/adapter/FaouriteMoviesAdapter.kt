package com.dstv.movie.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dstv.movie.R
import com.dstv.movie.data.entity.UserFavouriteMovieEntity
import com.dstv.movie.data.model.Item
import com.google.gson.Gson

/**
 * created by {Bennette Molepo} on {10/23/2021}.
 */
class FaouriteMoviesAdapter(var favouritesItems: List<UserFavouriteMovieEntity>):RecyclerView.Adapter<FaouriteMoviesAdapter.FavouriteViewHolder>(){

    companion object{
        private val TAG = "FaouriteMoviesAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favourites_movies_listview,parent,false)
        return FavouriteViewHolder(view)
    }

    //update my recleryview using diffutil
    private val diffUtil=object : DiffUtil.ItemCallback<UserFavouriteMovieEntity>(){
        override fun areItemsTheSame(
            oldItem: UserFavouriteMovieEntity,
            newItem: UserFavouriteMovieEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: UserFavouriteMovieEntity,
            newItem: UserFavouriteMovieEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtil)

    fun setList(favouriteList: List<UserFavouriteMovieEntity>){
        Log.d(TAG,"List Values: ${Gson().toJson(favouriteList)} ")
        asyncListDiffer.submitList(favouriteList)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        Log.d(TAG,"=========== AM BINDING HISTORY DATA TO MY VIEW ===========")
        holder.bind(favouritesItems[position],position)
    }

    //getting the list size here
    override fun getItemCount(): Int = favouritesItems.size

    class FavouriteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        private val txtItemTitle = itemView.findViewById<TextView>(R.id.txt_movie_title)
        private val txtItemDescription = itemView.findViewById<TextView>(R.id.txt_movie_description)

        @SuppressLint("SetTextI18n")
        fun bind(data: UserFavouriteMovieEntity, position: Int){
            Log.d(TAG,"Binding data $data")

            txtItemTitle.text = data.title
            txtItemDescription.text = data.synopsis

        }
    }


}