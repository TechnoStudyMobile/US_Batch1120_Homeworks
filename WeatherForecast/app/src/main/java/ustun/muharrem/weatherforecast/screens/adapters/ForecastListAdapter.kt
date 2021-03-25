package ustun.muharrem.weatherforecast.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.databinding.ForecastItemViewBinding
import ustun.muharrem.weatherforecast.databinding.ForecastTodayItemViewBinding
import ustun.muharrem.weatherforecast.utilities.DateUtil
import ustun.muharrem.weatherforecast.utilities.DrawableManager.getImageId
import ustun.muharrem.weatherforecast.utilities.SharedPrefs
import kotlin.math.roundToInt

class ForecastListAdapter(
    private val forecastContainer: ForecastContainer, val onClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ForecastViewHolder(private val binding: ForecastItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            binding.run {
                highTempForecastItemView.text =
                    forecastContainer.data[position].high_temp.roundToInt().toString().plus("°")
                lowTempForecastItemView.text =
                    forecastContainer.data[position].low_temp.roundToInt().toString().plus("°")
                weatherDescriptionForecastItemView.text =
                    forecastContainer.data[position].weather.description
                dateForecastItemView.text =
                    DateUtil.getDateText(forecastContainer.data[position].valid_date, position)
                weatherIconForecastItemView.setImageResource(
                    R.drawable::class.java.getImageId(
                        forecastContainer.data[position].weather.icon
                    )
                )
            }
        }
    }

    inner class ForecastTodayViewHolder(private val binding: ForecastTodayItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            binding.run {
                forecastTodayItemViewHighTemp.text =
                    forecastContainer.data[position].high_temp.roundToInt().toString().plus("°")
                forecastTodayItemViewLowTemp.text =
                    forecastContainer.data[position].low_temp.roundToInt().toString().plus("°")
                forecastTodayItemViewWeatherDescription.text =
                    forecastContainer.data[position].weather.description
                forecastTodayItemViewDate.text =
                    DateUtil.getDateText(forecastContainer.data[position].valid_date, position)
                forecastTodayItemViewIcon.setImageResource(
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
                ForecastTodayItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ForecastViewHolder(
                ForecastItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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