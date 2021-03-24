package ustun.muharrem.weatherforecast.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.database.ForecastContainerDao
import ustun.muharrem.weatherforecast.network.GetDataService
import ustun.muharrem.weatherforecast.network.RetrofitClient
import ustun.muharrem.weatherforecast.utilities.*
import java.lang.Error
import java.lang.Exception

class ForecastContainerRepository(private val dao: ForecastContainerDao) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()

    private suspend fun getSavedForecastContainer() {
//        withContext(Dispatchers.IO) {
            //What happens if the app runs for the very first time???
            val forecastContainer = dao.getForecastContainer()
            forecastContainer?.let {
                withContext(Dispatchers.Main) {
                    forecastContainerResultLiveData.value =
                        ForecastContainerResult.Success(forecastContainer)
                }
            }
//        }
    }

    // TODO: Remove suspend after taking the logic out of the database call
    suspend fun getForecastContainer() {
        withContext(Dispatchers.IO) {
            if (System.currentTimeMillis() - SharedPrefs.lastEpochTime > THREE_HOUR_EPOCH_TIME) {
                fetchForecastContainer()
            } else {
               getSavedForecastContainer()
            }
        }
    }

//    private suspend fun timePassed(): Boolean {
//        var forecastEpochBefore: Long
//        withContext(Dispatchers.IO) {
//            forecastEpochBefore = dao.getForecastEpoch()
//        }
//        return System.currentTimeMillis() - forecastEpochBefore > THREE_HOUR_EPOCH_TIME
//    }

//    private fun insertToDatabase(forecastContainer: ForecastContainer) {
//        dao.insert(forecastContainer)
//        dao.updateForecastEpoch(System.currentTimeMillis())
//        dao.updateIsCelsius(SharedPrefs.isCelsius)
//    }

    suspend fun fetchForecastContainer() {
        withContext(Dispatchers.IO) {
            val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
            val langCode = SharedPrefs.langCode
            val units =
                if (SharedPrefs.isCelsius) METRIC_QUERY_PARAM_VALUE else IMPERIAL_QUERY_PARAM_VALUE
            val callForecast =
                getDataService?.getForecast(langCode!!, units, 43026, API_KEY)

            try {
                val response = callForecast?.execute()
                val forecastContainer = response?.body()
                forecastContainer?.let {
                    withContext(Dispatchers.Main) {
                        forecastContainerResultLiveData.value = ForecastContainerResult.Success(it)
                    }
                    SharedPrefs.lastEpochTime = System.currentTimeMillis()
                    dao.updateForecastEpoch(System.currentTimeMillis())
                    dao.insert(it)
                }
                // TODO: Handle if forecastContainer is null
            } catch (ex: Exception) {
                ex.localizedMessage?.let { Log.d("MyApp", it) }
                ForecastContainerResult.Failure(Error(ex.message))
            }
        }
    }
}