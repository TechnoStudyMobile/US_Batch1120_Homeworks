package com.samil.app.theweather.network

import com.samil.app.theweather.model.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    //https://api.weatherbit.io/v2.0/forecast/daily?days=14&&lat=38.123&lon=-78.543&key=1ba5a02c727f4bb6aea6d04552549da6

    //?days=14&&lat=38.123&lon=-78.543&key=1ba5a02c727f4bb6aea6d04552549da6
    @GET("daily")
    fun getForecast(
        @Query("days") days: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("key") key: String
    ): Call<ForecastResponse>
}