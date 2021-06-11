package com.crnkic.weatherapp.data.repository

import com.DB_IS_EMPTY_ERROR_MESSAGE
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ForecastContainerRepositoryTest {
    //Subject under test
    private lateinit var forecastContainerRepository: ForecastContainerRepository
    private lateinit var fakeForecastLocalDataSource: FakeForecastLocalDataSource


    @Before
    fun setUp() {
        fakeForecastLocalDataSource = FakeForecastLocalDataSource()
        forecastContainerRepository = ForecastContainerRepository(fakeForecastLocalDataSource)

    }

    @Test
    fun fetchForecastContainer() {

    }

    @Test
    fun getSavedForecastContainer_whenDBIsEmpty_returnResultFailure() {
        runBlockingTest {
            //Given

            //When
            val result = forecastContainerRepository.getSavedForecastContainer() as ForecastContainerResult.Failure

            //Then
            Assert.assertThat(
                result.error.message, `is` (DB_IS_EMPTY_ERROR_MESSAGE))

        }
    }

    @Test
    fun getSavedForecastContainer_whenDBNotEmpty_returnResultSuccess() {
        runBlockingTest {
            //Given
            val forecastContainer = ForecastContainer(0, mutableListOf(), "Toronto", 123.0, "UCT-4", 123.0, "CA","ON")
            fakeForecastLocalDataSource.insert(forecastContainer)
            //When
            val result = forecastContainerRepository.getSavedForecastContainer() as ForecastContainerResult.Success

            //Then
            Assert.assertThat(result.forecastContainer, `is` (forecastContainer))

        }
    }
}