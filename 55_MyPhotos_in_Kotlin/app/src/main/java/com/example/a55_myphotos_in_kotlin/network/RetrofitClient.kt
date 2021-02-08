package com.example.a55_myphotos_in_kotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    //Furkan`s preference
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }

//    fun getInstanceOfRetrofit(): Retrofit? {
//        if (retrofit == null){
//            retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit
//    }
//}
//
//class RetrofitClientWithComp {
//    companion object{
//        const val BASE_URL = "https://jsonplaceholder.typicode.com"
//        var retrofit: Retrofit? = null
//
//        fun getInstance(): Retrofit? {
//            if (retrofit == null){
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return retrofit
//        }
//    }
}