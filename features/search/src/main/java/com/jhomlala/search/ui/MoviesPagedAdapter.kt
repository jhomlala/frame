package com.jhomlala.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding
import com.jhomlala.search.databinding.ItemMovieFooterBinding
import timber.log.Timber

class MoviesPagedAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(movieDiffCallback){
    private var state = State.LOADING
    companion object{

        const val DATA_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYPE = 1

        val movieDiffCallback = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DATA_VIEW_TYPE) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(itemBinding)
        } else {

            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemMovieFooterBinding.inflate(layoutInflater, parent, false)
            return MovieFooterViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val movie = getItem(position)
            holder.bind(movie!!)
        }
        if (holder is MovieFooterViewHolder){
            holder.bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    override fun onCurrentListChanged(
        previousList: PagedList<Movie>?,
        currentList: PagedList<Movie>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
        Timber.d("on current list changed!")
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State){
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


}