package com.example.myphotoskotlinversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoskotlinversion.model.Photo
import com.example.myphotoskotlinversion.network.GetDataService
import com.example.myphotoskotlinversion.network.RetrofitClient
import com.example.myphotoskotlinversion.views.PhotoListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_View)

        val getDataService = RetrofitClient.instance?.create(GetDataService::class.java)
        val callPhotos = getDataService?.getPhotos()
        callPhotos?.enqueue(object: Callback<MutableList<Photo>>{
            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                t.stackTrace
            }

            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
             val photoList = response.body()
                if (photoList != null) {
                    for(photo in photoList){
                        Log.d("MyPhotos", "My photo: ${photo.title}")
                    }
                    createRecyclerView(photoList)
                }
            }

        })
    }

    fun createRecyclerView(photoList: MutableList<Photo>){
        val layoutManager = LinearLayoutManager(this)
        val adapter = PhotoListAdapter(photoList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}