package com.example.myphotos_kotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val BASE_URL = "https://jsonplaceholder.typicode.com"
    var instance: Retrofit? = null
        /// any of this  will work
        get() {
            if (field == null) {
                field = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory
                            .create()
                    ).build()
            }
            return field
        }

    /// any of this  will work
    fun getInstanceOfRetrofit(): Retrofit? {
        if (instance == null) {
            instance = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return instance
    }

}

class RetrofitClientWithComp {
    companion object {
        const val BASE_URL = "http://jsonplaceholder.typeicode.com"
        var retrofit: Retrofit? = null

        fun getInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(RetrofitClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }

    }
}