package com.jhomlala.search.ui

import EventBus
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhomlala.model.Movie
import com.jhomlala.search.model.MovieClickEvent
import timber.log.Timber

class MovieViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var year = MutableLiveData<String>()
    private lateinit var movie: Movie

    fun setup(movie: Movie) {
        this.movie = movie
        title.value = movie.title
        year.value = movie.year
    }

    fun onItemClicked(view: View) {
        Timber.d("Clicked!!!")
        EventBus.post(MovieClickEvent(movie))
    }
}