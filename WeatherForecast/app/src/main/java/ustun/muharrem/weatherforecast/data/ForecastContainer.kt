package ustun.muharrem.weatherforecast.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

@Parcelize
@Entity(tableName = "forecastContainers")
data class ForecastContainer(
    @Expose(deserialize = false, serialize = false)
    @PrimaryKey
    val id: Int = 0,

    val data: List<Forecast>,
    val city_name: String,
    val lon: Double,
    val timezone: String,
    val lat: Double,
    val country_code: String,
    val state_code: String
) : Parcelable