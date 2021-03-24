package ustun.muharrem.weatherforecast.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.forecast_item_view.view.*
import kotlinx.android.synthetic.main.forecast_today_item_view.view.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.utilities.DateUtil
import ustun.muharrem.weatherforecast.utilities.DrawableManager.getImageId
import ustun.muharrem.weatherforecast.utilities.SharedPrefs
import kotlin.math.roundToInt

class ForecastListAdapter(
    private val forecastContainer: ForecastContainer, val onClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            itemView.run {
                high_temp_forecast_item_view.text =
                    forecastContainer.data[position].high_temp.roundToInt().toString().plus("°")
                low_temp_forecast_item_view.text =
                    forecastContainer.data[position].low_temp.roundToInt().toString().plus("°")
                weather_description_forecast_item_view.text =
                    forecastContainer.data[position].weather.description
                date_forecast_item_view.text =
                    DateUtil.getDateText(forecastContainer.data[position].valid_date, position)
                weather_icon_forecast_item_view.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastContainer.data[position].weather.icon
                    )
                )
            }
        }
    }

    inner class ForecastTodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            itemView.run {
                forecast_today_item_view_high_temp.text =
                    forecastContainer.data[position].high_temp.roundToInt().toString().plus("°")
                forecast_today_item_view_low_temp.text =
                    forecastContainer.data[position].low_temp.roundToInt().toString().plus("°")
                forecast_today_item_view_weather_description.text =
                    forecastContainer.data[position].weather.description
                forecast_today_item_view_date.text =
                    DateUtil.getDateText(forecastContainer.data[position].valid_date, position)
                forecast_today_item_view_icon.setImageResource(
                    R.drawable::class.java.getImageId(forecastContainer.data[position].weather.icon)
                )
            }
        }
    }

    private val TODAY = 1
    private val OTHER_DAYS = 2
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TODAY else OTHER_DAYS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TODAY) {
            ForecastTodayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.forecast_today_item_view, parent, false)
            )
        } else {
            ForecastViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.forecast_item_view, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return SharedPrefs.numberOfDays!!.toInt()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ForecastViewHolder -> holder.bind(position)
            is ForecastTodayViewHolder -> holder.bind(position)
        }
    }
}