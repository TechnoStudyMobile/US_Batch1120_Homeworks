package com.crnkic.weatherapp.model

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
        val id: Int =0,
        @SerializedName("data") val forecastList: List<Forecast>,
        val city_name: String,
        val lon: Double,
        val timezone: String,
        val lat: Double,
        val country_code: String,
        val state_code: String,
) : Parcelable

@Parcelize
data class Forecast(
        val moonrise_ts: Int,
        val wind_cdir: String,
        @SerializedName("rh") val rh: Int,
        @SerializedName("pres") val pres: Double,
        val high_temp: Double,
        @SerializedName("sunset_ts") val sunset_ts: Int,
        @SerializedName("ozone") val ozone: Double,
        @SerializedName("moon_phase") val moon_phase: Double,
        @SerializedName("wind_gust_spd") val wind_gust_spd: Double,
        @SerializedName("snow_depth") val snow_depth: Double,
       val clouds: Int,
        @SerializedName("ts") val ts: Int,
        @SerializedName("sunrise_ts") val sunrise_ts: Int,
        @SerializedName("app_min_temp") val app_min_temp: Double,
        @SerializedName("wind_spd") val wind_spd: Double,
        @SerializedName("pop") val pop: Int,
        @SerializedName("wind_cdir_full") val wind_cdir_full: String,
        @SerializedName("slp") val slp: Double,
        @SerializedName("moon_phase_lunation") val moon_phase_lunation: Double,
        val valid_date: String,
        @SerializedName("app_max_temp") val app_max_temp: Double,
        @SerializedName("vis") val vis: Double,
        @SerializedName("dewpt") val dewpt: Double,
        val snow: Double,
        @SerializedName("uv") val uv: Double,
        val weather: Weather,
        @SerializedName("wind_dir") val wind_dir: Int,
        @SerializedName("max_dhi") val max_dhi: String,
        @SerializedName("clouds_hi") val clouds_hi: Int,
        @SerializedName("precip") val precip: Double,
        @SerializedName("low_temp") val low_temp: Double,
        @SerializedName("max_temp") val max_temp: Double,
        @SerializedName("moonset_ts") val moonset_ts: Int,
        @SerializedName("datetime") val datetime: String,
        @SerializedName("temp") val temp: Double,
        @SerializedName("min_temp") val min_temp: Double,
        @SerializedName("clouds_mid") val clouds_mid: Int,
        @SerializedName("clouds_low") val clouds_low: Int
) : Parcelable

@Parcelize
data class Weather(
        val icon: String,
        val code: Int,
        val description: String
) : Parcelable