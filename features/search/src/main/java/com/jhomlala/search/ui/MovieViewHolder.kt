package com.jhomlala.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding
import com.jhomlala.search.ui.MovieViewModel

class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
    val viewModel = MovieViewModel()

    init{
         binding.viewModel = viewModel
    }

    fun bind(movie: Movie) {
        viewModel.setup(movie)
        Glide.with(binding.root.context).load(movie.poster).into(binding.itemMoviePoster)
    }

}