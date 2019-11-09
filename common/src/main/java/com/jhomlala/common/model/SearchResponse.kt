package com.jhomlala.common.model

import com.google.gson.annotations.SerializedName
import com.jhomlala.model.Movie

data class SearchResponse(
    @SerializedName("Search") val search: List<Movie>,
    val totalResults: Int,
    @SerializedName("Response") val response: Boolean,
    @SerializedName("Error") val error: String
)
