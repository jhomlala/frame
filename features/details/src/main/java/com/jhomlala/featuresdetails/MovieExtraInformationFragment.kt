package com.jhomlala.featuresdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.ui.BaseFragment
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.FragmentMovieExtraInformationBinding
import timber.log.Timber


class MovieExtraInformationFragment :
    BaseFragment<MovieExtraInformationFragmentViewModel, FragmentMovieExtraInformationBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_movie_extra_information
    }

    override fun setupViewModel(): MovieExtraInformationFragmentViewModel {
        return ViewModelProviders.of(this).get(MovieExtraInformationFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieDetails = arguments?.get(BundleConst.MOVIE_DETAILS) as MovieDetails
        Timber.d("Movie details: " + movieDetails)
    }

}
