package com.kryvovyaz.a96_weatherapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val data: List<Data>,
    val city_name: String,
    val lon: Double,
    val timezone: String,
    val lat: Double,
    val country_code: String,
    val state_code: String
):Parcelable