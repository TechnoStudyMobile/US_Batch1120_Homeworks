package ustun.muharrem.weatherforecast.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.database.ForecastLocalDataServiceImp
import ustun.muharrem.weatherforecast.utilities.DB_IS_EMPTY_MESSAGE
import java.lang.Error

class FakeForecastLocalDataService(val forecastContainerList: MutableList<ForecastContainer> = mutableListOf()) :
    ForecastLocalDataServiceImp {

    override suspend fun getSavedForecastContainer(): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val forecastContainer = forecastContainerList.getOrNull(0)
            return@withContext forecastContainer?.let {
                ForecastContainerResult.Success(it)
            } ?: run {
                ForecastContainerResult.Failure(Error(DB_IS_EMPTY_MESSAGE))
            }
        }

    override suspend fun insert(forecastContainer: ForecastContainer) {
        withContext(Dispatchers.IO) {
            // List must be cleared to mimic "onConflict = OnConflictStrategy.REPLACE" action
            forecastContainerList.clear()
            forecastContainerList.add(forecastContainer)
        }
    }
}