package com.samil.app.theweather.screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samil.app.theweather.WEATHER_API_KEY
import com.samil.app.theweather.database.ForecastContainerDao
import com.samil.app.theweather.database.WeatherDatabase
import com.samil.app.theweather.model.ForecastContainer
import com.samil.app.theweather.network.ForecastService
import com.samil.app.theweather.network.RetrofitClient
import com.samil.app.theweather.repository.ForecastContainerRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastListLiveData = MutableLiveData<ForecastContainer>()
    val forecastListLiveData: LiveData<ForecastContainer>
        get() = _forecastListLiveData

    fun getforecastContainer(isCelsius: Boolean) {

    }



}

class ForecastViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            val dao = WeatherDatabase.getDatabase(application).getForecastContainerDao()
            val repository = ForecastContainerRepository(dao)
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


/*
    TODO: Challenge
    TODO: Check if it`s been 30 min since last time
    1. First we should save last timestamp to SharedPref
    2. Retrieve sharedPref
    3. If it has been passed 30 mins or not

    fun checkIfBeenHalfHour(): Boolean {
        // retrieved saved timestamp from sharedPref
        val currentTime = System.currentTimeMillis()

        //Check if sharedPref time < currentTime
           // return true +save to sharedPref
        //if not
            // return false
        return true
    }
 */