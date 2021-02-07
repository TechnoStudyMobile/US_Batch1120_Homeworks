package com.example.a55myphotosretrofit_kotlinversion.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a55myphotosretrofit_kotlinversion.R
import com.example.a55myphotosretrofit_kotlinversion.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_view_holder.view.*

class PhotoAdapter(var listPhoto: List<Photo>, var whatToDoOnClicks: (Int) -> Unit) :
        RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.photo_view_holder, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPhoto.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Picasso.get().load(listPhoto[position].thumbnailUrl).into(holder.itemView.image_View_image);
        holder.itemView.text_view_text.text = listPhoto[position].title
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var photoImageView = itemView.image_View_image
//        var titleTextView = itemView.text_view_text

        init {
            itemView.setOnClickListener { whatToDoOnClicks(adapterPosition) }

        }

    }
}