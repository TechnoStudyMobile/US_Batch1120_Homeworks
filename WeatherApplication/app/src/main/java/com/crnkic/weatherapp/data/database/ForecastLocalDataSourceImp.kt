package com.crnkic.weatherapp.data.database

import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult

interface ForecastLocalDataSourceImp {
    suspend fun getSavedForecastContainer() : ForecastContainerResult
    suspend fun insert(forecastContainer : ForecastContainer)
    suspend fun deleteAll()
}