package com.kryvovyaz.a96_weatherapplication.model

data class Forecast(
    val data: List<Data>,
    val city_name: String,
    val lon: Double,
    val timezone: String,
    val lat: Double,
    val country_code: String,
    val state_code: String
)