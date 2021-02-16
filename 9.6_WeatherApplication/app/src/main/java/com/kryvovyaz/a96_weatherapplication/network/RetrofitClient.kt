package com.kryvovyaz.a96_weatherapplication.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val BASE_URL = "https://api.weatherbit.io/v2.0/forecast/"
    var instance: Retrofit? = null
        get() {
            if (field == null) {
                Log.d("MyApp", "Retrofit is  null, new instance created")
                field = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory
                            .create()
                    ).build()
            } else Log.d("MyApp", "Retrofit is not null")
            return field
        }
}