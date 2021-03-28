package com.kryvovyaz.a96_weatherapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "forecastContainerTable")
data class ForecastContainer(
    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = false)
    val id: Int=0,
    @SerializedName("data")
    val forecastList: List<Forecast>,
    val city_name: String,
    val lon: Double,
    val timezone: String,
    val lat: Double,
    val country_code: String,
    val state_code: String
) : Parcelable