package com.jhomlala.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding
import com.jhomlala.search.databinding.ItemMovieFooterBinding
import com.jhomlala.search.model.MovieItemType
import com.jhomlala.search.model.State

class MoviesPagedAdapter(private val viewModelCreator: (Int) -> ViewModel) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(movieDiffCallback) {
    private var state = State.LOADING

    companion object {
        val movieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MovieItemType.DATA) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(
                itemBinding,
                viewModelCreator.invoke(MovieItemType.DATA) as MovieViewModel
            )
        } else {

            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemMovieFooterBinding.inflate(layoutInflater, parent, false)
            return MovieFooterViewHolder(
                itemBinding,
                viewModelCreator.invoke(MovieItemType.FOOTER) as MovieFooterViewModel
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val movie = getItem(position)
            if (movie != null) {
                holder.bind(movie)
            }
        }
        if (holder is MovieFooterViewHolder) {
            holder.bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) MovieItemType.DATA else MovieItemType.FOOTER
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}