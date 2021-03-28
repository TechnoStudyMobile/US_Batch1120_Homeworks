package com.kryvovyaz.a96_weatherapplication.data.repository

import org.junit.Before

class ForecastContainerRepositoryTest {
    //subject under test
    private lateinit var forecastContainerRepository:ForecastContainerRepository
    val fakeForecastLocalDataSource=FakeForecastLocalDataSource()


    @Before
    fun setUp() {
        //forecastContainerRepository=ForecastContainerRepository(fakeForecastLocalDataSource)
    }

    @Before
    fun fetchForecastContainer(){

    }

    @Before
    fun getSavedForecastContainer(){

    }
}