package com.samil.app.theweather.screen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samil.app.theweather.R
import com.samil.app.theweather.model.ForecastContainer
import com.samil.app.theweather.utils.GetImageId.getImageId
import kotlinx.android.synthetic.main.item_list_forecast.view.*
import kotlinx.android.synthetic.main.item_list_forecast_first_big_item.view.*

class ForecastAdapter(
    private val forecastContainer: ForecastContainer, val onClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val firstItem = 0
    private val otherItems = 1
    lateinit var view: View

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) firstItem else otherItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return if (viewType == firstItem) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_forecast_first_big_item, parent, false)
            WeatherViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_forecast, parent, false)
            WeatherViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            firstItem -> {
                holder.itemView.run {
                    imageView_forecast_first_element.setImageResource(
                        R.drawable::class.java
                            .getImageId(forecastContainer.forecastList[position].weather.icon)
                    )
                    textView_weekdays_first_element.text = forecastContainer
                        .forecastList[position].datetime
                    textView_forecast_first_element.text =
                        forecastContainer.forecastList[position].weather.description
                    textView_high_temp_first_element.text =
                        forecastContainer.forecastList[position].maxTemp.toString()
                    textView_low_temp_first_element.text =
                        forecastContainer.forecastList[position].minTemp.toString()
                }
            }
            else -> {
                holder.itemView.run {
                    imageView_forecast.setImageResource(
                        R.drawable::class.java
                            .getImageId(forecastContainer.forecastList[position].weather.icon)
                    )
                    textView_weekdays.text = forecastContainer.forecastList[position].datetime
                    textView_forecast.text =
                        forecastContainer.forecastList[position].weather.description
                    textView_high_temp.text =
                        forecastContainer.forecastList[position].maxTemp.toString()
                    textView_low_temp.text =
                        forecastContainer.forecastList[position].minTemp.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return forecastContainer.forecastList.size
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }
    }
}