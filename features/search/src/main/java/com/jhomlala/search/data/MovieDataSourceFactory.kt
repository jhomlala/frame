package com.jhomlala.search.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.model.Movie
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
    private val api: OmdbService,
    private val coroutineScope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {
    val moviesDataSource = MutableLiveData<MovieDataSource>()
    var searchQuery = ""

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(api, coroutineScope)
        moviesDataSource.postValue(movieDataSource)
        movieDataSource.searchQuery = searchQuery
        return movieDataSource
    }
}