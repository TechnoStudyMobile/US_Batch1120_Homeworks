package com.kryvovyaz.a96_weatherapplication.data.repository

import com.example.a96_weatherapplication.data.database.ForecastLocalDataSourceImp
import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.util.DB_IS_EMPTY_ERROR_MASSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Error

class FakeForecastLocalDataSource(val forecastContainerList: MutableList<ForecastContainer> = mutableListOf()) :
    ForecastLocalDataSourceImp {

    override suspend fun getSavedForecastContainer(): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val forecastContainer = forecastContainerList.getOrNull(0)
            forecastContainer?.let {
                return@withContext ForecastContainerResult.Success(forecastContainer)
            }
        } ?: run withContext@{
            return@withContext ForecastContainerResult.Failure(Error(DB_IS_EMPTY_ERROR_MASSAGE))
        }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        forecastContainerList.clear()
    }

    override suspend fun insert(forecastContainer: ForecastContainer) {
        withContext(Dispatchers.IO) {
            forecastContainerList.add(forecastContainer)
        }
    }
}