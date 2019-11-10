package com.jhomlala.featuresdetails

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.model.Rating
import com.jhomlala.common.repository.OmdbService
import com.jhomlala.common.ui.BaseViewModel
import com.jhomlala.model.Movie
import org.koin.core.inject
import kotlinx.coroutines.*
import timber.log.Timber

class DetailsActivityViewModel : BaseViewModel() {

    lateinit var movie: Movie
    lateinit var movieDetails: MovieDetails
    private val omdbService: OmdbService by inject()

    val onMovieDetailsSelected = SingleLiveEvent<MovieDetails>()
    val plot = MutableLiveData<String>()
    val genre = MutableLiveData<String>()
    //var imdbRating: RatingViewModel = RatingViewModel(Rating("1234","1234"))


    fun setup(movie: Movie) {
        this.movie = movie
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
        onMovieDetailsSelected.value = movieDetails

        //imdbRating = RatingViewModel(movieDetails.ratings.first())
    }
}