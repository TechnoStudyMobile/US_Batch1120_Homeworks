package com.kryvovyaz.a96_weatherapplication.data.network

import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("daily")
    fun getForecast(
        @Query("days") days: Int,
        @Query("lat") lat: String,
        @Query("Lon") lon: String,
        @Query("units") units: String,
        @Query("key") key: String,
      ): Call<ForecastContainer>
}