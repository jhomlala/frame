package com.jhomlala.search.ui

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhomlala.common.model.NavigationEvent
import com.jhomlala.common.model.NavigationEventType
import com.jhomlala.search.R

import com.jhomlala.search.databinding.ActivitySearchBinding
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var viewModel: SearchActivityViewModel
    lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        //val viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()
        setupUI()
        subscribeToViewModel()
        Timber.d("Created search activity")
    }

    private fun setupUI() {
        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Test", "Query text submit: " + query)
                if (query != null) {
                    viewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Test", "Query text change: " + newText)
                return true
            }
        })
        setSupportActionBar(binding.toolbar)
        binding.search.requestFocus()

        //moviesAdapter = MovieAdapter(viewModel.items)
        //binding.activitySearchRecyclerView.adapter = moviesAdapter
        val adapter = MoviesPagedAdapter()
        binding.activitySearchRecyclerView.adapter = adapter
        viewModel.moviesList.observe(this, Observer {
            adapter.submitList(it)
            onDataChanged()
        })
        binding.activitySearchRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                onDataChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                onDataChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                onDataChanged()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                onDataChanged()
            }
        })
    }

    private fun subscribeToViewModel() {
        viewModel.moviesRecyclerAdapterUpdateEvent.observe(this, Observer {
            //moviesAdapter.submitList()
        })

        viewModel.movieClickEvent.observe(this, Observer { event ->
            EventBus.post(NavigationEvent(NavigationEventType.DETAILS, event.movie))
        })

    }

    private fun onDataChanged(){
        Timber.d("On data changed "+viewModel.moviesList.value?.size)
        viewModel.onDataChanged(viewModel.moviesList.value?.size)
    }


}