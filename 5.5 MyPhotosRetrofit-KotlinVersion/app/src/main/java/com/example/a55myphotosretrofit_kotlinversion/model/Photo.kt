package com.example.a55myphotosretrofit_kotlinversion.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo (

    var albumId: Int,
    val id: Int,
    val title: String?,
    val url: String?,
    var thumbnailUrl: String?

) : Parcelable


