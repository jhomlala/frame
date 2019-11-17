package com.jhomlala.search.ui

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel

import com.jhomlala.model.Movie
import com.jhomlala.search.R
import com.jhomlala.search.data.MovieDataSource
import com.jhomlala.search.data.MovieDataSourceFactory
import com.jhomlala.search.model.ErrorState
import com.jhomlala.search.model.MovieClickEvent
import com.jhomlala.search.model.State

import kotlinx.coroutines.*
import org.koin.core.inject
import timber.log.Timber

class SearchActivityViewModel : BaseViewModel() {

    private val omdbService: OmdbService by inject()

    private var lastErrorState: ErrorState? = null
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory
    private var searchClicked = false

    val progressState = MutableLiveData<Boolean>()
    val emptyTextState = MutableLiveData<Boolean>()
    val emptyText = MutableLiveData<String>()
    val startSearchTextState = MutableLiveData<Boolean>(true)
    val movieClickEvent = SingleLiveEvent<MovieClickEvent>()
    var moviesList: LiveData<PagedList<Movie>>

    init {
        EventBus.register(
            this.javaClass.simpleName,
            Dispatchers.Main,
            MovieClickEvent::class.java
        ) {
            movieClickEvent.value = it
        }

        moviesDataSourceFactory =
            MovieDataSourceFactory(omdbService, viewModelScope)
        val config = PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build()
        moviesList = LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, config).build()
        setupEmptyText()
    }

    fun searchMovies(movieTitle: String) {
        searchClicked = true
        moviesDataSourceFactory.searchQuery = movieTitle.trim()
        moviesList.value?.dataSource?.invalidate()
    }


    fun getState(): LiveData<State> {
        return Transformations.switchMap<MovieDataSource, State>(
            moviesDataSourceFactory.moviesDataSource,
            MovieDataSource::state
        )
    }

    fun getErrorState(): LiveData<ErrorState> {
        return Transformations.switchMap<MovieDataSource, ErrorState>(
            moviesDataSourceFactory.moviesDataSource,
            MovieDataSource::errorState
        )
    }

    fun onStateChanged(state: State?) {
        if (!searchClicked) {
            Timber.e("Search not clicked yet!")
            return
        }

        val size = moviesList.value?.size
        startSearchTextState.value = false
        if (size == 0) {
            if (state == State.DONE || state == State.ERROR) {
                emptyTextState.value = true
                progressState.value = false
            } else {
                progressState.value = true
            }
        }

        if (size != null && size > 0) {
            emptyTextState.value = false
            progressState.value = false
        }
    }

    private fun setupEmptyText() {

        if (lastErrorState == null || lastErrorState == ErrorState.ERROR_NO_RESULTS) {
            emptyText.value = getString(R.string.activity_search_error_empty)
        } else if (lastErrorState == ErrorState.ERROR_NETWORK) {
            emptyText.value = getString(R.string.activity_search_error_internet_connection)
        } else {
            emptyText.value = ""
        }

    }

    fun setupLastErrorState(state: ErrorState) {
        this.lastErrorState = state
        setupEmptyText()
    }
}