package com.jhomlala.search.ui

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel
import com.jhomlala.common.ui.RecyclerAdapterUpdateEvent
import com.jhomlala.common.ui.RecyclerAdapterUpdateEventType

import com.jhomlala.model.Movie
import com.jhomlala.search.model.MovieClickEvent

import kotlinx.coroutines.*
import org.koin.core.inject
import timber.log.Timber

class SearchActivityViewModel : BaseViewModel() {

    private val omdbService: OmdbService by inject()
    val items: ArrayList<Movie> = ArrayList()
    val moviesRecyclerAdapterUpdateEvent = SingleLiveEvent<RecyclerAdapterUpdateEvent>()
    val progressState = MutableLiveData<Boolean>()
    val emptyTextState = MutableLiveData<Boolean>()
    val startSearchTextState = MutableLiveData<Boolean>(true)
    val movieClickEvent = SingleLiveEvent<MovieClickEvent>()

    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory
    lateinit var moviesList: LiveData<PagedList<Movie>>

    init {
        EventBus.register(
            this.javaClass.simpleName,
            Dispatchers.Main,
            MovieClickEvent::class.java
        ) {
            movieClickEvent.value = it
        }

        moviesDataSourceFactory = MovieDataSourceFactory(omdbService,viewModelScope)
        val config = PagedList.Config.Builder().setPageSize(10).build()
        moviesList = LivePagedListBuilder<Int,Movie>(moviesDataSourceFactory,config).build()



    }

    fun searchMovies(movieTitle: String){
        moviesDataSourceFactory.searchQuery = movieTitle
        moviesList.value?.dataSource?.invalidate()
    }

    fun searchMovies2(movieTitle: String) {
        items.clear()
        refreshAllElementsInRecyclerView()
        progressState.value = true
        startSearchTextState.value = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResult = omdbService.searchMovie("56a61252", movieTitle,1)
                Timber.d("Search result: " + searchResult)
                if (!searchResult.response) {
                    onSearchErrorResponse()
                    return@launch
                }

                val results = searchResult.search
                results.forEach { movie ->
                    if (movie.poster == "N/A") {
                        movie.poster = "https://i.imgur.com/59Uc49b.png"
                    }
                }
                items.addAll(results)
                withContext(Dispatchers.Main) {
                    progressState.value = false
                    refreshAllElementsInRecyclerView()
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                Timber.e(exception)
            }
        }
    }

    private suspend fun onSearchErrorResponse() {
        Timber.e("On search error response")
        withContext(Dispatchers.Main) {
            emptyTextState.value = true
            progressState.value = false
        }
    }

    private fun refreshAllElementsInRecyclerView() {
        moviesRecyclerAdapterUpdateEvent.value =
            RecyclerAdapterUpdateEvent(RecyclerAdapterUpdateEventType.ALL)
    }

}