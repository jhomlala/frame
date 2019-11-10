package com.jhomlala.featuresdetails

import androidx.databinding.ObservableField
import com.jhomlala.common.model.Rating
import com.jhomlala.common.ui.BaseViewModel

class RatingViewModel: BaseViewModel() {
    fun setup(rating: Rating) {
        if (rating.source.startsWith("Internet")){
            name.set("IMDB")
        } else {
            name.set(rating.source)
        }
        value.set(rating.value)
    }

    val name = ObservableField<String>()
    val value = ObservableField<String>()


}