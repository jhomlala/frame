package com.jhomlala.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String, val year: String
)