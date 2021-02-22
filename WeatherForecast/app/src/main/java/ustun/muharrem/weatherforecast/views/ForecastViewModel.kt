package ustun.muharrem.weatherforecast.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ustun.muharrem.weatherforecast.data.DummyForecast
import kotlin.math.round
import kotlin.random.Random

class ForecastViewModel : ViewModel() {

    private val _forecastListLiveData: MutableLiveData<MutableList<DummyForecast>> =
        MutableLiveData()
    val forecastListLiveData: LiveData<MutableList<DummyForecast>>
        get() = _forecastListLiveData

    fun setForecastListLiveData() {
        val mutableListOfDummyForecast = mutableListOf<DummyForecast>()
        val valid_date = "2017-02-16"
        val weatherDescription = "Thunderstorm with light rain"

        for (i in 1..16) {
            val high_temp = round(Random.nextDouble(35.0, 120.0) * 10) / 10
            val low_temp = round(Random.nextDouble(-23.0, 35.0) * 10) / 10

            val dummyForecast = DummyForecast(high_temp, low_temp, valid_date, weatherDescription)
            mutableListOfDummyForecast.add(dummyForecast)
        }
        _forecastListLiveData.value = mutableListOfDummyForecast
    }
}