package com.kryvovyaz.a96_weatherapplication.network

import com.kryvovyaz.a96_weatherapplication.ForecastViewModel.model.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET


interface ForecastService {
    @GET("daily?city=Pittsburgh&country=United%20States&state=Pennsylvania&key=99cdb4a2a77c490fa33ad918f21246c0")
    fun getForecast(): Call<Json4Kotlin_Base>
}
//https://api.weatherbit.io/v2.0/forecast/daily?city=Pittsburgh&country=United%20States&state=Pennsylvania&key=99cdb4a2a77c490fa33ad918f21246c0'