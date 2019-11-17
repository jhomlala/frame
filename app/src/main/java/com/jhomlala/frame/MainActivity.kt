package com.jhomlala.frame
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jhomlala.common.model.NavigationEvent
import com.jhomlala.common.model.NavigationEventType
import com.jhomlala.common.ui.BaseActivity
import com.jhomlala.common.utils.BundleConst
import com.jhomlala.common.utils.launchActivity
import com.jhomlala.featuresdetails.MovieDetailsActivity
import com.jhomlala.frame.databinding.ActivityMainBinding
import com.jhomlala.model.Movie
import com.jhomlala.search.ui.SearchActivity
import kotlinx.coroutines.Dispatchers


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setupViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerInBus()
        //EventBus.post(NavigationEvent(NavigationEventType.SEARCH))
    }

    private fun registerInBus() {
        EventBus.register(
            this.javaClass.simpleName,
            Dispatchers.Main,
            NavigationEvent::class.java
        ) { navigationEvent ->
            if (navigationEvent.type == NavigationEventType.SEARCH) {
                launchActivity<SearchActivity>()
            }
            if (navigationEvent.type == NavigationEventType.DETAILS) {
                val bundle = Bundle()
                bundle.putParcelable(BundleConst.MOVIE, navigationEvent.data as Movie)
                launchActivity<MovieDetailsActivity>(bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.unregister(this.javaClass.simpleName)
    }
}
