package com.crnkic.weatherapp.data.model

sealed class ForecastContainerResult {

    class Success(var forecastContainer : ForecastContainer) : ForecastContainerResult()
    class Failure(val error: Error) : ForecastContainerResult()
    object IsLoading : ForecastContainerResult()
}
