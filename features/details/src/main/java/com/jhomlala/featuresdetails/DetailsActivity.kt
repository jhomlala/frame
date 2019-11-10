package com.jhomlala.featuresdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform

import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.ActivityDetailsBinding
import com.jhomlala.model.Movie
import jp.wasabeef.glide.transformations.BlurTransformation

import timber.log.Timber

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsActivityViewModel
    private lateinit var firstRatingViewModel: RatingViewModel
    private lateinit var secondRatingViewModel: RatingViewModel
    private lateinit var thirdRatingViewModel: RatingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        viewModel = ViewModelProviders.of(this).get(DetailsActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        val extras = intent.extras
        Timber.d("Extras: " + extras)
        if (extras != null && extras.containsKey(BundleConst.MOVIE)) {
            val movie = extras.getParcelable<Movie>(BundleConst.MOVIE)
            if (movie != null) {
                viewModel.setup(movie)
                setupUi()
                subscribeToViewModel()
            } else {
                Timber.e("Movie is null")
                return
            }
        } else {
            Timber.e("Movie is not passed")
            finish()
        }

    }

    private fun setupUi() {
        setupToolbar()
        setupBackground()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.activityDetailsToolbar)
        title = viewModel.movie.title
    }

    private fun setupBackground() {
        val movie = viewModel.movie
        val url = movie.poster.replace("SX300", "SX900")
        Glide.with(this).load(url).apply(bitmapTransform(BlurTransformation(10, 2)))
            .into(binding.activityDetailsBackgroundImageView)

        Glide.with(this).load(url).into(binding.activityDetailsPosterImageView)
        binding.activityDetailsTitleTextView.text = movie.title
        binding.activityDetailsYearTextView.text = movie.year
    }

    private fun subscribeToViewModel(){
        viewModel.onMovieDetailsSelected.observe(this, Observer {
            movieDetails ->
            movieDetails.ratings.forEachIndexed { index, rating ->
                if (index == 0){
                    firstRatingViewModel = ViewModelProviders.of(this).get("firstRatingModel",RatingViewModel::class.java)
                    firstRatingViewModel.setup(movieDetails.ratings[index])
                    binding.firstRatingViewModel = firstRatingViewModel
                }
                if (index == 1){
                    secondRatingViewModel = ViewModelProviders.of(this).get("secondRatingModel",RatingViewModel::class.java)
                    secondRatingViewModel.setup(movieDetails.ratings[index])
                    binding.secondRatingViewModel = secondRatingViewModel
                }
                if (index == 2){
                    thirdRatingViewModel = ViewModelProviders.of(this).get("thirdRatingModel",RatingViewModel::class.java)
                    thirdRatingViewModel.setup(movieDetails.ratings[index])
                    binding.thirdRatingViewModel = thirdRatingViewModel
                }
            }
            binding.executePendingBindings()

        })
    }

}
