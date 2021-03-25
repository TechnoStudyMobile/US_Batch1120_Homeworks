package ustun.muharrem.weatherforecast.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Forecast(
    val valid_date: String,
    val ts: Int,
    val datetime: String,
    val wind_gust_spd: Double,
    val wind_spd: Double,
    val wind_dir: Int,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val temp: Double,
    val max_temp: Double,
    val min_temp: Double,
    val high_temp: Double,
    val low_temp: Double,
    val app_max_temp: Double,
    val app_min_temp: Double,
    val pop: Double,
    val precip: Double,
    val snow: Double,
    val snow_depth: Double,
    val slp: Double,
    val pres: Double,
    val dewpt: Double,
    val rh: Double,
    val weather: Weather,
    val pod: String,
    val clouds_low: Int,
    val clouds_mid: Int,
    val clouds_hi: Int,
    val clouds: Int,
    val vis: Double,
    val max_dhi: Int,
    val uv: Double,
    val moon_phase: Double,
    val moon_phase_lunation: Double,
    val moonrise_ts: Long,
    val moonset_ts: Long,
    val sunrise_ts: Long,
    val sunset_ts: Long,
) : Parcelable