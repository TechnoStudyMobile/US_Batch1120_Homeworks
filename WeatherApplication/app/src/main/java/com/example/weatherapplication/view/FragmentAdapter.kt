package com.example.weatherapplication.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import kotlinx.android.synthetic.main.element_in_fragment_activity.view.*
import kotlinx.android.synthetic.main.first_element_in_recycler_view.view.*

class FragmentAdapter(
        var listOfItems: Json4Kotlin_Base,
        var doOnClick: (Int) -> Unit
) : RecyclerView.Adapter<FragmentAdapter.ElementViewHolder>() {

    private val firstItem = 0
    private val otherItems = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        return if(viewType == firstItem) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.first_element_in_recycler_view, parent, false)
            ElementViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.element_in_fragment_activity, parent, false)
            ElementViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            firstItem
        } else otherItems
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
     when(holder.itemViewType) {
         0 -> {
             holder.itemView.image_view_cloud_in_first_element.setImageResource(R.drawable::class.java.getId(listOfItems.data.get(position).weather.icon))
             holder.itemView.text_view_day_in_first_element.text = listOfItems.data[position].datetime
             holder.itemView.text_view_day_forecast_in_first_element.text = listOfItems.data[position].weather.description
             holder.itemView.text_view_temperature_in_first_element.text = listOfItems.data[position].temp.toInt().toString()+ "°"
             holder.itemView.text_view_temperature_feels_like_in_first_element.text = listOfItems.data[position].low_temp.toInt().toString() + "°"
         }
         else -> {
             holder.itemView.image_view_cloud.setImageResource(R.drawable::class.java.getId(listOfItems.data[position].weather.icon))
             holder.itemView.text_view_day.text = listOfItems.data[position].datetime
             holder.itemView.text_view_day_forecast.text = listOfItems.data[position].weather.description
             holder.itemView.text_view_temperature.text = listOfItems.data[position].temp.toInt().toString() + "°"
             holder.itemView.text_view_temperature_feels_like.text = listOfItems.data[position].low_temp.toInt().toString() + "°"
         }
     }
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