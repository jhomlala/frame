package com.jhomlala.frame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jhomlala.common.model.NavigationEvent
import com.jhomlala.common.model.NavigationEventType
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.common.utils.launchActivity
import com.jhomlala.featuresdetails.MovieDetailsActivity
import com.jhomlala.model.Movie
import com.jhomlala.search.ui.SearchActivity
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerInBus()
        EventBus.post(NavigationEvent(NavigationEventType.SEARCH))
    }

    private fun registerInBus() {
        EventBus.register(
            this.javaClass.simpleName,
            Dispatchers.Main,
            NavigationEvent::class.java
        ) {
            navigationEvent ->
            if (navigationEvent.type == NavigationEventType.SEARCH){
                launchActivity<SearchActivity>()
            }
            if (navigationEvent.type == NavigationEventType.DETAILS){
                val bundle = Bundle()
                bundle.putParcelable(BundleConst.MOVIE,navigationEvent.data as Movie)
                Timber.d("Bundle: " + bundle)
                launchActivity<MovieDetailsActivity>(bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.unregister(this.javaClass.simpleName)
    }
}
