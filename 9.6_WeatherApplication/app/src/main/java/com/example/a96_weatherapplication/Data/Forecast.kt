package com.example.a96_weatherapplication.Data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Forecast(
    val imageID:Int=0,
    val textViewWeekDay:String = "Monday",
    val textViewForecast:String = "Sunny",
    val textViewFTempHight:String = "0°",
    val textViewFTempLow:String = "1°") :Parcelable




