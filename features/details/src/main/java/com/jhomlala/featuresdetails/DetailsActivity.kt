package com.jhomlala.featuresdetails

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.CustomTarget

import com.bumptech.glide.request.transition.Transition
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.ActivityDetailsBinding
import com.jhomlala.model.Movie
import jp.wasabeef.glide.transformations.BlurTransformation

import kotlinx.android.synthetic.main.activity_details.*
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        val extras = intent.extras
        Timber.d("Extras: " + extras)
        if (extras != null && extras.containsKey(BundleConst.MOVIE)) {
            val movie = extras.getParcelable<Movie>(BundleConst.MOVIE)
            if (movie != null) {
                setupToolbar(movie)
                setupBackground(movie)
            } else {
                Timber.e("Movie is null")
                return
            }
        } else {
            Timber.e("Movie is not passed")
            finish()
        }

    }

    private fun setupToolbar(movie: Movie) {
        setSupportActionBar(binding.activityDetailsToolbar)
        title = movie.title
    }

    private fun setupBackground(movie: Movie) {
        val url = movie.poster.replace("SX300", "SX900")
        Glide.with(this).load(url).apply(bitmapTransform(BlurTransformation(10, 2)))
            .into(binding.activityDetailsBackgroundImageView)

        Glide.with(this).load(url).into(binding.activityDetailsPosterImageView)
        binding.activityDetailsTitleTextView.text = movie.title
        binding.activityDetailsYearTextView.text = movie.year
    }

}
