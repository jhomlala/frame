package com.jhomlala.featuresdetails

import androidx.lifecycle.MutableLiveData
import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.ui.BaseViewModel

class MovieExtraInformationFragmentViewModel : BaseViewModel() {
    lateinit var movieDetails: MovieDetails
    val runtime = MutableLiveData<String>()
    val rated = MutableLiveData<String>()
    val releaseDate = MutableLiveData<String>()
    val language = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val production = MutableLiveData<String>()
    val boxOffice = MutableLiveData<String>()
    val director = MutableLiveData<String>()
    val writer = MutableLiveData<String>()
    val actors = MutableLiveData<String>()

    fun setup(movieDetails: MovieDetails){
        this.movieDetails = movieDetails
        runtime.value = movieDetails.runtime
        rated.value = movieDetails.rated
        releaseDate.value = movieDetails.released
        language.value = movieDetails.language
        country.value = movieDetails.country
        production.value = movieDetails.production
        boxOffice.value = movieDetails.boxOffice
        director.value = movieDetails.director
        writer.value = movieDetails.writer
        actors.value = movieDetails.actors
    }
}