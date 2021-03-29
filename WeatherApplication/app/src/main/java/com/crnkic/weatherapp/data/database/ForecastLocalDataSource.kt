package com.crnkic.weatherapp.data.database

import com.DB_IS_EMPTY_ERROR_MESSAGE
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ForecastLocalDataSource(val dao: ForecastContainerDao) : ForecastLocalDataSourceImp {

    override suspend fun getSavedForecastContainer() : ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val forecastContainer = dao.getForecastContainer()
            forecastContainer?.let {
               return@withContext ForecastContainerResult.Success(forecastContainer)
            } ?: run {
                return@withContext ForecastContainerResult.Failure(Error(DB_IS_EMPTY_ERROR_MESSAGE))
            }
        }

    override suspend fun insert(forecastContainer : ForecastContainer) = withContext(Dispatchers.IO){
        dao.insert(forecastContainer)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO){
        dao.deleteAll()
    }

}