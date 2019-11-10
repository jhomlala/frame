package com.jhomlala.featuresdetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jhomlala.common.ui.BaseFragment
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.FragmentMovieGeneralInformationBinding
import com.jhomlala.model.Movie
import jp.wasabeef.glide.transformations.BlurTransformation


class MovieGeneralInformationFragment :
    BaseFragment<MovieGeneralInformationFragmentViewModel, FragmentMovieGeneralInformationBinding>() {

    private lateinit var firstRatingViewModel: RatingViewModel
    private lateinit var secondRatingViewModel: RatingViewModel
    private lateinit var thirdRatingViewModel: RatingViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_movie_general_information
    }

    override fun setupViewModel(): MovieGeneralInformationFragmentViewModel {
        val viewModel =
            ViewModelProviders.of(this).get(MovieGeneralInformationFragmentViewModel::class.java)
        val binding = getViewDataBinding()
        binding?.viewModel = viewModel
        binding?.executePendingBindings()
        return viewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = this.arguments
        if (arguments != null && arguments.containsKey(BundleConst.MOVIE)) {
            val movie = arguments.get(BundleConst.MOVIE) as Movie
            viewModel.setup(movie)
        }
        subscribeToViewModel()
        setupUi()
    }

    private fun setupUi() {
        val url = viewModel.movie.poster.replace("SX300", "SX900")
        setupBackground(url)
        setupPoster(url)
    }

    private fun setupBackground(url: String) {
        val binding = getViewDataBinding() ?: return
        Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(BlurTransformation(10, 2)))
            .into(binding.fragmentMovieGeneralInformationBackgroundImageView)
    }

    private fun setupPoster(url: String) {
        val binding = getViewDataBinding() ?: return
        Glide.with(this).load(url).into(binding.activityDetailsPosterImageView)
    }


    private fun subscribeToViewModel() {
        val binding = getViewDataBinding() ?: return
        viewModel.movieDetailsSelectedEvent.observe(this, Observer { movieDetails ->
            movieDetails.ratings.forEachIndexed { index, rating ->
                if (index == 0) {
                    firstRatingViewModel = ViewModelProviders.of(this)
                        .get("firstRatingModel", RatingViewModel::class.java)
                    firstRatingViewModel.setup(movieDetails.ratings[index])
                    binding.firstRatingViewModel = firstRatingViewModel
                }
                if (index == 1) {
                    secondRatingViewModel = ViewModelProviders.of(this)
                        .get("secondRatingModel", RatingViewModel::class.java)
                    secondRatingViewModel.setup(movieDetails.ratings[index])
                    binding.secondRatingViewModel = secondRatingViewModel
                }
                if (index == 2) {
                    thirdRatingViewModel = ViewModelProviders.of(this)
                        .get("thirdRatingModel", RatingViewModel::class.java)
                    thirdRatingViewModel.setup(movieDetails.ratings[index])
                    binding.thirdRatingViewModel = thirdRatingViewModel
                }
            }
            binding.executePendingBindings()

        })
        viewModel.showExtrasClickedEvent.observe(this, Observer {
            val bundle = Bundle()
            bundle.putParcelable(BundleConst.MOVIE_DETAILS, viewModel.movieDetails)
            findNavController().navigate(
                R.id.action_generalInformationFragment_to_movieExtraInformationFragment,
                bundle
            )
        })
    }


}