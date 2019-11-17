package com.jhomlala.search.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.Executor

class MovieDataSource(private val omdbService: OmdbService, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()
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
            val result = omdbService.searchMovie("56a61252", searchQuery, page)
            withContext(Dispatchers.Main) {
                if (result.response) {
                    setState(State.DONE)
                    val movies = result.search
                    fixPosterUrls(movies)
                    moviesCallback.onMoviesLoaded(movies)
                } else {
                    if (page > 1){
                        setState(State.DONE)
                    } else {
                        setState(State.ERROR)
                    }
                    moviesCallback.onMoviesLoadFailed()
                }

            }
        }
    }

    private fun fixPosterUrls(movies: List<Movie>) {
        movies.forEach { movie ->
            if (movie.poster.isNullOrEmpty() || movie.poster == "N/A") {
                movie.poster = "https://i.imgur.com/59Uc49b.png"

            }
        }
    }

    private fun setState(currentState: State){
        state.postValue(currentState)
    }

}

interface MoviesCallback {
    fun onMoviesLoaded(movies: List<Movie>)
    fun onMoviesLoadFailed()
}

enum class State{
    DONE,LOADING,ERROR
}