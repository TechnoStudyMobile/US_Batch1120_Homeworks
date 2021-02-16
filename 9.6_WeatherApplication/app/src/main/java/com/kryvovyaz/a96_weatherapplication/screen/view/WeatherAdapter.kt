package com.kryvovyaz.a96_weatherapplication.screen.view

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
import kotlinx.android.synthetic.main.forecast_single_view.view.*

class WeatherAdapter(var forecast: Forecast?, var doOnClick: (Int) -> Unit) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_single_view, parent, false)
        return WeatherViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        forecast?.data?.get(
            position
        )?.weather?.icon?.let {getIcon->
            R.drawable::class.java.getImageId(
               getIcon
            )
        }?.let {setImage->
            holder.itemView.imageView_single_view.setImageResource(
                setImage
            )
        }
        holder.itemView.textView_single_view_day_of_week.text = forecast?.data?.get(position)?.let {setDate->
            formatDate(
                setDate.datetime)
        }
        holder.itemView.textView_single_view_forecast.text = forecast?.data?.get(position)?.weather?.description
        holder.itemView.textView_single_view_temp_high.text = (forecast?.data?.get(position)?.high_temp?.toInt().toString() + "°")
        holder.itemView.textView_single_view_temp_low.text = (forecast?.data?.get(position)?.low_temp?.toInt().toString() + "°")
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