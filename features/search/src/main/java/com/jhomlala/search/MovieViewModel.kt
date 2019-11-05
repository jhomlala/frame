package com.jhomlala.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    var title = MutableLiveData<String>()
}