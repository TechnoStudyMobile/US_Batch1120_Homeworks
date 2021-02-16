package com.example.weatherapplication.weatherData

import com.google.gson.annotations.SerializedName

data class Weather (
		@SerializedName("icon") val icon : String,
		@SerializedName("code") val code : Int,
		@SerializedName("description") val description : String
)