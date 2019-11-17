package com.jhomlala.search.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhomlala.common.model.NavigationEvent
import com.jhomlala.common.model.NavigationEventType
import com.jhomlala.common.ui.BaseActivity
import com.jhomlala.search.R

import com.jhomlala.search.databinding.ActivitySearchBinding
import com.jhomlala.search.model.MovieItemType

class SearchActivity : BaseActivity<SearchActivityViewModel, ActivitySearchBinding>() {

    private lateinit var moviesAdapter: MoviesPagedAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun setupViewModel(): SearchActivityViewModel {
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        return viewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = viewDataBinding
        binding?.viewModel = viewModel
        binding?.executePendingBindings()
        setupUI()
        subscribeToViewModel()
    }

    private fun setupUI() {
        val binding = viewDataBinding ?: return
        setupSearchView(binding)
        setupToolbar(binding)
        setupRecyclerView(binding)
    }

    private fun setupRecyclerView(binding: ActivitySearchBinding) {
        moviesAdapter = MoviesPagedAdapter(::createViewModelForMovieItem)
        binding.activitySearchRecyclerView.adapter = moviesAdapter
        binding.activitySearchRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearchView(binding: ActivitySearchBinding) {
        binding.activitySearchSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.activitySearchSearch.requestFocus()
    }

    private fun setupToolbar(binding: ActivitySearchBinding) {
        setSupportActionBar(binding.activitySearchToolbar)
    }

    private fun subscribeToViewModel() {
        viewModel.movieClickEvent.observe(this, Observer { event ->
            EventBus.post(NavigationEvent(NavigationEventType.DETAILS, event.movie))
        })

        viewModel.getState().observe(this, Observer { state ->
            moviesAdapter.setState(state)
            viewModel.onStateChanged(state)
        })

        viewModel.getErrorState().observe(this, Observer { errorState ->
            viewModel.setupLastErrorState(errorState)
        })

        viewModel.moviesList.observe(this, Observer { pagedList ->
            if (::moviesAdapter.isInitialized)
                moviesAdapter.submitList(pagedList)
        })
    }

    private fun createViewModelForMovieItem(itemType: Int): ViewModel {
        return if (itemType == MovieItemType.DATA) {
            ViewModelProviders.of(this)
                .get("${System.currentTimeMillis()}", MovieViewModel::class.java)
        } else {
            ViewModelProviders.of(this)
                .get("${System.currentTimeMillis()}", MovieFooterViewModel::class.java)
        }

    }
}