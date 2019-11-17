package com.jhomlala.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhomlala.model.Movie
import com.jhomlala.search.databinding.ItemMovieBinding

class MovieViewHolder(private val binding: ItemMovieBinding, val viewModel: MovieViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = viewModel
    }
    fun bind(movie: Movie) {
        viewModel.setup(movie)
        Glide.with(binding.root.context).load(movie.poster).into(binding.itemMoviePoster)
    }

}