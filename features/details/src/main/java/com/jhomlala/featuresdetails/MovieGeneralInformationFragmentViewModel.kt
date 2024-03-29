package com.jhomlala.featuresdetails

import SingleLiveEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel
import com.jhomlala.model.Movie
import org.koin.core.inject
import kotlinx.coroutines.*
import timber.log.Timber

class MovieGeneralInformationFragmentViewModel : BaseViewModel() {

    lateinit var movie: Movie
    lateinit var movieDetails: MovieDetails
    private val omdbService: OmdbService by inject()

    val movieDetailsSelectedEvent = SingleLiveEvent<MovieDetails>()
    val showExtrasClickedEvent = SingleLiveEvent<Void>()
    val plot = MutableLiveData<String>()
    val genre = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val year = MutableLiveData<String>()


    fun setup(movie: Movie) {
        this.movie = movie
        this.title.value = movie.title
        this.year.value = movie.year
        Timber.d("Set title: " + movie.title)
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDetails = omdbService.getMovie("56a61252", movie.imdbID)
            withContext(Dispatchers.Main) {
                setupMovieDetails(movieDetails)
            }
            Timber.d("Movie details: " + movieDetails)
        }
    }

    private fun setupMovieDetails(movieDetails: MovieDetails) {
        this.movieDetails = movieDetails
        plot.value = movieDetails.plot
        genre.value = movieDetails.genre
        movieDetailsSelectedEvent.value = movieDetails
    }

    fun onShowExtrasClicked(view: View){
        Timber.d("Show extras!")
        showExtrasClickedEvent.call()
    }
}