package com.samil.app.theweather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ForecastDummy (
    val imageId: Int = 0,
    val textViewWeekdays: String = "Sunday",
    val textViewForecast: String = "Scattered Clouds",
    val textViewLowTemp: String = "11°",
    val textViewHighTemp: String = "33°"): Parcelable