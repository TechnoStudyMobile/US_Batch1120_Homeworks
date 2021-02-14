package com.example.a96_weatherapplication.network

import com.example.a96_weatherapplication.Data.Forecast
import retrofit2.Call
import retrofit2.http.GET

interface ForecastService {
    @GET("daily?postal_code=15237&key=710be5b3d0504715b7bd821410dacce2")
    fun getForecast(): Call<List<Forecast>>
}