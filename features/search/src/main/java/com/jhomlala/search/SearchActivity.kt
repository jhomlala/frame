package com.jhomlala.search

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jhomlala.repository.service.OmdbService
import com.jhomlala.search.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var viewModel: SearchActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        //val viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        binding.viewModel = viewModel

        setupUI()
    }

    private fun setupUI() {
        binding.activitySearchSearchView.setOnQueryTextListener(object :
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
    }


}