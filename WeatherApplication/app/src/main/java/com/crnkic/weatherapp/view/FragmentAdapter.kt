package com.crnkic.weatherapplication.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crnkic.weatherapplication.R
import com.crnkic.weatherapplication.util.DrawableUtil.getImageId
import com.crnkic.weatherapplication.weatherData.Json4Kotlin_Base
import kotlinx.android.synthetic.main.element_in_fragment_activity.view.*
import kotlinx.android.synthetic.main.first_element_in_recycler_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentAdapter(
        private var listOfItems: Json4Kotlin_Base,
        var doOnClick: (Int) -> Unit
) : RecyclerView.Adapter<FragmentAdapter.ElementViewHolder>() {

    var firstItem = 0
    var otherItem = 1

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        view = when (viewType) {
            firstItem -> {
                LayoutInflater.from(parent.context).inflate(R.layout.first_element_in_recycler_view, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.element_in_fragment_activity, parent, false)
            }
        }
        return ElementViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> firstItem
            else -> otherItem
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        when (holder.itemViewType) {
            firstItem -> {
                holder.itemView.image_view_cloud_in_first_element.setImageResource(R.drawable::class.java.getImageId(listOfItems.data.get(position).weather.icon))
                holder.itemView.text_view_day_in_first_element.text = listOfItems.data[position].datetime.format()
                holder.itemView.text_view_day_forecast_in_first_element.text = listOfItems.data[position].weather.description
                holder.itemView.text_view_temperature_in_first_element.text = listOfItems.data[position].temp.toInt().toString() + "°"
                holder.itemView.text_view_temperature_feels_like_in_first_element.text = listOfItems.data[position].low_temp.toInt().toString() + "°"

            }
            else -> {
                holder.itemView.image_view_cloud.setImageResource(R.drawable::class.java.getImageId(listOfItems.data[position].weather.icon))
                holder.itemView.text_view_day.text = listOfItems.data[position].datetime.format()
                holder.itemView.text_view_day_forecast.text = listOfItems.data[position].weather.description
                holder.itemView.text_view_temperature.text = listOfItems.data[position].temp.toInt().toString() + "°"
                holder.itemView.text_view_temperature_feels_like.text = listOfItems.data[position].low_temp.toInt().toString() + "°"
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.data.size
    }

    /////////////////////////////////////////////////////////////////////////////////
   open inner class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { doOnClick(adapterPosition) }
        }

    }

}

private fun String.format(): String {
    return SimpleDateFormat("EEEE, MMM-dd").format(Date())
}


