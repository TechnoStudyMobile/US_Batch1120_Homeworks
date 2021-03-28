package com.kryvovyaz.a96_weatherapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.kryvovyaz.a96_weatherapplication.App
import com.kryvovyaz.a96_weatherapplication.data.database.ForecastLocalDataSource
import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.data.network.ForecastRemoteDataSource

class ForecastContainerRepository(private val localDataSource :ForecastLocalDataSource) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()
//TODO :remove parameters from fun
    suspend fun fetchForecastContainer(isCelsius: Boolean, days: Int, lat: String, long: String) {
        val forecastContainerResult = App.prefs!!.lattitude?.let {
            App.prefs!!.longtitude?.let { it1 ->
                ForecastRemoteDataSource.fetchForecastContainer(
                    App.prefs!!.icCelsius,
                    App.prefs!!.days, it, it1
                )
            }
        }
        forecastContainerResultLiveData
            .postValue(forecastContainerResult!!)
        if (forecastContainerResult is ForecastContainerResult.Success) {
            insertToDatabase(forecastContainerResult.forecastContainer)
        }
    }

    suspend fun getSavedForecastContainer() {
        val forecastContainerResult = localDataSource.getSavedForecastContainer()
        forecastContainerResultLiveData.postValue(forecastContainerResult)
    }

    private suspend fun insertToDatabase(forecastContainer: ForecastContainer) {
        localDataSource.deleteAll()
        localDataSource.insert(forecastContainer)
    }
}