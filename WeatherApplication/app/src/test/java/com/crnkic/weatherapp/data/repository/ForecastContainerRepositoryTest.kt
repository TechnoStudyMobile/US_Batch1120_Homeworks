package com.crnkic.weatherapp.data.repository

import com.crnkic.weatherapp.data.database.ForecastContainerDao
import com.crnkic.weatherapp.data.database.ForecastLocalDataSource
import com.crnkic.weatherapp.data.database.WeatherDatabase
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

class ForecastContainerRepositoryTest {

    val fakeforecastLocalDataSource = FakeForecastLocalDataSource()

    //Subject under test
    private lateinit var forecastContainerRepository: ForecastContainerRepository

    @Before
    fun setUp() {
        forecastContainerRepository = ForecastContainerRepository(fakeforecastLocalDataSource)

    }

    @Test
    fun fetchForecastContainer() {

    }

    @Test
    fun getSavedForecastContainer() {
    }
}