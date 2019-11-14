package com.jhomlala.search.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.model.Movie
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import java.util.concurrent.Executor

class MovieDataSourceFactory(val api: OmdbService, val coroutineScope: CoroutineScope) :
    DataSource.Factory<Int, Movie>() {

    val moviesDataSource = MutableLiveData<MovieDataSource>()
    var searchQuery = ""

    override fun create(): DataSource<Int, Movie> {
        Timber.d("Create!")
        val movieDataSource = MovieDataSource(api, coroutineScope)
        moviesDataSource.postValue(movieDataSource)
        movieDataSource.searchQuery = searchQuery
        return movieDataSource
    }


}