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

class WeatherAdapter(private val forecastList: Forecast, var doOnClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                holder.itemView.run {
                    image_first_item.setImageResource(
                        R.drawable::class.java.getImageId(
                            forecastList.data.get(position).weather.icon
                        )
                    )
                    forecast_first_item.text =
                        forecastList.data.get(position).weather.description
                    tempHigh_first_item.text =
                        (forecastList.data.get(position).high_temp.toInt().toString() + "°")
                    tempLow_first_item.text =
                        (forecastList.data.get(position).low_temp.toInt().toString() + "°")
                    curent_temp.text = "Current " +
                            (forecastList.data.get(position).temp.toInt().toString() + "°")
                    humidity.text =
                        "Humidity " + forecastList.data.get(position).humidityAverage.toString() + "%"
                }
            }
            else -> {
                holder.itemView.run {
                    this.icon_image_view.setImageResource(
                        R.drawable::class.java.getImageId(
                            forecastList.data.get(position).weather.icon
                        )
                    )
                    day_of_week_text_view.text =
                        forecastList.data.get(position).let { setDate ->
                            formatDate(
                                setDate.datetime
                            )
                        }
                    textView_single_view_forecast.text =
                        forecastList.data.get(position).weather.description
                    textView_single_view_temp_high.text =
                        (forecastList.data.get(position).high_temp.toInt().toString() + "°")
                    textView_single_view_temp_low.text =
                        (forecastList.data.get(position).low_temp.toInt().toString() + "°")
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            firstItem
        } else otherItems
    }

    override fun getItemCount(): Int {
        return forecastList.data.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}