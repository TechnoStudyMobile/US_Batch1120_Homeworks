package com.samil.app.theweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.samil.app.theweather.R
import com.samil.app.theweather.model.Forecast
import kotlinx.android.synthetic.main.item_list_forecast.view.*

class WeatherAdapter(val forecastList: List<Forecast>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_forecast,parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.imageViewHolder.setImageResource(forecastList.get(position).imageId)
        holder.textViewWeekdays.text = forecastList.get(position).textViewWeekdays
        holder.textViewForecast.text = forecastList.get(position).textViewForecast
        holder.textViewHighTemp.text = forecastList.get(position).textViewHighTemp
        holder.textViewLowTemp.text = forecastList.get(position).textViewLowTemp
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageViewHolder = itemView.imageView_forecast
        val textViewWeekdays = itemView.textView_weekdays
        val textViewForecast = itemView.textView_forecast
        val textViewHighTemp = itemView.textView_high_temp
        val textViewLowTemp = itemView.textView_low_temp
    }
}