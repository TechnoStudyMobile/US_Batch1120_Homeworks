package com.kryvovyaz.a96_weatherapplication.model

import com.google.gson.annotations.SerializedName

data class Json4Kotlin_Base(
    val data: List<Data>,
    val city_name: String,
    @SerializedName("lon") val lon: Double,
    val timezone: String,
    @SerializedName("lat") val lat: Double,
    val country_code: String,
    val state_code: String
)