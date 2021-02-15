package com.example.myphotoskotlinversion.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoskotlinversion.R
import com.example.myphotoskotlinversion.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_view_item.view.*

class PhotoListAdapter(
    val photoList: MutableList<Photo>
) : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImage: ImageView = itemView.findViewById(R.id.photo_image)
        val photoTitle: TextView = itemView.findViewById(R.id.photo_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_view_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.photoTitle.text = photoList[position].title
        Picasso.get().load(photoList[position].url).into(holder.photoImage)
    }


}