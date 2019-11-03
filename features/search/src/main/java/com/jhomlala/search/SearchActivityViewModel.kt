package com.jhomlala.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jhomlala.repository.service.OmdbService
import kotlinx.coroutines.*

class SearchActivityViewModel: ViewModel() {
    val omdbService = OmdbService.create()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun searchMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResult = omdbService.searchMovie("56a61252","Joker")
                Log.d("Test", "Got movies: " + searchResult)
            } catch (exception: Exception){
                Log.d("Test", "Exc: " + exception)
            }
        }
    }
}