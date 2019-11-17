package com.jhomlala.search.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.utils.AppConst
import com.jhomlala.model.Movie
import com.jhomlala.search.model.ErrorState
import com.jhomlala.search.model.MoviesCallback
import com.jhomlala.search.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieDataSource(private val omdbService: OmdbService, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()
    val errorState: MutableLiveData<ErrorState> = MutableLiveData()
    var searchQuery: String = ""


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        loadMovies(1, object : MoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                callback.onResult(movies, 1, 2)
            }

            override fun onMoviesLoadFailed() {
                callback.onResult(ArrayList<Movie>(), 1, null)
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadMovies(params.key, object : MoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                callback.onResult(movies, params.key + 1)
            }

            override fun onMoviesLoadFailed() {
                callback.onResult(ArrayList<Movie>(), null)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    private fun loadMovies(page: Int, moviesCallback: MoviesCallback) {

        setState(State.LOADING)
        scope.launch(Dispatchers.IO) {
            try {
                val result = omdbService.searchMovie(AppConst.API_KEY, searchQuery, page)
                withContext(Dispatchers.Main) {
                    if (result.response) {
                        setState(State.DONE)
                        setErrorState(ErrorState.NONE)
                        val movies = result.search
                        fixPosterUrls(movies)
                        moviesCallback.onMoviesLoaded(movies)
                    } else {
                        if (page > 1) {
                            setErrorState(ErrorState.NONE)
                            setState(State.DONE)
                        } else {
                            setErrorState(ErrorState.ERROR_NO_RESULTS)
                            setState(State.ERROR)
                        }
                        moviesCallback.onMoviesLoadFailed()
                    }
                }
            } catch (exception: Exception) {
                Timber.e(exception)
                setErrorState(ErrorState.ERROR_NETWORK)
                setState(State.ERROR)
            }
        }

    }

    private fun fixPosterUrls(movies: List<Movie>) {
        movies.forEach { movie ->
            if (movie.poster.isNullOrEmpty() || movie.poster == "N/A") {
                movie.poster = AppConst.PLACEHOLDER_IMAGE_URL

            }
        }
    }

    private fun setState(currentState: State) {
        state.postValue(currentState)
    }

    private fun setErrorState(currentErrorState: ErrorState) {
        errorState.postValue(currentErrorState)
    }
}