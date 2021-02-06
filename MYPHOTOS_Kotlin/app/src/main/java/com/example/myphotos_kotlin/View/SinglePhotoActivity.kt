package com.example.myphotos_kotlin.View

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myphotos_kotlin.MainActivity
import com.example.myphotos_kotlin.R
import com.example.myphotos_kotlin.model.Photo
import com.squareup.picasso.Picasso

class SinglePhotoActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var photo: Photo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_photo)
        textView = findViewById(R.id.single)
        imageView = findViewById(R.id.single_image)
        val intenet = intent

        var photo = intenet.getParcelableExtra<Photo>(MainActivity.KEY_PHOTO_BUNDLE)
        //Implement object from Bundle
        textView.text = photo?.title


        if (photo != null) {
            Picasso.get()

                .load(photo.url)
                .into(imageView)
        }

    }
}