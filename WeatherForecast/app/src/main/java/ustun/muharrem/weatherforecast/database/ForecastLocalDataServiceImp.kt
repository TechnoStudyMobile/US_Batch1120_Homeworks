package ustun.muharrem.weatherforecast.database

import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult

interface ForecastLocalDataServiceImp {
    suspend fun getSavedForecastContainer(): ForecastContainerResult
    suspend fun insert(forecastContainer: ForecastContainer)
}