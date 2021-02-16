package com.example.a55myphotosretrofit_kotlinversion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a55myphotosretrofit_kotlinversion.model.Photo
import com.example.a55myphotosretrofit_kotlinversion.network.GetDataService
import com.example.a55myphotosretrofit_kotlinversion.network.RetrofitClient
import com.example.a55myphotosretrofit_kotlinversion.view.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val callPhotos: Call<List<Photo>>? = getDataService?.getPhotos()

        callPhotos?.enqueue(object : Callback<List<Photo>> {

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                response.body()?.let { callPhotos ->
                    for (photo in callPhotos) {

                    }
                    createRecycler(callPhotos)
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {

            }
        })
    }


    fun createRecycler(photos: List<Photo>) {
        val adapter = PhotoAdapter((photos)) { adapterPosition ->
            Toast.makeText(this, "Item in $adapterPosition is clicked", Toast.LENGTH_SHORT).show()

        }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }
}

