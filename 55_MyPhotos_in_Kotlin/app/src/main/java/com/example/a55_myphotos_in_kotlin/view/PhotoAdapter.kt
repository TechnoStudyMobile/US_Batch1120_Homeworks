package com.example.a55_myphotos_in_kotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a55_myphotos_in_kotlin.R
import com.example.a55_myphotos_in_kotlin.model.Photo
import com.squareup.picasso.Picasso

class PhotoAdapter(val listPhoto: MutableList<Photo>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_view_holder, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.textView.text = listPhoto[position].title
        Picasso.get().load(listPhoto[position].url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return listPhoto.size
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.image)
        var textView: TextView = itemView.findViewById<TextView>(R.id.text)
    }
}

