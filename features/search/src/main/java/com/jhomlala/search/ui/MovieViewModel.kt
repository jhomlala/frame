package com.jhomlala.search.ui

import EventBus
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhomlala.model.Movie
import com.jhomlala.search.model.MovieClickEvent
import timber.log.Timber

class MovieViewModel : ViewModel() {
    var title = ObservableField<String>()
    var year = ObservableField<String>()
    private lateinit var movie: Movie

    fun setup(movie: Movie) {
        this.movie = movie
        title.set(movie.title)
        year.set(movie.year)
    }

    fun onItemClicked(view: View) {
        Timber.d("Clicked!!!")
        EventBus.post(MovieClickEvent(movie))
    }
}