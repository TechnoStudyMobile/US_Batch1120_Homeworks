package ustun.muharrem.weatherforecast.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.utilities.DB_IS_EMPTY_MESSAGE
import java.lang.Error

class ForecastLocalDataService(private val dao: ForecastContainerDao) :
    ForecastLocalDataServiceImp {

    override suspend fun getSavedForecastContainer(): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val forecastContainer = dao.getForecastContainer()
            return@withContext forecastContainer?.let {
                ForecastContainerResult.Success(it)
            } ?: run {
                ForecastContainerResult.Failure(Error(DB_IS_EMPTY_MESSAGE))
            }
        }

    override suspend fun insert(forecastContainer: ForecastContainer) =
        withContext(Dispatchers.IO) {
            dao.insert(forecastContainer)
        }
}