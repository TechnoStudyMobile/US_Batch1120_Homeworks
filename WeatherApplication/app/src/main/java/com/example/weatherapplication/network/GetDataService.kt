package com.example.weatherapplication.network

import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {

    @GET("daily?city=Pittsburgh&country=United%20States&state=Pennsylvania&key=69c331eb3c2c440382c93901d4be55bd")
    fun getWeatherData(): Call<Json4Kotlin_Base>


//    @GET("/photos")
//    fun getWeatherData(): Call<List<com.example.weatherapplication.data.WeatherData.Weather>>

//    @GET("(/current)")
//    fun getWeatherData(@Query("Harrisburg") city : String,
//                       @Query("US") country :String,
//                       @Query("69c331eb3c2c440382c93901d4be55bd") key : String
//    ) : Call<List<com.example.weatherapplication.data.WeatherData.Weather>>

    //(/daily?city=Raleigh,NC&key=69c331eb3c2c440382c93901d4be55bd)


}