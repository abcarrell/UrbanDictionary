package com.acarrell.urbandictionary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UDResponse(
    @SerializedName("list") val entries: List<DictionaryEntry>
) : Parcelable