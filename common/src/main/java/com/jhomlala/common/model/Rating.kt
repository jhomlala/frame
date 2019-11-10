package com.jhomlala.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
): Parcelable