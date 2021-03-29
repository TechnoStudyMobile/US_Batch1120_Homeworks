package com.crnkic.weatherapp.data.repository

import com.DB_IS_EMPTY_ERROR_MESSAGE
import com.crnkic.weatherapp.data.database.ForecastContainerDao
import com.crnkic.weatherapp.data.database.ForecastLocalDataSourceImp
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeForecastLocalDataSource(private val forecastContainerList: MutableList<ForecastContainer> = mutableListOf()) : ForecastLocalDataSourceImp {


    override suspend fun getSavedForecastContainer(): ForecastContainerResult =
            withContext(Dispatchers.IO) {
                val forecastContainer = forecastContainerList.getOrNull(0)
                forecastContainer?.let {
                    return@withContext ForecastContainerResult.Success(forecastContainer)
                } ?: run {
                    return@withContext ForecastContainerResult.Failure(Error(DB_IS_EMPTY_ERROR_MESSAGE))
                }
            }

    override suspend fun insert(forecastContainer: ForecastContainer) {
            withContext(Dispatchers.IO) {
                forecastContainerList.add(forecastContainer)
            }
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        forecastContainerList.clear()
    }

}