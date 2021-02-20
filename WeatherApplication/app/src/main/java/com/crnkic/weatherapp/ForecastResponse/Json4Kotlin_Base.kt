package com.crnkic.weatherapp.ForecastResponse

import com.crnkic.weatherapp.data.weatherData.Forecast
import com.google.gson.annotations.SerializedName

data class Json4Kotlin_Base(
        @SerializedName("data") val forecastList: List<Forecast>,
        @SerializedName("city_name") val city_name: String,
        @SerializedName("lon") val lon: Double,
        @SerializedName("timezone") val timezone: String,
        @SerializedName("lat") val lat: Double,
        @SerializedName("country_code") val country_code: String,
        @SerializedName("state_code") val state_code: String,

        val title: String
)