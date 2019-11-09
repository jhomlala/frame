package com.jhomlala.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.model.Movie
import android.view.LayoutInflater
import com.jhomlala.search.databinding.ItemMovieBinding


class MovieAdapter(val items: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

}