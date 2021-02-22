package com.kryvovyaz.a96_weatherapplication.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel.model.Json4Kotlin_Base
import com.kryvovyaz.a96_weatherapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherAdapter(val forcastList: Json4Kotlin_Base) :

    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_single_view, parent, false)
        return WeatherViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
//       Picasso.get().load(forcastList.data?.get(position)?.weather?.icon)
//          .into(holder.imageViewHolder)

//forcastList.data.get(position).weather.icon.toString()

       // holder.imageViewHolder.setImageResource()
        holder.imageViewHolder.setImageResource(R.drawable.c02d)
        holder.weekDay.text = formatDate(forcastList.data.get(position).datetime)
        holder.forecast.text = forcastList.data.get(position).weather.description
        holder.tempHigh.text = (forcastList.data.get(position).high_temp.toString() + "°")
        holder.tempLow.text = (forcastList.data.get(position).low_temp.toString() + "°")
    }

    override fun getItemCount(): Int {
        return forcastList.data.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewHolder = itemView.findViewById<ImageView>(R.id.imageView_single_view)
        val weekDay = itemView.findViewById<TextView>(R.id.textView_single_view_day_of_week)
        val forecast = itemView.findViewById<TextView>(R.id.textView_single_view_forecast)
        val tempHigh = itemView.findViewById<TextView>(R.id.textView_single_view_temp_high)
        val tempLow = itemView.findViewById<TextView>(R.id.textView_single_view_temp_low)
    }

//    fun Context.resIdByName(resIdName: String?, resType: String): Int {
//           return resources.getIdentifier(resIdName, resType, packageName)
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: String): String {
        var formatter: DateTimeFormatter
        if (LocalDate.parse(date).isAfter(LocalDate.now().plusDays(6))  ) {
            formatter = DateTimeFormatter.ofPattern("EE, MMM dd")
        } else {
            formatter = DateTimeFormatter.ofPattern("EEEE")
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter).toString()
    }
}

