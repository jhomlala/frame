package com.jhomlala.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding
import timber.log.Timber

class MoviesPagedAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(movieDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val movie = getItem(position)
            holder.bind(movie!!)
        }
    }

    override fun onCurrentListChanged(
        previousList: PagedList<Movie>?,
        currentList: PagedList<Movie>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
        Timber.d("on current list changed!")
    }





    companion object{
        val movieDiffCallback = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return oldItem == newItem
            }

        }
    }
}