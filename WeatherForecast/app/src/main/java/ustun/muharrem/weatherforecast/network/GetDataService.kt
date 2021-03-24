package ustun.muharrem.weatherforecast.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ustun.muharrem.weatherforecast.data.ForecastContainer

interface GetDataService {

    @GET("daily")
    fun getForecast(
        @Query("lang") lang: String,
        @Query("units") units: String,
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
        @Query("postal_code") postalCode: Int,
        @Query("key") key: String
    ): Call<ForecastContainer>
}