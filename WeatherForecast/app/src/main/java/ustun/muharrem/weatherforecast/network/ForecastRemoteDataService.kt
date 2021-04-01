package ustun.muharrem.weatherforecast.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.utilities.*
import java.lang.Error
import java.lang.Exception

object ForecastRemoteDataService {

    suspend fun fetchForecastContainer(): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
            val langCode = SharedPrefs.langCode
            val units =
                if (SharedPrefs.isCelsius) METRIC_QUERY_PARAM_VALUE else IMPERIAL_QUERY_PARAM_VALUE
            val lat = SharedPrefs.lat
            val lon = SharedPrefs.lon
            val callForecast =
                getDataService?.getForecast(langCode!!, units, lat, lon, API_KEY)

            try {
                val response = callForecast?.execute()
                val forecastContainer = response?.body()
                return@withContext forecastContainer?.let {
                    ForecastContainerResult.Success(it)
                } ?: run {
                    ForecastContainerResult.Failure(Error(RESPONSE_PARSING_ERROR_MESSAGE))
                }

// TODO: Handle the following in the repository
// SharedPrefs.lastEpochTime = System.currentTimeMillis()
// dao.insert(it)

            } catch (ex: Exception) {
                ex.localizedMessage?.let { Log.d("MyApp", it) }
                return@withContext ForecastContainerResult.Failure(Error(ex.message))
            }
        }
}