package com.crnkic.weatherapp.view.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.util.DrawableUtil.getImageId
import com.crnkic.weatherapp.forecastResponse.ForcastResponse
import kotlinx.android.synthetic.main.element_in_fragment_activity.view.*
import kotlinx.android.synthetic.main.first_element_in_recycler_view.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FragmentAdapter(
        private var listOfItems: ForcastResponse,
        val doOnClick: (position:Int) -> Unit
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        when (holder.itemViewType) {
            firstItem -> {
                holder.itemView.run {
                    image_view_cloud_in_first_element.setImageResource(R.drawable::class.java.getImageId(listOfItems.forecastList.get(position).weather.icon))
                    text_view_day_in_first_element.text = listOfItems.forecastList[position].datetime.format()
                    text_view_day_forecast_in_first_element.text = listOfItems.forecastList[position].weather.description
                    text_view_temperature_in_first_element.text = listOfItems.forecastList[position].temp.toInt().toString() + "°"
                    text_view_temperature_feels_like_in_first_element.text = listOfItems.forecastList[position].low_temp.toInt().toString() + "°"
                }
            }
            else -> {
                holder.itemView.image_view_cloud.setImageResource(R.drawable::class.java.getImageId(listOfItems.forecastList[position].weather.icon))
                holder.itemView.text_view_day.text = formatDate(
                        listOfItems.forecastList[position].datetime
                )
                holder.itemView.text_view_day_forecast.text = listOfItems.forecastList[position].weather.description
                holder.itemView.text_view_temperature.text = listOfItems.forecastList[position].temp.toInt().toString() + "°"
                holder.itemView.text_view_temperature_feels_like.text = listOfItems.forecastList[position].low_temp.toInt().toString() + "°"
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.forecastList.size
    }

    /////////////////////////////////////////////////////////////////////////////////
    inner class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { doOnClick.invoke(adapterPosition) }
        }
    }

}

private fun String.format(): String {
    return SimpleDateFormat("EEEE, MMM-dd").format(Date())
}

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date: String): String {
        val formatter: DateTimeFormatter = if (LocalDate.parse(date).isAfter(LocalDate.now().plusDays(6))  ) {
            DateTimeFormatter.ofPattern("EE, MMM dd")
        } else {
            DateTimeFormatter.ofPattern("EEEE")
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter).toString()
    }