package com.jhomlala.search.ui

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel
import com.jhomlala.common.ui.RecyclerAdapterUpdateEvent

import com.jhomlala.model.Movie
import com.jhomlala.search.model.MovieClickEvent

import kotlinx.coroutines.*
import org.koin.core.inject
import timber.log.Timber

class SearchActivityViewModel : BaseViewModel() {

    private val omdbService: OmdbService by inject()
    val moviesRecyclerAdapterUpdateEvent = SingleLiveEvent<RecyclerAdapterUpdateEvent>()
    val progressState = MutableLiveData<Boolean>()
    val emptyTextState = MutableLiveData<Boolean>()
    val startSearchTextState = MutableLiveData<Boolean>(true)
    val movieClickEvent = SingleLiveEvent<MovieClickEvent>()
    private var searchClicked = false

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
        val config = PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build()
        moviesList = LivePagedListBuilder<Int,Movie>(moviesDataSourceFactory,config).build()


    }

    fun searchMovies(movieTitle: String){
        searchClicked = true
        moviesDataSourceFactory.searchQuery = movieTitle
        moviesList.value?.dataSource?.invalidate()

    }



    fun onDataChanged(size: Int?) {
        Timber.d("on data changed: " + size)
        if (!searchClicked){
            return
        }
        val startSearchTextStateValue = startSearchTextState.value
        if (startSearchTextStateValue!!){
            startSearchTextState.value = false
        }
        emptyTextState.value = size == 0
    }
}