package com.kryvovyaz.a96_weatherapplication.data.repository

import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import java.lang.Error

sealed class ForecastContainerResult {
    class Success(val forecastContainer: ForecastContainer) : ForecastContainerResult(){

    }
    class Failure(val error: Error) : ForecastContainerResult(){

    }
    object IsLoading : ForecastContainerResult(){

    }
}