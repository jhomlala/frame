package com.jhomlala.search.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.jhomlala.search.model.State

class MovieFooterViewModel : ViewModel() {
    val progressState = ObservableBoolean()

    fun bind(state: State) {
        progressState.set(state == State.LOADING)
    }
}