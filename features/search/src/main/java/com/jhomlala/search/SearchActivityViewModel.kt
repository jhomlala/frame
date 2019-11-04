package com.jhomlala.search

import android.util.Log
import androidx.databinding.adapters.SearchViewBindingAdapter
import com.jhomlala.commonuicomponents.BaseActivityViewModel
import com.jhomlala.repository.service.OmdbService
import kotlinx.coroutines.*
import org.koin.core.inject

class SearchActivityViewModel: BaseActivityViewModel() {

    private val omdbService: OmdbService by inject()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun searchMovies(movieTitle: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResult = omdbService.searchMovie("56a61252",movieTitle)
                Log.d("Test", "Got movies: " + searchResult)
            } catch (exception: Exception){
                Log.d("Test", "Exc: " + exception)
            }
        }
    }

    fun onSearch(submit: SearchViewBindingAdapter.OnQueryTextSubmit, change: SearchViewBindingAdapter.OnQueryTextChange){
        Log.d("Test","ON search!")

    }
}