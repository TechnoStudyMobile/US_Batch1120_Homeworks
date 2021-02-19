package com.kryvovyaz.a96_weatherapplication.screen.view

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.DrawableUtil.getImageId
import kotlinx.android.synthetic.main.first_item_in_fragment_forecast.view.*
import kotlinx.android.synthetic.main.forecast_single_view.view.*

class WeatherAdapter(var forecast: Forecast?, var doOnClick: (Int) -> Unit) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val firstItem = 0
    private val otherItems = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View
        when (viewType) {
            0 -> view = LayoutInflater.from(parent.context)
                .inflate(R.layout.first_item_in_fragment_forecast, parent, false)
            else -> view = LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_single_view, parent, false)
        }
        return WeatherViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                holder.itemView.image_first_item.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecast?.data?.get(position)?.weather?.icon
                    )
                )
                holder.itemView.forecast_first_item.text =
                    forecast?.data?.get(position)?.weather?.description
                holder.itemView.tempHigh_first_item.text =
                    (forecast?.data?.get(position)?.high_temp?.toInt().toString() + "°")
                holder.itemView.tempLow_first_item.text =
                    (forecast?.data?.get(position)?.low_temp?.toInt().toString() + "°")
                holder.itemView.curent_temp.text = "Current " +
                        (forecast?.data?.get(position)?.temp?.toInt().toString() + "°")
                holder.itemView.humidity.text =
                    "Humidity " + forecast?.data?.get(position)?.humidityAverage.toString() + "%"
            }
            else -> {
                holder.itemView.imageView_single_view.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecast?.data?.get(position)?.weather?.icon
                    )
                )
                holder.itemView.textView_single_view_day_of_week.text =
                    forecast?.data?.get(position)?.let { setDate ->
                        formatDate(
                            setDate.datetime
                        )
                    }
                holder.itemView.textView_single_view_forecast.text =
                    forecast?.data?.get(position)?.weather?.description
                holder.itemView.textView_single_view_temp_high.text =
                    (forecast?.data?.get(position)?.high_temp?.toInt().toString() + "°")
                holder.itemView.textView_single_view_temp_low.text =
                    (forecast?.data?.get(position)?.low_temp?.toInt().toString() + "°")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            firstItem
        } else otherItems
    }

    override fun getItemCount(): Int {
        return forecast?.data?.size!!
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { doOnClick(adapterPosition) }
        }
    }
}