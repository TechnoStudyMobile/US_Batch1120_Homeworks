package com.kryvovyaz.a96_weatherapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val icon: String,
    val code: Int,
    val description: String
):Parcelable