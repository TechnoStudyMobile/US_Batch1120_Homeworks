package com.samil.app.theweather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https:api.weatherbit.io/v2.0/forecast/"
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }
        private set
}