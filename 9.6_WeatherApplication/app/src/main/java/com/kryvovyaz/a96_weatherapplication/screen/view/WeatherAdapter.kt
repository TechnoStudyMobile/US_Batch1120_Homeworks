package com.kryvovyaz.a96_weatherapplication.screen.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.util.Capitalization.capitalizeWords
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.DrawableUtil.getImageId
import kotlinx.android.synthetic.main.forecast_single_view.view.*
import kotlinx.android.synthetic.main.today_forecast.view.*
import java.util.*

class WeatherAdapter(
    private val forecastList: Forecast,
    private val pref: Boolean,
    val onClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val FORECAST_DAY = 0
    private val FORECAST_WEEK = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FORECAST_DAY) {
            WeatherTodayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.today_forecast, parent, false)
            )
        } else {
            WeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.forecast_single_view, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherTodayViewHolder -> holder.bind(position)
            is WeatherViewHolder -> holder.bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) FORECAST_DAY else FORECAST_WEEK
    }

    override fun getItemCount(): Int {
        return forecastList.data.size
    }

    inner class WeatherTodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            itemView.run {
                image_today.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastList.data[position].weather.icon
                    )
                )
                forecast_today.text =
                    forecastList.data[position].weather.description
                tempHigh_today.text =
                    (forecastList.data[position].high_temp.toInt().toString() + "°")
                tempLow_today.text =
                    (forecastList.data[position].low_temp.toInt().toString() + "°")
                curent_temp_today.text =
                    forecastList.data[position].temp.toInt().toString().plus("°")
                humidity_today.text = (forecastList.data[position].humidityAverage.toString()
                    .plus("%"))
                wind_speed_today.text = (forecastList.data[position].wind_spd.toInt().toString()
                    .plus(" ").plus(
                        if (pref) resources.getString(R.string.wind_speed_m) else context.getString(
                            R.string.wind_speed_i
                        )
                    ))
            }
        }
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            itemView.run {
                this.icon_image_view.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastList.data[position].weather.icon
                    )
                )
                day_of_week_text_view.text = capitalizeWords(formatDate(
                    forecastList.data[position].datetime, position, context
                ))
                formatDate(
                    forecastList.data[position].datetime, position, context
                ).capitalize(Locale.ROOT)
                textView_single_view_forecast.text =
                    forecastList.data[position].weather.description
                temp_high_text_view.text =
                    (forecastList.data[position].high_temp.toInt().toString() + "°")
                textView_single_view_temp_low.text =
                    (forecastList.data[position].low_temp.toInt().toString() + "°")
            }
        }
    }
}