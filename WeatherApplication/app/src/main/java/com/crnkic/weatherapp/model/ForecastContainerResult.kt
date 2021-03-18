package com.crnkic.weatherapp.model

sealed class ForecastContainerResult {

    class Success(var forecastContainer : ForecastContainer) : ForecastContainerResult()
    class Failure(val error: Error) : ForecastContainerResult()
    object IsLoading : ForecastContainerResult()
}
