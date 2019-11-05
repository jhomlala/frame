package com.jhomlala.search

import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding

class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
    val viewModel = MovieViewModel()

    init{
         binding.viewModel = viewModel
    }

    fun bind(movie: Movie) {
        viewModel.title.value = movie.title
    }

}