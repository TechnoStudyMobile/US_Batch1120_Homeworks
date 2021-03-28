package com.example.a96_weatherapplication.data.database

import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerResult


interface ForecastLocalDataSourceImp {
    suspend fun getSavedForecastContainer(): ForecastContainerResult
    suspend fun insert(forecastContainer: ForecastContainer)
    suspend fun deleteAll()
}