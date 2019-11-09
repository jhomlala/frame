package com.jhomlala.search.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhomlala.common.model.NavigationEvent
import com.jhomlala.common.model.NavigationEventType
import com.jhomlala.common.utils.launchActivity
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

        moviesAdapter = MovieAdapter(viewModel.items)
        binding.activitySearchRecyclerView.adapter = moviesAdapter
        binding.activitySearchRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun subscribeToViewModel() {
        viewModel.moviesRecyclerAdapterUpdateEvent.observe(this, Observer {
            moviesAdapter.notifyDataSetChanged()
        })

        viewModel.movieClickEvent.observe(this, Observer { event ->
            EventBus.post(NavigationEvent(NavigationEventType.DETAILS, event.movie))
        })
    }


}