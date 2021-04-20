package ustun.muharrem.weatherforecast.repository

import androidx.lifecycle.MutableLiveData
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.database.ForecastContainerDao
import ustun.muharrem.weatherforecast.database.ForecastLocalDataService
import ustun.muharrem.weatherforecast.database.ForecastLocalDataServiceImp
import ustun.muharrem.weatherforecast.network.ForecastRemoteDataService
import ustun.muharrem.weatherforecast.utilities.*

class ForecastContainerRepository(private val forecastLocalDataService : ForecastLocalDataServiceImp) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()

    suspend fun fetchForecastContainer() {
        val forecastContainerResult = ForecastRemoteDataService.fetchForecastContainer()
        forecastContainerResultLiveData.postValue(forecastContainerResult)
        if (forecastContainerResult is ForecastContainerResult.Success) {
            forecastLocalDataService.insert(forecastContainerResult.forecastContainer)
            SharedPrefs.lastEpochTime = System.currentTimeMillis()
        }
    }

    suspend fun getSavedForecastContainer() {
        val forecastContainerResult = forecastLocalDataService.getSavedForecastContainer()
        forecastContainerResultLiveData.postValue(forecastContainerResult)
    }
}