package com.example.a55_myphotos_in_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a55_myphotos_in_kotlin.model.Photo
import com.example.a55_myphotos_in_kotlin.network.PhotosService
import com.example.a55_myphotos_in_kotlin.network.RetrofitClient
import com.example.a55_myphotos_in_kotlin.view.PhotoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)

        val getPhotosService =
            RetrofitClient.retrofit?.create(PhotosService::class.java)

        getPhotosService?.getPhotos()?.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
                val photoListFromResponse = response.body()
                if (photoListFromResponse != null) {
                    photoListFromResponse.forEach {
                        Log.i("Samil", "${it.albumId} && ${it.thumbnailUrl}")
                    }
                    createRecycler(photoListFromResponse)
                }
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Houston we have a problem",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun createRecycler(photoList: MutableList<Photo>) {
        val layoutManager = LinearLayoutManager(this)
        val adapter = PhotoAdapter(photoList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}