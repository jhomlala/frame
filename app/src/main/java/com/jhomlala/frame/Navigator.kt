package com.jhomlala.frame

import com.jhomlala.model.Movie

interface Navigator{
    fun launchSearchActivity()
    fun launchDetailsActivity(movie: Movie)
}