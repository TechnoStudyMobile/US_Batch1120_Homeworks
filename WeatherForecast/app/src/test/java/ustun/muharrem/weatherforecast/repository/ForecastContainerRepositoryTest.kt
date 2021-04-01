package ustun.muharrem.weatherforecast.repository

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ForecastContainerRepositoryTest {

    private val fakeForecastLocalDataService = FakeForecastLocalDataService()

    // Subject Under Test
    private lateinit var forecastContainerRepository: ForecastContainerRepository

    @Before
    fun setUp(){
        forecastContainerRepository = ForecastContainerRepository(fakeForecastLocalDataService)
    }

    @Test
    fun fetchForecastContainer() {

    }

    @Test
    fun getForecastContainer() {
    }
}