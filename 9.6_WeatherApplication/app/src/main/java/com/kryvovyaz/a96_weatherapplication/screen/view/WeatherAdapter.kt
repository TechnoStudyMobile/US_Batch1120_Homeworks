package com.kryvovyaz.a96_weatherapplication.screen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.ForecastSingleViewBinding
import com.kryvovyaz.a96_weatherapplication.databinding.TodayForecastBinding
import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.App
import com.kryvovyaz.a96_weatherapplication.util.TextUtil.capitalizeWords
import com.kryvovyaz.a96_weatherapplication.util.DateUtil.formatDate
import com.kryvovyaz.a96_weatherapplication.util.DrawableUtil.getImageId

import java.util.*

class WeatherAdapter(
    private val forecastContainerList: ForecastContainer,
    private val pref: Boolean,
    val onClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val FORECAST_DAY = 0
    private val FORECAST_WEEK = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FORECAST_DAY) {
            WeatherTodayViewHolder(
                TodayForecastBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        } else {
            WeatherViewHolder(
                ForecastSingleViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
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
        return forecastContainerList.forecastList.size
    }

    inner class WeatherTodayViewHolder(private val binding: TodayForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            itemView.run {
                binding.imageToday.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastContainerList.forecastList[position].weather.icon
                    )
                )
                binding.forecastToday.text =
                    forecastContainerList.forecastList[position].weather.description
                binding.tempHighToday.text =
                    forecastContainerList.forecastList[position].high_temp.toInt().toString()
                        .plus(context.getString(R.string.degree_character))
                binding.tempLowToday.text =
                    forecastContainerList.forecastList[position].low_temp.toInt().toString()
                        .plus(context.getString(R.string.degree_character))
                binding.curentTempToday.text =
                    forecastContainerList.forecastList[position].temp.toInt().toString()
                        .plus(context.getString(R.string.degree_character))
                binding.humidityToday.text = (forecastContainerList.forecastList[position]
                    .probability.toString()
                    .plus(context.getString(R.string.percent)))
                binding.windSpeedToday.text = (forecastContainerList.forecastList[position].wind_spd
                    .toInt().toString()
                    .plus(context.getString(R.string.space)).plus(
                        if (App.prefs!!.icCelsius)
                            resources.getString(R.string.wind_speed_m) else context.getString(
                            R.string.wind_speed_i
                        )
                    ))
            }
        }
    }

    inner class WeatherViewHolder(private val binding: ForecastSingleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            itemView.run {
                binding.iconImageView.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastContainerList.forecastList[position].weather.icon
                    )
                )
                binding.dayOfWeekTextView.text = capitalizeWords(
                    formatDate(
                        forecastContainerList.forecastList[position].datetime, position, context
                    )
                )
                formatDate(
                    forecastContainerList.forecastList[position].datetime, position, context
                ).capitalize(Locale.ROOT)
                binding.textViewSingleViewForecast.text =
                    forecastContainerList.forecastList[position].weather.description
                binding.tempHighTextView.text =
                    forecastContainerList.forecastList[position].high_temp.toInt().toString()
                        .plus(context.getString(R.string.degree_character))
                binding.textViewSingleViewTempLow.text =
                    forecastContainerList.forecastList[position].low_temp.toInt().toString()
                        .plus(context.getString(R.string.degree_character))
            }
        }
    }
}