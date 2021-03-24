package ustun.muharrem.weatherforecast.data

sealed class ForecastContainerResult{
    class Success(val forecastContainer: ForecastContainer) : ForecastContainerResult()
    class Failure(val error: Error) : ForecastContainerResult()
    object IsLoading : ForecastContainerResult()
}
