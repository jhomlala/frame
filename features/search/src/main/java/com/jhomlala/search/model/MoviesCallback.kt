package com.jhomlala.search.model

import com.jhomlala.model.Movie

interface MoviesCallback {
    fun onMoviesLoaded(movies: List<Movie>)
    fun onMoviesLoadFailed()
}