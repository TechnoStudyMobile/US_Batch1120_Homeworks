package com.example.weatherapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import kotlinx.android.synthetic.main.element_in_fragment_activity.view.*

class FragmentAdapter(
        var listOfItems: Json4Kotlin_Base,
        var doOnClick: (Int) -> Unit
) : RecyclerView.Adapter<FragmentAdapter.ElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.element_in_fragment_activity, parent, false)
        return ElementViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        holder.itemView.image_view_cloud.setImageResource(R.drawable::class.java.getId(listOfItems.data.get(position).weather.icon))
        holder.itemView.text_view_day.text = listOfItems.data[position].datetime
        holder.itemView.text_view_day_forecast.text = listOfItems.data[position].weather.description
        holder.itemView.text_view_temperature.text = listOfItems.data[position].temp.toString()
        holder.itemView.text_view_temperature_feels_like.text = listOfItems.data[position].low_temp.toString()
    }

    override fun getItemCount(): Int {
        return listOfItems.data.size
    }

    inner class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { doOnClick(adapterPosition) }
        }

    }

    inline fun <reified T : Class<R.drawable>> T.getId(resourceName: String): Int {
        return try {
            val idField = getDeclaredField(resourceName)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}