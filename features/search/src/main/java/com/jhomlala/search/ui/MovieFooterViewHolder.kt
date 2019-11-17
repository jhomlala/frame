package com.jhomlala.search.ui

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.search.databinding.ItemMovieFooterBinding

class MovieFooterViewHolder(val binding: ItemMovieFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val viewModel: MovieFooterViewModel
    init{
        viewModel = MovieFooterViewModel()
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
    fun bind(state: State){
        viewModel.bind(state)
    }
}