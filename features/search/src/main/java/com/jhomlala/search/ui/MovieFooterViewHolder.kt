package com.jhomlala.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.search.databinding.ItemMovieFooterBinding
import com.jhomlala.search.model.State

class MovieFooterViewHolder(
    binding: ItemMovieFooterBinding,
    private val viewModel: MovieFooterViewModel
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    fun bind(state: State) {
        viewModel.bind(state)
    }
}