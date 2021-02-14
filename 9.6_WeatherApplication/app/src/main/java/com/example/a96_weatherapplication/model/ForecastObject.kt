package com.example.a96_weatherapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Forecast(
    val country_code: String?,
    val city_name: String? ,
    val data: List<DataItem?>?,
    val timezone: String?,
    val lon: String?,
    val state_code: String?,
    val lat: String?
) : Parcelable

@Parcelize
data class Weather(
    val code: Int? ,
    val icon: String?,
    val description: String?
) : Parcelable

@Parcelize
data class DataItem(

    val pres: Double?,//presure
    val moon_phase: Double?,
    val wind_cdir: String?,
    val moonrise_ts: Int?,
    val clouds: Int?,
    val low_temp: Double?,
    val wind_spd: Double?,
    val ozone: Double?,
    val pop: Int?,
    val valid_ate: String?,////////////date
    val datetime: String?,
    val precip: Double?,
    val sunrise_ts: Int?,
    val min_temp: Double?,////////////////min Temp
    val weather: Weather?,
    val app_max_temp: Double?,
    val max_temp: Int?,/////////////////max temp
    val snow_epth: Double?,
    val sunset_ts: Int?,
    //val max_dhi: Any? = null,
    val clouds_mid: Int?,
    val vis: Double?,
    val uv: Double?,
    val highT_temp: Int?,
    val temp: Double?,
    val clouds_hi: Int?,
    val app_min_temp: Double?,
    val moon_phase_Lunation: Double?,
    val dewpt: Double?,
    val wind_dir: Int?,
    val wind_gust_spd: Double?,
    val clouds_low: Int?,
    val rh: Int?,
    val slp: Int?,
    val snow: Int?,
    val wind_cdir_full: String?,
    val moonset_ts: Int?,
    val ts: Int?
) : Parcelable
