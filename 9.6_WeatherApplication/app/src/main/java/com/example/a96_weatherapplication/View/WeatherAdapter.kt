package com.example.a96_weatherapplication.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a96_weatherapplication.Data.Forecast
import com.example.a96_weatherapplication.R

class WeatherAdapter(val forcastList: List<Forecast>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_single_view, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.imageViewHolder.setImageResource(forcastList.get(position).imageID)
        holder.textViewDayOfWeek.text = forcastList.get(position).textViewWeekDay
        holder.textViewForecast.text = forcastList.get(position).textViewForecast
        holder.textViewTempHigh.text = forcastList.get(position).textViewFTempHight
        holder.textViewTempLow.text = forcastList.get(position).textViewFTempLow
    }

    override fun getItemCount(): Int {
        return forcastList.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewHolder = itemView.findViewById<ImageView>(R.id.imageView_single_view)
        val textViewDayOfWeek =
            itemView.findViewById<TextView>(R.id.textView_single_view_day_of_week)
        val textViewForecast = itemView.findViewById<TextView>(R.id.textView_single_view_forecast)
        val textViewTempHigh = itemView.findViewById<TextView>(R.id.textView_single_view_temp_high)
        val textViewTempLow = itemView.findViewById<TextView>(R.id.textView_single_view_temp_low)


    }
}