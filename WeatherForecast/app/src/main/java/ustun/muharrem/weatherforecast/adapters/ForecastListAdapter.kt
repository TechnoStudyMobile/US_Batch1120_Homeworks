package ustun.muharrem.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.forecast_item_view.view.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.DummyForecast

class ForecastListAdapter(private val forecastList: MutableList<DummyForecast>) :
    RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item_view, parent, false)
        return ForecastViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.itemView.run {
            high_temp_forecast_item_view.text = forecastList[position].high_temp.toString().plus("°")
            low_temp_forecast_item_view.text = forecastList[position].low_temp.toString().plus("°")
            weather_description_forecast_item_view.text = forecastList[position].weatherDescription
            date_forecast_item_view.text = forecastList[position].valid_date
            weather_icon_forecast_item_view.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }


}