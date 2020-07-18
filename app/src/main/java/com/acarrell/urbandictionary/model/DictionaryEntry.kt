package com.acarrell.urbandictionary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DictionaryEntry(
    @SerializedName("defid") val defId: Int,
    @SerializedName("word") val word: String,
    @SerializedName("definition") val definition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbs_up") val thumbsUp: Int,
    @SerializedName("thumbs_down") val thumbsDown: Int,
    @SerializedName("author") val author: String,
    @SerializedName("example") val example: String,
    @SerializedName("written_on") val writtenOn: Date,
    @SerializedName("sound_urls") val soundUrls: List<String>
) : Parcelable