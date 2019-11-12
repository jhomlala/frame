package com.jhomlala.featuresdetails

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.jhomlala.common.ui.BaseActivity
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.featuresdetails.databinding.ActivityMovieDetailsBinding
import com.jhomlala.model.Movie

import kotlinx.android.synthetic.main.activity_movie_details.*
import timber.log.Timber

class MovieDetailsActivity : BaseActivity<MovieDetailsActivityViewModel,ActivityMovieDetailsBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_movie_details
    }

    override fun setupViewModel(): MovieDetailsActivityViewModel {
        return ViewModelProviders.of(this).get(MovieDetailsActivityViewModel::class.java)
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        if (extras != null && extras.containsKey(BundleConst.MOVIE)) {
            val movie = extras.getParcelable<Movie>(BundleConst.MOVIE)
            if (movie != null) {
                viewModel.movie = movie
                setupUi()
                //subscribeToViewModel()
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
        val navController by lazy { findNavController(R.id.activity_movie_details_nav_host_fragment) }
        val bundle = Bundle()
        bundle.putParcelable(BundleConst.MOVIE,viewModel.movie)
        navController.setGraph(R.navigation.movie_details_navigation, bundle)
    }

    private fun setupToolbar() {
        setSupportActionBar(viewDataBinding?.activityMovieDetailsToolbar)
        title = viewModel.movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
