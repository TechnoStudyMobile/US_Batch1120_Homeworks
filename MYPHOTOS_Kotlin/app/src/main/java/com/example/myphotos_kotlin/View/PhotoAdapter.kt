package com.example.myphotos_kotlin.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotos_kotlin.R
import com.example.myphotos_kotlin.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_view_holder.view.*

class PhotoAdapter(val list: List<Photo>, val whattoDoOnClicks: (Int) -> Unit) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_view_holder, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.textViewHolder.text = list[position].title
        Picasso.get().load(list.get(position).url).into(holder.imageViewHolder)
        holder.itemView.setOnClickListener { whattoDoOnClicks(position) }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHolder = itemView.findViewById<TextView>(R.id.text)
        val imageViewHolder = itemView.findViewById<ImageView>(R.id.image)

    }
}