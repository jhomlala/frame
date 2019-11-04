package com.jhomlala.search

import SingleLiveEvent
import android.view.View
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    val onNextClickedEvent = SingleLiveEvent<Void>()

    fun onNextClicked(view: View){
        onNextClickedEvent.call()
    }

}
