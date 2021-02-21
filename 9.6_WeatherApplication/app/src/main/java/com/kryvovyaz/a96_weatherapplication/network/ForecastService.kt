package com.kryvovyaz.a96_weatherapplication.network

import com.kryvovyaz.a96_weatherapplication.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("daily")
    fun getForecast(
        @Query("days") days: String,
        @Query("lat") lat: String,
        @Query("Lon") lon: String,
        @Query("key") key: String,
    ): Call<Forecast>
}