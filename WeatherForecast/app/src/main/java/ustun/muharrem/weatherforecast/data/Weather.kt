package ustun.muharrem.weatherforecast.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Weather(
    val icon: String,
    val code: Int,
    val description: String
) : Parcelable