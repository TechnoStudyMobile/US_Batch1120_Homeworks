package ustun.muharrem.weatherforecast.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class DummyForecast(
    val high_temp: Double,
    val low_temp: Double,
    val valid_date: String,
//    val forecastIcon: Int,
    val weatherDescription: String
) : Parcelable