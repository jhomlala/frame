package com.jhomlala.featuresdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.ui.BaseFragment
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.FragmentMovieExtraInformationBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import timber.log.Timber


class MovieExtraInformationFragment :
    BaseFragment<MovieExtraInformationFragmentViewModel, FragmentMovieExtraInformationBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_movie_extra_information
    }

    override fun setupViewModel(): MovieExtraInformationFragmentViewModel {
        val viewModel = ViewModelProviders.of(this).get(MovieExtraInformationFragmentViewModel::class.java)
        val binding = getViewDataBinding()
        binding?.viewModel = viewModel
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDetails = arguments?.get(BundleConst.MOVIE_DETAILS) as MovieDetails
        viewModel.setup(movieDetails)
        setupUi()
    }

    private fun setupUi() {
        val url = viewModel.movieDetails.poster.replace("SX300", "SX900")
        setupBackground(url)
    }

    private fun setupBackground(url: String) {
        val binding = getViewDataBinding() ?: return
        Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(BlurTransformation(10, 2)))
            .into(binding.fragmentMovieGeneralInformationBackgroundImageView)
    }

}
