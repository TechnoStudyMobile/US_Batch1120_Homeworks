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
import com.crnkic.weatherapp.forecastResponse.ForecastResponse
import com.crnkic.weatherapp.forecastResponse.Forecast
import kotlinx.android.synthetic.main.forecast_next_day_item.view.*
import kotlinx.android.synthetic.main.forecast_today_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentAdapter(
        private var forecast: ForecastResponse,
        val doOnClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TODAY_FORECAST_ITEM = 0
    private val NEXT_DAY_FORECAST_ITEM = 1

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TODAY_FORECAST_ITEM) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_today_item, parent, false)
            ForecastTodayViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_next_day_item, parent, false)
            ForecastNextDayViewHolder(view, doOnClick)
        }
    }

override fun getItemViewType(position: Int): Int {
    return when (position) {
        0 -> TODAY_FORECAST_ITEM
        else -> NEXT_DAY_FORECAST_ITEM
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SetTextI18n")
override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
        is ForecastTodayViewHolder -> holder.bindToday(position)
        is ForecastNextDayViewHolder -> holder.bindNextDay(forecast.forecastList[position])
    }
}

override fun getItemCount(): Int {
    return forecast.forecastList.size
}

/////////////////////////////////////////////////////////////////////////////////
inner class ForecastTodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener { doOnClick.invoke(adapterPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bindToday(position: Int) {
        itemView.run {
            today_image_view_cloud.setImageResource(R.drawable::class.java.getImageId(forecast.forecastList.get(position).weather.icon))
            today_text_view_day.text = forecast.forecastList[position].datetime.format()
            today_text_view_day_forecast.text = forecast.forecastList[position].weather.description
            today_text_view_temperature.text = forecast.forecastList[position].temp.toInt().toString() + "°"
            today_text_view_temperature_feels_like.text = forecast.forecastList[position].low_temp.toInt().toString() + "°"
        }
    }
}

}

private fun String.format(): String {
    return SimpleDateFormat("EEEE, MMM-dd").format(Date())
}

class ForecastNextDayViewHolder(itemView: View, val doOnClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener { doOnClick.invoke(adapterPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bindNextDay(forecast: Forecast) {
        itemView.run {
            next_day_image_view_cloud.setImageResource(R.drawable::class.java.getImageId(forecast.weather.icon))
            next_day_text_view_day.text = forecast.datetime.format()
            next_day_text_view_day_forecast.text = forecast.weather.description
            next_day_text_view_temperature.text = forecast.temp.toInt().toString() + "°"
            next_day_text_view_temperature_feels_like.text = forecast.low_temp.toInt().toString() + "°"
        }
    }
}