package com.example.a96_weatherapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val BASE_URL = "https://api.weatherbit.io/v2.0/forecast/"
    var instance: Retrofit? = null
        /// any of this  will work
        get() {
            if (field == null) {
                field = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory
                            .create()
                    ).build()
            }
            return field
        }


}