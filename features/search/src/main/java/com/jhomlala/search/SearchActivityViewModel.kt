package com.jhomlala.search

import SingleLiveEvent
import android.util.Log
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.MutableLiveData
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel

import com.jhomlala.model.Movie

import kotlinx.coroutines.*
import org.koin.core.inject

class SearchActivityViewModel : BaseViewModel() {

    private val omdbService: OmdbService by inject()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val items: ArrayList<Movie> = ArrayList()
    val moviesRecyclerAdapterUpdateEvent = SingleLiveEvent<RecyclerAdapterUpdateEvent>()
    val progressState = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun searchMovies(movieTitle: String) {
        items.clear()
        progressState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResult = omdbService.searchMovie("56a61252", movieTitle)
                items.addAll(searchResult.Search)
                withContext(Dispatchers.Main) {
                    moviesRecyclerAdapterUpdateEvent.value =
                        RecyclerAdapterUpdateEvent(RecyclerAdapterUpdateEventType.ALL)
                    progressState.value = false
                }
                Log.d("Test", "Got movies: " + searchResult)
            } catch (exception: Exception) {
                Log.d("Test", "Exc: " + exception)
            }
        }
    }

}