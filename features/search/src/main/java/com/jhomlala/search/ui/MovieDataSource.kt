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

    var state: MutableLiveData<Int> = MutableLiveData()
    var searchQuery: String = ""


    fun setSearchQueryTest(text: String){
        searchQuery = text
        Timber.d("Set search query: " + searchQuery + " hashcode: " + hashCode())
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        Timber.d("Load initial: " + searchQuery + " hashcode: " + hashCode());
        state.postValue(1)
        scope.launch(Dispatchers.IO) {
            val result = omdbService.searchMovie("56a61252", searchQuery, 1)
            withContext(Dispatchers.Main) {
                state.value = 0
                if (result.response) {
                    callback.onResult(result.search, null, 2)
                } else {
                    callback.onResult(arrayListOf(),null,null)
                }
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Timber.d("Load after!" + searchQuery);
        state.postValue(1)
        scope.launch(Dispatchers.IO) {
            val result = omdbService.searchMovie("56a61252", searchQuery, params.key)
            withContext(Dispatchers.Main) {
                state.value = 0
                if (result.response) {
                    Timber.d("Got results!")
                    callback.onResult(result.search, params.key + 1)
                } else {
                    Timber.d("No results!")
                    callback.onResult(ArrayList(), null)
                }

            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

}