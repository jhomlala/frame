package com.jhomlala.search.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieFooterViewModel : ViewModel() {
    val progressState = ObservableBoolean()

    fun bind(state: State) {
        progressState.set(progressState == State.LOADING)
    }
}