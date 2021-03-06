package com.kryvovyaz.a96_weatherapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName ="" )
data class Forecast(
    val moonrise_ts: Int,
    @SerializedName("wind_cdir") val abdreviatedWindDirection: String,
    @SerializedName("rh") val humidityAverage: Int,
    @SerializedName("pres") val pressureAverage: Double,
    val high_temp: Double,
    @SerializedName("sunset_ts") val sunset_time_unix_timestamps: Int,
    @SerializedName("ozone") val ozoneAverage: Double,
    val moon_phase: Double,
    val wind_gust_spd: Double,
    val snow_depth: Double,
    val clouds: Int,
    @SerializedName("ts") val forecast_period_start_unix_timestamp: Int,
    @SerializedName("sunrise_ts") val sunrise_time_unix_timestamps: Int,
    val app_min_temp: Double,
    val wind_spd: Double,
    @SerializedName("pop") val probability: Int,
    @SerializedName("wind_cdir_full") val verbalWindDirection: String,
    @SerializedName("slp") val seaLevelPresure: Double,
    val moon_phase_lunation: Double,
    val valid_date: String,
    val app_max_temp: Double,
    @SerializedName("vis") val visibility: Double,
    @SerializedName("dewpt") val dewPointAverage: Double,
    val snow: Double,
    @SerializedName("uv") val maximunUVindex: Double,
   //
    val weather: Weather,
    @SerializedName("wind_dir") val windDirection: Int,
    @SerializedName("max_dhi") val max_dhi: String,
    val clouds_hi: Int,
    @SerializedName("precip") val accumulatedLiquidEquivalentPrecipitation: Double,
    val low_temp: Double,
    val max_temp: Double,
    @SerializedName("moonset_ts") val moonset_time_stamp: Int,
    val datetime: String,
    val temp: Double,
    val min_temp: Double,
    val clouds_mid: Int,
    val clouds_low: Int
):Parcelable