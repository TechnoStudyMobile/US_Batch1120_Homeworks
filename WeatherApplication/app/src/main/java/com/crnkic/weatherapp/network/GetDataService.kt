package com.crnkic.weatherapplication.network

import com.crnkic.weatherapplication.weatherData.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("daily?city=Harrisburg&country=United%20States&state=Pennsylvania&key=69c331eb3c2c440382c93901d4be55bd")
    fun getWeatherData(): Call<Json4Kotlin_Base>

}